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
          prop="updateTime"
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
          :page-sizes="[5, 10, 20, 50, 100]"
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

        <el-form-item label="文件上传" prop="url">
          <el-upload
            class="upload-resource"
            :http-request="handleFileUpload"
            :disabled="!!formData.url"
            :auto-upload="true"
            accept="*"
            :show-file-list="false"
          >
            <el-button size="small" type="primary">
              <el-icon><Upload /></el-icon>
              点击上传
            </el-button>
            <div style="margin-top: 10px">
              <span style="color: #666" v-if="formData.url">
                <i class="el-icon-check"></i> 文件已上传:
                {{ formData.fileName || formData.name }}
              </span>
            </div>
          </el-upload>
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
      message="确定要删除该资源吗？删除后将无法恢复。"
      @confirm="confirmDelete"
    />
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
// 引入你的通用上传接口
import { uploadImage } from "@/api/upload";
import {
  getResourceList,
  addResource,
  updateResource,
  deleteResource,
} from "../api/resource";
import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";

const deleteDialogVisible = ref(false);
const currentDeleteRow = ref(null);

// 搜索关键字
const searchKeyword = ref("");

// 表格数据
const tableData = ref([]);
const loading = ref(false);

// 分页
const pagination = reactive({
  page: 1,
  pageSize: 5,
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
  url: "", // 用于存储上传后返回的 URL
  fileName: "", // 用于显示已上传的文件名
});

// 表单验证规则
const formRules = {
  name: [{ required: true, message: "请输入文件名", trigger: "blur" }],
  size: [{ required: true, message: "请输入文件大小", trigger: "blur" }],
  type: [{ required: true, message: "请选择文件类型", trigger: "change" }],
  url: [{ required: true, message: "请上传文件", trigger: "change" }], // URL 为必填项
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
    if (res.code === 1) {
      tableData.value = res.data.data;
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
  // 重置表单，特别是 url 和 fileName
  // formRef.value?.resetFields();
  formData.id = "";
  formData.url = "";
  formData.fileName = "";
  dialogVisible.value = true;
};

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = "编辑资源";
  // 将行数据赋值给表单
  Object.assign(formData, row);
  // 假设后端返回的 URL 字段名为 'url'，如果不是，请修改
  // formData.url = row.fileUrl;
  dialogVisible.value = true;
};

// 删除
const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  try {
    const res = await deleteResource(currentDeleteRow.value.id);
    if (res.code === 1) {
      ElMessage.success("删除成功");
      fetchResourceList();
    }
  } catch (error) {
    console.error("删除失败:", error);
  }
};

const handleFileUpload = async (options) => {
  const file = options.file;
  try {
    // 直接调用你导入的通用上传接口
    const res = await uploadImage(file);

    if (res.code === 1) {
      // 注意：你的接口返回成功码是 1
      formData.url = res.data; // 将接口返回的 URL 字符串赋值给 formData.url
      formData.fileName = file.name; // 保存原始文件名用于显示
      ElMessage.success("文件上传成功");
    } else {
      ElMessage.error(res.message || "文件上传失败");
    }
  } catch (error) {
    console.error("文件上传失败:", error);
    ElMessage.error("文件上传失败，请稍后重试");
  }
};

// 提交表单
const handleSubmit = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        // 准备提交的数据
        const submitData = {
          name: formData.name,
          size: formData.size,
          type: formData.type,
          url: formData.url, // 将上传好的 URL 包含在提交数据中
        };

        if (formData.id) {
          // 编辑
          const res = await updateResource(formData.id, submitData);
          if (res.code === 1) {
            ElMessage.success("更新成功");
            dialogVisible.value = false;
            fetchResourceList();
          }
        } else {
          // 新增
          const res = await addResource(submitData);
          if (res.code === 1) {
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
  // 重置表单，特别是 url 和 fileName
  formRef.value?.resetFields();
  formData.id = "";
  formData.url = "";
  formData.fileName = "";
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
  // 首先，检查 type 是否存在且为字符串
  if (!type || typeof type !== "string") {
    return ""; // 如果 type 是 null, undefined 或其他类型，返回空字符串
  }

  const colorMap = {
    PDF: "danger",
    DOCX: "primary",
    XLSX: "success",
    ZIP: "warning",
    PNG: "info",
    JPG: "info",
    MD: "",
    SQL: "danger",
    // ... 其他你需要的类型
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
