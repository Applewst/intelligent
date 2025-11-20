<template>
  <div class="student-management">
    <!-- 搜索栏 -->
    <el-card class="search-card" shadow="never">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="姓名">
          <el-input
            v-model="searchForm.name"
            placeholder="请输入姓名"
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 工具栏 -->
    <el-card class="toolbar-card" shadow="never">
      <el-button type="primary" @click="handleAdd">新增学生</el-button>
    </el-card>

    <!-- 数据表格 -->
    <el-card class="table-card" shadow="never">
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="stucode" label="学工号" width="150" />
        <el-table-column prop="grade" label="年级" width="120" />
        <el-table-column prop="gender" label="性别" width="100">
          <template #default="{ row }">
            <el-tag :type="row.gender === 1 ? 'primary' : 'danger'">
              {{ row.gender === 1 ? "男" : "女" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="avatar" label="照片" width="120">
          <template #default="{ row }">
            <el-image
              :src="row.avatar"
              :preview-src-list="[row.avatar]"
              fit="cover"
              style="width: 60px; height: 60px; border-radius: 4px"
            />
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)"
              >编辑</el-button
            >
            <el-button type="danger" size="small" @click="handleDelete(row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.pageNum"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[5, 10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑弹窗 -->
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
        <el-form-item label="学工号" prop="stucode">
          <el-input v-model="formData.stucode" placeholder="请输入学工号" />
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-select
            v-model="formData.grade"
            placeholder="请选择年级"
            style="width: 100%"
          >
            <el-option label="大一" value="大一" />
            <el-option label="大二" value="大二" />
            <el-option label="大三" value="大三" />
            <el-option label="大四" value="大四" />
          </el-select>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="formData.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="0">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="照片" prop="avatar">
          <el-input v-model="formData.avatar" placeholder="请输入照片URL" />
          <div v-if="formData.avatar" style="margin-top: 10px">
            <el-image
              :src="formData.avatar"
              fit="cover"
              style="width: 100px; height: 100px; border-radius: 4px"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button
          type="primary"
          @click="handleSubmit"
          :loading="submitLoading"
        >
          确定
        </el-button>
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
  getStudentList,
  addStudent,
  updateStudent,
  deleteStudent,
} from "@/api/student";
import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";
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
  pageNum: 1,
  pageSize: 5,
  total: 0,
});

// 弹窗
const dialogVisible = ref(false);
const dialogTitle = ref("新增学生");
const formRef = ref(null);
const submitLoading = ref(false);

// 表单数据
const formData = reactive({
  id: null,
  name: "",
  stucode: "",
  grade: "",
  gender: 1,
  avatar: "",
});

// 表单验证规则
const formRules = {
  name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
  stucode: [{ required: true, message: "请输入学工号", trigger: "blur" }],
  grade: [{ required: true, message: "请选择年级", trigger: "change" }],
  gender: [{ required: true, message: "请选择性别", trigger: "change" }],
  avatar: [{ required: true, message: "请输入照片URL", trigger: "blur" }],
};

// 获取学生列表
const fetchStudentList = async () => {
  loading.value = true;
  try {
    const params = {
      ...searchForm,
      pageNum: pagination.pageNum,
      pageSize: pagination.pageSize,
    };
    const res = await getStudentList(params);
    // console.log(res.data);

    if (res.code === 1) {
      tableData.value = res.data.data;
      pagination.total = res.data.total;
    }
  } catch (error) {
    ElMessage.error("获取学生列表失败");
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.pageNum = 1;
  fetchStudentList();
};

// 重置
const handleReset = () => {
  searchForm.name = "";
  pagination.pageNum = 1;
  fetchStudentList();
};

// 新增
const handleAdd = () => {
  dialogTitle.value = "新增学生";
  dialogVisible.value = true;
  resetForm();
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑学生";
  dialogVisible.value = true;
  console.log(formData);

  Object.assign(formData, row);
};

// 删除
const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  try {
    await deleteStudent({ id: currentDeleteRow.value.id });
    ElMessage.success("删除成功");
    fetchStudentList();
  } catch (error) {
    ElMessage.error("删除失败");
  }
};

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitLoading.value = true;
      try {
        if (formData.id) {
          // 编辑
          await updateStudent(formData.id, formData);
          ElMessage.success("更新成功");
        } else {
          // 新增
          await addStudent(formData);
          ElMessage.success("添加成功");
        }
        dialogVisible.value = false;
        fetchStudentList();
      } catch (error) {
        ElMessage.error("操作失败");
      } finally {
        submitLoading.value = false;
      }
    }
  });
};

// 重置表单
const resetForm = () => {
  formData.id = null;
  formData.name = "";
  formData.stucode = "";
  formData.grade = "";
  formData.gender = 1;
  formData.avatar = "";
  if (formRef.value) {
    formRef.value.clearValidate();
  }
};

// 分页大小变化
const handleSizeChange = () => {
  fetchStudentList();
};

// 当前页变化
const handleCurrentChange = () => {
  fetchStudentList();
};

// 页面加载时获取数据
onMounted(() => {
  fetchStudentList();
});
</script>

<style scoped>
.student-management {
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
