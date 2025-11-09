<template>
  <div class="resource-management">
    <el-card>
      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input
          v-model="searchKeyword"
          placeholder="请输入资源名称或类型"
          clearable
          style="width: 300px"
          @keyup.enter="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" @click="handleSearch">
          <el-icon><Search /></el-icon>
          搜索
        </el-button>
        <el-button type="success" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          添加资源
        </el-button>
      </div>

      <!-- 表格 -->
      <el-table
        :data="tableData"
        v-loading="loading"
        border
        stripe
        style="width: 100%; margin-top: 20px"
      >
        <el-table-column type="index" label="序号" width="60" align="center" />
        <el-table-column
          prop="name"
          label="文件名"
          min-width="200"
          show-overflow-tooltip
        />
        <el-table-column
          prop="size"
          label="文件大小"
          width="120"
          align="center"
        />
        <el-table-column
          prop="type"
          label="文件类型"
          width="120"
          align="center"
        >
          <template #default="{ row }">
            <el-tag :type="getTypeColor(row.type)">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="uploadTime"
          label="上传时间"
          width="180"
          align="center"
        />
        <el-table-column
          prop="downloads"
          label="下载量"
          width="100"
          align="center"
        />
        <el-table-column label="操作" width="180" align="center" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              size="small"
              link
              @click="handleEdit(row)"
            >
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button
              type="danger"
              size="small"
              link
              @click="handleDelete(row)"
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="80px"
      >
        <el-form-item label="文件名" prop="name">
          <el-input v-model="formData.name" placeholder="请输入文件名" />
        </el-form-item>
        <el-form-item label="文件大小" prop="size">
          <el-input
            v-model="formData.size"
            placeholder="请输入文件大小，如：2.5MB"
          />
        </el-form-item>
        <el-form-item label="文件类型" prop="type">
          <el-select
            v-model="formData.type"
            placeholder="请选择文件类型"
            style="width: 100%"
          >
            <el-option label="PDF" value="PDF" />
            <el-option label="DOCX" value="DOCX" />
            <el-option label="XLSX" value="XLSX" />
            <el-option label="ZIP" value="ZIP" />
            <el-option label="PNG" value="PNG" />
            <el-option label="JPG" value="JPG" />
            <el-option label="MD" value="MD" />
            <el-option label="SQL" value="SQL" />
            <el-option label="其他" value="OTHER" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import { Search, Plus, Edit, Delete } from "@element-plus/icons-vue";
import {
  getResourceList,
  addResource,
  updateResource,
  deleteResource,
} from "../api/resource";

// 搜索关键字
const searchKeyword = ref("");

// 表格数据
const tableData = ref([]);
const loading = ref(false);

// 分页
const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0,
});

// 对话框
const dialogVisible = ref(false);
const dialogTitle = ref("添加资源");
const formRef = ref(null);
const formData = reactive({
  id: "",
  name: "",
  size: "",
  type: "",
});

// 表单验证规则
const formRules = {
  name: [{ required: true, message: "请输入文件名", trigger: "blur" }],
  size: [{ required: true, message: "请输入文件大小", trigger: "blur" }],
  type: [{ required: true, message: "请选择文件类型", trigger: "change" }],
};

// 获取资源列表
const fetchResourceList = async () => {
  loading.value = true;
  try {
    const res = await getResourceList(
      pagination.page,
      pagination.pageSize,
      searchKeyword.value
    );
    if (res.code === 200) {
      tableData.value = res.data.list;
      pagination.total = res.data.total;
    }
  } catch (error) {
    console.error("获取资源列表失败:", error);
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pagination.page = 1;
  fetchResourceList();
};

// 添加
const handleAdd = () => {
  dialogTitle.value = "添加资源";
  dialogVisible.value = true;
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑资源";
  Object.assign(formData, row);
  dialogVisible.value = true;
};

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm("确定要删除这个资源吗？", "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(async () => {
      try {
        const res = await deleteResource(row.id);
        if (res.code === 200) {
          ElMessage.success("删除成功");
          fetchResourceList();
        }
      } catch (error) {
        console.error("删除失败:", error);
      }
    })
    .catch(() => {
      ElMessage.info("已取消删除");
    });
};

// 提交表单
const handleSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (formData.id) {
          // 编辑
          const res = await updateResource(formData.id, formData);
          if (res.code === 200) {
            ElMessage.success("更新成功");
            dialogVisible.value = false;
            fetchResourceList();
          }
        } else {
          const res = await addResource({
            name: formData.name,
            size: formData.size,
            type: formData.type,
          });
          if (res.code === 200) {
            ElMessage.success("添加成功");
            dialogVisible.value = false;
            fetchResourceList();
          }
        }
      } catch (error) {
        console.error("操作失败:", error);
      }
    }
  });
};

// 对话框关闭
const handleDialogClose = () => {
  formRef.value.resetFields();
  formData.id = "";
  formData.name = "";
  formData.size = "";
  formData.type = "";
};

// 分页大小改变
const handleSizeChange = () => {
  pagination.page = 1;
  fetchResourceList();
};

// 当前页改变
const handleCurrentChange = () => {
  fetchResourceList();
};

// 获取类型颜色
const getTypeColor = (type) => {
  const colorMap = {
    PDF: "danger",
    DOCX: "primary",
    XLSX: "success",
    ZIP: "warning",
    PNG: "info",
    JPG: "info",
    MD: "",
    SQL: "danger",
  };
  return colorMap[type] || "";
};

// 初始化
onMounted(() => {
  fetchResourceList();
});
</script>

<style scoped>
.resource-management {
  padding: 20px;
}

.search-bar {
  display: flex;
  gap: 10px;
  align-items: center;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
