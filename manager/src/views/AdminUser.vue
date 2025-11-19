<template>
  <div class="user-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>人员管理</span>
          <el-button type="primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增人员
          </el-button>
        </div>
      </template>

      <!-- 搜索表单 -->
      <el-form :inline="true" :model="queryParams" class="search-form">
        <el-form-item label="关键词">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入用户名搜索"
            clearable
            @clear="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">
            <el-icon><Search /></el-icon>
            查询
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>

      <!-- 数据表格 -->
      <el-table :data="userList" v-loading="loading" border style="width: 100%">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" />
        <el-table-column prop="identity" label="身份" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              @click="handleEdit(row)"
              link
            >
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              @click="handleDelete(row)"
              link
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleQuery"
        @current-change="handleQuery"
        class="pagination"
      />
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
      :before-close="handleClose"
    >
      <el-form
        :model="formData"
        :rules="formRules"
        ref="formRef"
        label-width="80px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="formData.password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="身份" prop="identity">
          <el-select v-model="formData.identity" placeholder="请选择身份">
            <el-option label="管理员" value="admin" />
            <el-option label="普通用户" value="user" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="handleSubmit" :loading="submitting">
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { getUserList, addUser, updateUser, deleteUser } from "@/api/user";

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  name: "",
});

// 数据列表
const userList = ref([]);
const total = ref(0);
const loading = ref(false);

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref("新增人员");
const formRef = ref(null);
const submitting = ref(false);

// 表单数据
const formData = reactive({
  id: null,
  username: "",
  password: "",
  identity: "",
});

// 表单验证规则
const formRules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [
    {
      required: formData.id ? false : true,
      message: "请输入密码",
      trigger: "blur",
    },
    { min: 6, message: "密码长度不能少于 6 位", trigger: "blur" },
  ],
  identity: [{ required: true, message: "请选择身份", trigger: "change" }],
};

// 获取用户列表
const getList = async () => {
  loading.value = true;
  try {
    const res = await getUserList(queryParams);
    userList.value = res.data.data;
    total.value = res.data.total;
  } catch (error) {
    ElMessage.error("获取数据失败");
  } finally {
    loading.value = false;
  }
};

// 查询
const handleQuery = () => {
  queryParams.pageNum = 1;
  getList();
};

// 重置
const handleReset = () => {
  queryParams.name = "";
  queryParams.pageNum = 1;
  getList();
};

// 新增
const handleAdd = () => {
  dialogTitle.value = "新增人员";
  dialogVisible.value = true;
  formData.id = null;
  formData.username = "";
  formData.password = null;
  formData.identity = "";
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑人员";
  dialogVisible.value = true;
  // 注意：编辑时不填充密码，防止密码泄露
  Object.assign(formData, { ...row, password: null });
};

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm("确定要删除该人员吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  }).then(async () => {
    try {
      await deleteUser(row.id);
      ElMessage.success("删除成功");
      getList();
    } catch (error) {
      ElMessage.error("删除失败");
    }
  });
};

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true;
      try {
        if (formData.id) {
          // 编辑时，如果密码为空，则不传递 password 字段
          const submitData = { ...formData };
          if (!submitData.password) {
            delete submitData.password;
          }
          await updateUser(submitData);
          ElMessage.success("更新成功");
        } else {
          await addUser({ ...formData, id: undefined });
          ElMessage.success("新增成功");
        }
        dialogVisible.value = false;
        getList();
      } catch (error) {
        ElMessage.error(formData.id ? "更新失败" : "新增失败");
      } finally {
        submitting.value = false;
      }
    }
  });
};

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false;
  resetForm();
};

// 重置表单
const resetForm = () => {
  formData.id = null;
  formData.username = "";
  formData.password = null;
  formData.identity = "";
  if (formRef.value) {
    formRef.value.resetFields();
  }
};

// 页面加载时获取数据
onMounted(() => {
  getList();
});
</script>

<style scoped>
.user-management {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-form {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>