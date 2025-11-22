<script setup>
import { ref, onMounted, watch } from "vue";
import { ElMessage } from "element-plus";
import {
  getEventList,
  createEvent,
  updateEvent,
  deleteEvent,
} from "../api/events.js";
import { uploadImage } from "@/api/upload";
import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";
const deleteDialogVisible = ref(false);
const currentDeleteRow = ref(null);

// 搜索参数
const searchName = ref("");

// 分页参数
const pageNum = ref(1);
const pageSize = ref(5);
const total = ref(0);

// 数据列表
const eventList = ref([]);
const loading = ref(false);

// 对话框状态
const dialogVisible = ref(false);
const isEdit = ref(false);
const dialogTitle = ref("");
const formRef = ref();

// 表单数据
const formData = ref({
  title: "",
  image: "",
  detail: "",
  time: "", // 编辑时会填充，新增时为空
});

// 表单验证规则（改为 ref 以便动态修改）
const formRules = ref({
  title: [{ required: true, message: "请输入活动标题", trigger: "blur" }],
  image: [{ required: true, message: "请输入图片URL", trigger: "blur" }],
  detail: [{ required: true, message: "请输入活动详情", trigger: "blur" }],
  time: [
    { required: false, message: "请选择活动时间", trigger: "change" }, // 默认不必填
  ],
});

// 监听 isEdit 变化，动态更新 time 字段的验证规则
watch(isEdit, (newVal) => {
  formRules.value.time[0].required = newVal; // 编辑时必填，新增时不必填
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
      formData.value.image = response.data;
      ElMessage.success("图片上传成功!");
    }
  } catch (error) {
    ElMessage.error("图片上传失败!");
    console.error(error);
  }

  return false;
};

// 加载数据
const loadData = async () => {
  loading.value = true;
  try {
    const response = await getEventList({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      title: searchName.value,
    });
    eventList.value = response.data.data;
    total.value = response.data.total;
  } catch (error) {
    ElMessage.error("加载活动列表失败");
  } finally {
    loading.value = false;
  }
};

// 搜索
const handleSearch = () => {
  pageNum.value = 1;
  loadData();
};

// 分页改变
const handlePageChange = (page) => {
  pageNum.value = page;
  loadData();
};

const handleSizeChange = (size) => {
  pageSize.value = size;
  pageNum.value = 1;
  loadData();
};

// 添加
const handleAdd = () => {
  isEdit.value = false;
  dialogTitle.value = "添加新活动";
  // 重置表单数据，确保 id 和 time 为空
  formData.value = {
    title: "",
    image: "",
    detail: "",
    time: "",
  };
  dialogVisible.value = true;
};

// 编辑
const handleEdit = (row) => {
  isEdit.value = true;
  dialogTitle.value = "编辑活动";
  // 填充表单数据，包括 id 和 time
  formData.value = {
    id: row.id, // 仅内部使用，不显示在表单中
    title: row.title,
    image: row.image,
    detail: row.detail,
    time: row.time,
  };
  dialogVisible.value = true;
};

// 删除
const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

const confirmDelete = async () => {
  try {
    await deleteEvent(currentDeleteRow.value.id);
    ElMessage.success("删除成功");
    loadData();
  } catch (error) {
    ElMessage.error("删除失败");
  }
};

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      // 深拷贝表单数据，避免修改原对象
      const submitData = { ...formData.value };

      if (!isEdit.value) {
        // 新增时：删除 id 和 time 字段，不传递给后端
        delete submitData.id;
        delete submitData.time;
      }

      try {
        if (isEdit.value && submitData.id) {
          await updateEvent(submitData);
          ElMessage.success("活动更新成功");
        } else {
          await createEvent(submitData);
          ElMessage.success("活动创建成功");
        }
        dialogVisible.value = false;
        loadData();
      } catch (error) {
        ElMessage.error(`${isEdit.value ? "更新" : "创建"}活动失败`);
      }
    }
  });
};

// 初始化加载
onMounted(() => {
  loadData();
});
</script>

<template>
  <div class="event-admin">
    <!-- 搜索和操作栏 -->
    <div class="admin-header">
      <div class="header-content">
        <h1 class="admin-title">活动管理</h1>
        <p class="admin-subtitle">管理所有活动和事件</p>
      </div>

      <div class="header-actions">
        <div class="search-filters">
          <el-input
            v-model="searchName"
            placeholder="搜索活动..."
            class="search-input"
            clearable
            @clear="handleSearch"
          >
            <template #prefix>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="16"
                height="16"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              >
                <circle cx="11" cy="11" r="8"></circle>
                <path d="m21 21-4.3-4.3"></path>
              </svg>
            </template>
          </el-input>

          <el-button type="primary" class="search-btn" @click="handleSearch">
            搜索
          </el-button>
        </div>

        <el-button type="primary" class="add-btn" @click="handleAdd">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="16"
            height="16"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M5 12h14"></path>
            <path d="M12 5v14"></path>
          </svg>
          添加活动
        </el-button>
      </div>
    </div>

    <!-- 数据表格 -->
    <div class="admin-table-container">
      <el-table
        v-loading="loading"
        :data="eventList"
        class="admin-table"
        stripe
      >
        <el-table-column label="图片" width="120">
          <template #default="{ row }">
            <img :src="row.image" :alt="row.title" class="event-image" />
          </template>
        </el-table-column>

        <el-table-column prop="title" label="标题" min-width="150" />

        <el-table-column
          prop="detail"
          label="详情"
          min-width="180"
          show-overflow-tooltip
        />

        <el-table-column prop="time" label="活动时间" width="100" />

        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <div class="table-actions">
              <button class="action-btn edit-btn" @click="handleEdit(row)">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="14"
                  height="14"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <path
                    d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5Z"
                  ></path>
                  <path d="m15 5 4 4"></path>
                </svg>
                编辑
              </button>
              <button class="action-btn delete-btn" @click="handleDelete(row)">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="14"
                  height="14"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <path d="M3 6h18"></path>
                  <path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"></path>
                  <path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"></path>
                </svg>
                删除
              </button>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handlePageChange"
        />
      </div>
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      class="event-dialog"
      width="600px"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        class="event-form"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="formData.title" placeholder="请输入活动标题" />
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
            placeholder="请输入活动详情"
          />
        </el-form-item>

        <el-form-item label="活动时间" prop="time" v-if="isEdit">
          <el-date-picker
            v-model="formData.time"
            type="datetime"
            placeholder="选择日期和时间"
            format="YYYY-MM-DD HH:mm:ss"
            value-format="YYYY-MM-DD HH:mm:ss"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">
            {{ isEdit ? "更新" : "创建" }}
          </el-button>
        </div>
      </template>
    </el-dialog>

    <ConfirmDeleteDialog
      v-model="deleteDialogVisible"
      title="删除确认"
      message="确定要删除该活动吗？删除后将无法恢复。"
      @confirm="confirmDelete"
    />
  </div>
