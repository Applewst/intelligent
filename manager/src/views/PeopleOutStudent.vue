<template>
  <div class="graduate-management">
    <!-- 搜索栏 -->
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="毕业生姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入姓名"
            clearable
            @clear="handleSearch"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作栏 -->
    <el-card class="toolbar-card">
      <el-button type="primary" @click="handleAdd">新增毕业生</el-button>
    </el-card>

    <!-- 表格 -->
    <el-card class="table-card">
      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="gender" label="性别" width="100">
          <template #default="{ row }">
            <el-tag :type="row.gender === 1 ? 'primary' : 'danger'">
              {{ row.gender === 1 ? "男" : "女" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="照片" width="100">
          <template #default="{ row }">
            <el-image
              style="width: 50px; height: 50px; border-radius: 4px"
              :src="row.avatar"
              :preview-src-list="[row.avatar]"
              fit="cover"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.size"
          :page-sizes="[5, 10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      :close-on-click-modal="false"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.gender">
            <el-radio :value="1">男</el-radio>
            <el-radio :value="0">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="封面图" prop="avatar">
          <el-upload
            class="upload-demo"
            :before-upload="beforeUpload"
            :show-file-list="false"
          >
            <el-button type="primary">选择图片</el-button>
          </el-upload>
          <!-- 实时显示上传的图片 -->
          <div v-if="formData.avatar" style="margin-top: 10px">
            <el-image
              :src="formData.avatar"
              fit="cover"
              style="width: 100px; height: 80px; border-radius: 4px"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <ConfirmDeleteDialog
      v-model="deleteDialogVisible"
      title="删除确认"
      message="确定要删除该人员吗？删除后将无法恢复。"
      @confirm="confirmDelete"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import {
  getGraduateList,
  addGraduate,
  updateGraduate,
  deleteGraduate,
} from "@/api/graduate";
import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";

import { uploadImage } from "@/api/upload";

const beforeUpload = async (rawFile) => {
  // 示例：校验文件格式和大小
  const isImage = rawFile.type.startsWith("image/");
  if (!isImage) {
    ElMessage.error("只能上传图片文件！");
    return false;
  }

  const isLt2M = rawFile.size / 1024 / 1024 < 2;
  if (!isLt2M) {
    ElMessage.error("图片大小不能超过 2MB！");
    return false;
  }

  // 校验通过后手动上传
  try {
    const response = await uploadImage(rawFile);
    if (response && response.data) {
      formData.avatar = response.data;
      ElMessage.success("图片上传成功!");
    }
  } catch (error) {
    ElMessage.error("图片上传失败!");
    console.error(error);
  }

  return false;
};
const deleteDialogVisible = ref(false);
const currentDeleteRow = ref(null);

// 搜索表单
const searchForm = reactive({
  name: "",
});

// 表格数据
const tableData = ref([]);
const loading = ref(false);

// 分页
const pagination = reactive({
  page: 1,
  size: 5,
  total: 0,
});

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref("新增毕业生");
const formRef = ref(null);
const formData = reactive({
  id: null,
  name: "",
  gender: 1,
  avatar: "",
});

// 表单验证规则
const formRules = {
  name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
  gender: [{ required: true, message: "请选择性别", trigger: "change" }],
  avatar: [{ required: true, message: "请输入照片URL", trigger: "blur" }],
};

// 获取毕业生列表
const fetchGraduateList = async () => {
  loading.value = true;
  try {
    const params = {
      name: searchForm.name,
      pageNum: pagination.page,
      pageSize: pagination.size,
    };
    const res = await getGraduateList(params);
    // console.log(res);

    if (res.code === 1) {
      tableData.value = res.data.data;
      pagination.total = res.data.total;
    }
  } catch (error) {
    ElMessage.error("获取数据失败");
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.page = 1;
  fetchGraduateList();
};

// 重置
const handleReset = () => {
  searchForm.name = "";
  pagination.page = 1;
  fetchGraduateList();
};

// 新增
const handleAdd = () => {
  dialogTitle.value = "新增毕业生";
  dialogVisible.value = true;
  Object.assign(formData, {
    id: null,
    name: "",
    gender: 1,
    avatar: "",
  });
  formRef.value?.clearValidate();
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑毕业生";
  dialogVisible.value = true;
  // console.log(formData);
  Object.assign(formData, { ...row });
  formRef.value?.clearValidate();
};

// 删除

const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  try {
    await deleteGraduate({ id: currentDeleteRow.value.id });
    ElMessage.success("删除成功");
    fetchGraduateList();
  } catch (error) {
    ElMessage.error("删除失败");
  }
};

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate();

    const apiFunc = formData.id ? updateGraduate : addGraduate;
    const res = await apiFunc({ ...formData });

    if (res.code === 1) {
      ElMessage.success(formData.id ? "更新成功" : "添加成功");
      dialogVisible.value = false;
      fetchGraduateList();
    }
  } catch (error) {
    console.error("表单验证失败", error);
  }
};

// 分页
const handleSizeChange = (val) => {
  pagination.size = val;
  pagination.page = 1;
  fetchGraduateList();
};

const handleCurrentChange = (val) => {
  pagination.page = val;
  fetchGraduateList();
};

// 初始化
onMounted(() => {
  fetchGraduateList();
});
</script>

<style scoped>
.graduate-management {
  padding: 20px;
}

.search-card,
.toolbar-card,
.table-card {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
