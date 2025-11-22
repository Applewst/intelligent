<template>
  <div class="research-news-manager">
    <!-- 操作栏 -->
    <div class="toolbar">
      <div class="flex items-center gap-4">
        <el-input
          v-model="searchName"
          placeholder="请输入标题关键词搜索"
          clearable
          style="width: 300px"
          @clear="handleSearch"
        >
          <template #append>
            <el-button :icon="Search" @click="handleSearch" />
          </template>
        </el-input>
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          添加科研动态
        </el-button>
      </div>
    </div>

    <!-- 数据表格 -->
    <el-table
      :data="tableData"
      v-loading="loading"
      border
      style="width: 100%"
      class="data-table"
    >
      <el-table-column label="封面图" width="120" align="center">
        <template #default="{ row }">
          <el-image
            :src="row.image"
            fit="cover"
            style="width: 80px; height: 60px; border-radius: 4px"
            :preview-src-list="[row.image]"
          />
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" min-width="200" />
      <el-table-column
        prop="detail"
        label="详情"
        min-width="300"
        show-overflow-tooltip
      />
      <el-table-column
        prop="time"
        label="发布时间"
        width="180"
        align="center"
      />
      <el-table-column label="操作" width="180" align="center" fixed="right">
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
    <div class="pagination">
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadData"
        @current-change="loadData"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="resetForm"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入标题" />
        </el-form-item>

        <el-form-item label="封面图" prop="image">
          <el-upload
            class="upload-demo"
            :before-upload="beforeUpload"
            :show-file-list="false"
          >
            <el-button type="primary">选择图片</el-button>
          </el-upload>
          <!-- 实时显示上传的图片 -->
          <div v-if="formData.image" style="margin-top: 10px">
            <el-image
              :src="formData.image"
              fit="cover"
              style="width: 100px; height: 80px; border-radius: 4px"
            />
          </div>
        </el-form-item>

        <el-form-item label="详情" prop="detail">
          <el-input
            v-model="formData.detail"
            type="textarea"
            :rows="4"
            placeholder="请输入详细内容"
          />
        </el-form-item>
        <el-form-item label="发布时间" prop="time" v-if="isEdit">
          <el-date-picker
            v-model="formData.time"
            type="datetime"
            placeholder="选择日期时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <ConfirmDeleteDialog
      v-model="deleteDialogVisible"
      title="删除确认"
      message="确定要删除该动态吗？删除后将无法恢复。"
      @confirm="confirmDelete"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, watch } from "vue";
import { ElMessage } from "element-plus";
import {
  getResearchList,
  addResearch,
  updateResearch,
  deleteResearch,
} from "../api/research";
import { uploadImage } from "@/api/upload";
import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";
const deleteDialogVisible = ref(false);
const currentDeleteRow = ref(null);

// 表格数据
const tableData = ref([]);
const loading = ref(false);

// 分页数据
const pageNum = ref(1);
const pageSize = ref(10);
const total = ref(0);

const searchName = ref("");

// 对话框相关
const dialogVisible = ref(false);
const dialogTitle = ref("添加科研动态");
const isEdit = ref(false);

// 表单数据
const formRef = ref();
const formData = reactive({
  title: "",
  image: "",
  detail: "",
});

// 表单验证规则
const formRules = ref({
  title: [{ required: true, message: "请输入标题", trigger: "blur" }],
  image: [{ required: true, message: "请输入图片URL", trigger: "blur" }],
  detail: [{ required: true, message: "请输入详细内容", trigger: "blur" }],
  time: [
    {
      required: false, // 默认设为 false，后续动态修改
      message: "请选择发布时间",
      trigger: "change",
    },
  ],
});

watch(isEdit, (newVal) => {
  formRules.value.time[0].required = newVal;
});

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
      formData.image = response.data;
      ElMessage.success("图片上传成功!");
    }
  } catch (error) {
    ElMessage.error("图片上传失败!");
    console.error(error);
  }

  return false;
};

const loadData = async () => {
  loading.value = true;
  try {
    const response = await getResearchList(
      pageNum.value,
      pageSize.value,
      searchName.value
    );
    tableData.value = response.data.data;
    total.value = response.data.total;
    console.log(response.data.total);
  } catch (error) {
    ElMessage.error("加载数据失败");
    console.error(error);
  } finally {
    loading.value = false;
  }
};

const handleSearch = () => {
  pageNum.value = 1;
  loadData();
};

// 添加
const handleAdd = () => {
  dialogTitle.value = "添加科研动态";
  isEdit.value = false;
  dialogVisible.value = true;
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑科研动态";
  isEdit.value = true;
  Object.assign(formData, row);
  dialogVisible.value = true;
};

const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  try {
    await deleteResearch(currentDeleteRow.value.id);
    ElMessage.success("删除成功");
    loadData();
  } catch (error) {
    ElMessage.error("删除失败");
  }
};

const submitForm = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      // 深拷贝表单数据，避免修改原对象
      const submitData = { ...formData };

      if (!isEdit.value) {
        // 新增时：删除 time 字段，不传递给后端
        delete submitData.time;
      }

      try {
        if (isEdit.value) {
          await updateResearch(submitData);
          ElMessage.success("更新成功");
        } else {
          await addResearch(submitData); // 新增接口无 time 字段
          ElMessage.success("添加成功");
        }
        dialogVisible.value = false;
        loadData();
      } catch (error) {
        ElMessage.error(isEdit.value ? "更新失败" : "添加失败");
        console.error(error);
      }
    }
  });
};

// 重置表单
const resetForm = () => {
  formRef.value?.resetFields();
  Object.assign(formData, {
    id: undefined,
    title: "",
    image: "",
    detail: "",
    time: "",
  });
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
.research-news-manager {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}

.toolbar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.data-table {
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: flex-end;
}
</style>