</template>

<style scoped>
.event-admin {
  min-height: 100vh;
  background: #f5f7fa;
  padding: 24px;
}

.admin-header {
  margin-bottom: 24px;
}

.header-content {
  margin-bottom: 20px;
}

.admin-title {
  font-size: 32px;
  font-weight: 600;
  color: #1a1a1a;
  margin: 0 0 8px 0;
}

.admin-subtitle {
  font-size: 14px;
  color: #666666;
  margin: 0;
}

.header-actions {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
  flex-wrap: wrap;
}

.search-filters {
  display: flex;
  gap: 12px;
  flex: 1;
  max-width: 800px;
}

.search-input {
  flex: 1;
  min-width: 200px;
}

.add-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  background: #3b82f6;
  border-color: #3b82f6;
}

.add-btn:hover {
  background: #2563eb;
  border-color: #2563eb;
}

.admin-table-container {
  background: #ffffff;
  border-radius: 12px;
  padding: 24px;
  border: 1px solid #e5e7eb;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

.admin-table {
  background: transparent;
}

.admin-table :deep(.el-table__inner-wrapper::before) {
  display: none;
}

.admin-table :deep(.el-table__header) {
  background: #f9fafb;
}

.admin-table :deep(th.el-table__cell) {
  background: #f9fafb;
  color: #6b7280;
  font-weight: 500;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  border-bottom: 1px solid #e5e7eb;
}

.admin-table :deep(td.el-table__cell) {
  background: transparent;
  color: #1a1a1a;
  border-bottom: 1px solid #f3f4f6;
  vertical-align: middle;
}

.event-image {
  width: 80px;
  height: 50px;
  object-fit: cover;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
}

.table-actions {
  display: flex;
  gap: 12px;
  align-items: center;
  justify-content: flex-start;
  flex-wrap: nowrap;
}

.pagination-container {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}

.pagination-container :deep(.el-pagination) {
  --el-pagination-bg-color: transparent;
  --el-pagination-text-color: #6b7280;
  --el-pagination-button-bg-color: transparent;
  --el-pagination-hover-color: #3b82f6;
}

.event-dialog :deep(.el-dialog) {
  background: #ffffff;
  border: 1px solid #e5e7eb;
}

.event-dialog :deep(.el-dialog__header) {
  border-bottom: 1px solid #e5e7eb;
  padding: 20px 24px;
}

.event-dialog :deep(.el-dialog__title) {
  color: #1a1a1a;
  font-weight: 600;
  font-size: 20px;
}

.event-dialog :deep(.el-dialog__body) {
  padding: 24px;
}

.event-dialog :deep(.el-form-item__label) {
  color: #374151;
}

.event-dialog :deep(.el-input__wrapper) {
  background: #ffffff;
  border: 1px solid #d1d5db;
  box-shadow: none;
}

.event-dialog :deep(.el-input__inner) {
  color: #1a1a1a;
}

.event-dialog :deep(.el-textarea__inner) {
  background: #ffffff;
  border: 1px solid #d1d5db;
  color: #1a1a1a;
}

.event-dialog :deep(.el-select .el-input__wrapper) {
  background: #ffffff;
  border: 1px solid #d1d5db;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 16px 24px;
  border-top: 1px solid #e5e7eb;
}

/* Element Plus light theme overrides */
:deep(.el-input__wrapper) {
  background: #ffffff;
  border: 1px solid #d1d5db;
  box-shadow: none;
}

:deep(.el-input__inner) {
  color: #1a1a1a;
}

:deep(.el-input__wrapper:hover) {
  border-color: #9ca3af;
}

:deep(.el-input__wrapper.is-focus) {
  border-color: #3b82f6;
}

:deep(.el-button) {
  border-radius: 8px;
  font-weight: 500;
}

:deep(.el-button--primary) {
  background: #3b82f6;
  border-color: #3b82f6;
}

:deep(.el-button--primary:hover) {
  background: #2563eb;
  border-color: #2563eb;
}

/* 图标按钮样式 */
.action-btn {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  background: none;
  border: none;
  cursor: pointer;
  font-size: 14px;
  font-weight: 400;
  padding: 0;
  transition: opacity 0.2s;
  white-space: nowrap;
  flex-shrink: 0;
}

.action-btn:hover {
  opacity: 0.8;
}

.edit-btn {
  color: #3b82f6;
}

.delete-btn {
  color: #ef4444;
}

.action-btn svg {
  flex-shrink: 0;
}
</style>
