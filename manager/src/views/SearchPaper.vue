<template>
  <div class="paper-management">
    <!-- 顶部标题和新增按钮 -->
    <div class="header">
      <h2 class="title">论文管理</h2>
      <el-button type="primary" @click="handleAdd">新增</el-button>
    </div>

    <!-- 搜索表单 -->
    <div class="search-form">
      <div class="search-item">
        <label>名称</label>
        <el-input
          v-model="searchForm.title"
          placeholder="请输入论文名称"
          clearable
        />
      </div>
      <div class="search-item">
        <label>作者</label>
        <el-input
          v-model="searchForm.author"
          placeholder="请输入作者名称"
          clearable
        />
      </div>
      <el-button type="primary" @click="handleSearch" class="search-btn">
        搜索
      </el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="tableData" border class="data-table">
      <el-table-column label="ID" width="60" align="center">
        <template #default="{ row }">
          {{ row.calculatedIndex }}
        </template>
      </el-table-column>
      <el-table-column prop="title" label="论文名称" width="240" />
      <el-table-column prop="author" label="论文作者" width="100" />
      <el-table-column label="论文摘要" min-width="300" min-height="200">
        <template #default="{ row }">
          <div class="detail-cell" v-html="row.detail"></div>
        </template>
      </el-table-column>
      <el-table-column prop="file" label="论文图片" width="120">
        <template #default="{ row }">
          <el-image
            v-if="row.file"
            :src="row.file"
            :preview-src-list="[row.file]"
            fit="cover"
            style="width: 60px; height: 60px; border-radius: 4px"
          />
        </template>
      </el-table-column>
      <el-table-column prop="time" label="上传时间" width="100" sortable />
      <el-table-column label="操作" width="180" align="center">
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
      <span class="total">共 {{ total }}条</span>
      <el-select v-model="pageSize" class="page-size-select" @change="handlePageSizeChange">
        <el-option label="3个/页" :value="3" />
        <el-option label="5个/页" :value="5" />
        <el-option label="10个/页" :value="10" />
        <el-option label="20个/页" :value="20" />
      </el-select>
      <el-pagination
        v-model:current-page="pageNum"
        :page-size="pageSize"
        :total="total"
        layout="prev, pager, next, jumper"
        @current-change="handlePageChange"
      />
    </div>

    <!-- 对话框中使用封装的 RichTextEditor 组件 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="800px"
      @close="handleDialogClose"
    >
      <el-form :model="form" :rules="rules" ref="paperForm" label-width="80px">
        <el-form-item label="论文名称" prop="title">
          <el-input v-model="form.title" placeholder="请输入论文名称" />
        </el-form-item>
        <el-form-item label="论文作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者名称" />
        </el-form-item>
        <el-form-item label="封面图" prop="file">
          <el-upload
            class="upload-demo"
            :before-upload="beforeUpload"
            :show-file-list="false"
          >
            <el-button type="primary">选择图片</el-button>
          </el-upload>
          <div v-if="form.file" style="margin-top: 10px">
            <el-image
              :src="form.file"
              fit="cover"
              style="width: 100px; height: 80px; border-radius: 4px"
            />
          </div>
        </el-form-item>
        <!-- 使用 RichTextEditor 组件替换原有的 Quill 编辑器 -->
        <el-form-item label="论文摘要" prop="detail" class="editor-form-form">
          <RichTextEditor 
            ref="editorRef"
            v-model="form.detail"
            placeholder="请输入论文内容..."
            height="300px"
          />
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
import { ref, reactive, onMounted, watch } from 'vue';
import { ElMessage } from 'element-plus';
// 引入封装的 RichTextEditor 组件，移除 Quill 相关导入
import RichTextEditor from '@/components/RichTextEditor.vue';
import { GetPaperList, AddPaper, UpdatePaper, DeletePaper } from '@/api/SearchApi.js';
import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";
import { uploadImage } from "@/api/upload";

const deleteDialogVisible = ref(false);
const currentDeleteRow = ref(null);

// 搜索表单
const searchForm = reactive({
  title: '',
  author: ''
});

// 分页参数
const pageNum = ref(1);
const pageSize = ref(3);
const total = ref(0);

// 表格数据
const tableData = ref([]);

// 获取论文列表
const GetAllSearchProject = async (pageNum, pageSize, author, title) => {
  const response = await GetPaperList(pageNum, pageSize, author, title);
  console.log('获取论文列表文本处', response);
  tableData.value = response.data.data.map((item, index) => ({
    ...item,
    calculatedIndex: (pageNum - 1) * pageSize + index + 1
  }));
  total.value = response.data.total;
};

// 新增论文
const AddSearchPaper = async (title, author, detail, file) => {
  console.log('新增论文:', title, author, detail, file);
  const response = await AddPaper(title, author, detail, file);
  if (response.code === 1) {
    ElMessage.success('新增成功');
    pageNum.value = 1;
    GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
  } else {
    ElMessage.error('新增失败');
  }
};

// 修改论文
const EditSearchPaper = async (id, title, author, detail, file) => {
  console.log('修改论文文本中:', id, title, author, detail, file);
  const response = await UpdatePaper(id, title, author, detail, file);
  if (response.code === 1) {
    ElMessage.success('修改成功');
    GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
  } else {
    ElMessage.error('修改失败');
  }
};

// 删除论文
const DeleteSearchPaper = async (id) => {
  console.log('删除论文文本中', id);
  const response = await DeletePaper(id);
  if (response.code === 1) {
    ElMessage.success('删除成功');
    pageNum.value = 1;
    GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
  } else {
    ElMessage.error('删除失败');
  }
};

const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};

onMounted(() => {
  console.log('每次进入论文管理页面都会执行一次', pageNum.value, pageSize.value, searchForm.author, searchForm.title);
  GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
});

// 监视每页页数和当前页数
watch([pageNum, pageSize, searchForm.author, searchForm.title], () => {
  console.log('获取每次分页参数', pageNum.value, pageSize.value, searchForm.author, searchForm.title);
  GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
});

const dialogVisible = ref(false);
const dialogTitle = ref('');
const isEdit = ref(false);
const editId = ref(null);
// 使用 editorRef 替代原来的 quillEditor
const editorRef = ref(null);

const form = reactive({
  title: '',
  author: '',
  detail: '',
  file: '',
});

const rules = reactive({
  title: [{ required: true, message: '请输入论文名称', trigger: 'blur' }],
  author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  file: [{ required: true, message: '请上传论文图片', trigger: 'change' }],
});

const beforeUpload = async (rawFile) => {
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

  try {
    const response = await uploadImage(rawFile);
    if (response && response.data) {
      form.file = response.data;
      ElMessage.success("图片上传成功!");
    }
  } catch (error) {
    ElMessage.error("图片上传失败!");
    console.error(error);
  }

  return false;
};

// 简化提交逻辑，v-model 已自动同步 form.detail
const handleSubmit = () => {
  if (isEdit.value) {
    EditSearchPaper(editId.value, form.title, form.author, form.detail, form.file);
  } else {
    AddSearchPaper(form.title, form.author, form.detail, form.file);
  }
  dialogVisible.value = false;
};

// 删除确认
const confirmDelete = () => {
  DeleteSearchPaper(currentDeleteRow.value.id);
  deleteDialogVisible.value = false;
};

// 分页变化
const handlePageChange = (page) => {
  console.log('当前页:', page);
};

// 每页条数变化
const handlePageSizeChange = (size) => {
  console.log('每页条数:', size);
  pageNum.value = 1;
};

// 搜索
const handleSearch = () => {
  console.log('搜索获取到的数据', searchForm.author, searchForm.title);
  GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
};

// 简化新增逻辑，不再需要手动初始化 Quill
const handleAdd = () => {
  dialogTitle.value = '新增论文';
  isEdit.value = false;
  form.title = '';
  form.author = '';
  form.file = '';
  form.detail = '';  // 清空内容，v-model 会自动同步到编辑器
  dialogVisible.value = true;
};

// 简化编辑逻辑，不再需要手动设置 Quill 内容
const handleEdit = (row) => {
  dialogTitle.value = '编辑论文';
  isEdit.value = true;
  editId.value = row.id;
  form.title = row.title;
  form.file = row.file;
  form.author = row.author;
  form.detail = row.detail || '';  // 设置内容，v-model 会自动同步到编辑器
  dialogVisible.value = true;
};

// 简化关闭逻辑，不再需要手动清理 Quill 实例
const handleDialogClose = () => {
  form.title = '';
  form.author = '';
  form.detail = '';
  form.file = '';
};

// 删除 initQuillEditor 函数，组件内部已处理
</script>

<style scoped>
.paper-management {
  padding: 20px;
  background: #fff;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e8e8e8;
}

.title {
  margin: 0;
  font-size: 20px;
  font-weight: 500;
  color: #333;
}

.search-form {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 20px;
  padding: 20px;
  background: #fafafa;
  border-radius: 4px;
}

.search-item {
  display: flex;
  align-items: center;
  gap: 10px;
}

.search-item label {
  font-size: 14px;
  color: #666;
  white-space: nowrap;
}

.search-item .el-input {
  width: 200px;
}

.search-btn {
  border-radius: 20px;
  padding: 0 30px;
}

.data-table {
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
}

.total {
  font-size: 14px;
  color: #666;
}

.page-size-select {
  width: 100px;
}

/* 保留 editor-form-form 样式，删除所有 Quill 相关样式 */
:deep(.editor-form-form) {
  display: block;
}

:deep(.editor-form-form .el-form-item__content) {
  display: block;
  margin-left: 0 !important;
}

/* 表格中富文本内容的样式 */
.detail-cell {
  min-height: 120px;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 13px;
  line-height: 1.5;
}

.detail-cell :deep(p) {
  margin: 0 0 8px 0;
}

/* 添加 strong 加粗样式 */
.detail-cell :deep(strong) {
  font-weight: bold !important;
}

/* 添加 em 斜体样式 */
.detail-cell :deep(em) {
  font-style: italic !important;
}

/* 添加下划线样式 */
.detail-cell :deep(u) {
  text-decoration: underline !important;
}

/* 添加删除线样式 */
.detail-cell :deep(s),
.detail-cell :deep(strike) {
  text-decoration: line-through !important;
}

.detail-cell :deep(img) {
  max-width: 100%;
  max-height: 80px;
  object-fit: contain;
  border-radius: 4px;
}

.detail-cell :deep(h1),
.detail-cell :deep(h2),
.detail-cell :deep(h3),
.detail-cell :deep(h4),
.detail-cell :deep(h5),
.detail-cell :deep(h6) {
  margin: 0 0 8px 0;
  font-size: 14px;
  font-weight: 600;
}

.detail-cell :deep(ul),
.detail-cell :deep(ol) {
  margin: 0 0 8px 0;
  padding-left: 20px;
}

.detail-cell :deep(blockquote) {
  margin: 0 0 8px 0;
  padding-left: 10px;
  border-left: 3px solid #ddd;
  color: #666;
}

.detail-cell :deep(pre) {
  background: #f5f5f5;
  padding: 8px;
  border-radius: 4px;
  font-size: 12px;
  overflow: auto;
}

/* 添加代码样式 */
.detail-cell :deep(code) {
  background: #f5f5f5;
  padding: 2px 4px;
  border-radius: 3px;
  font-family: monospace;
}

/* 添加链接样式 */
.detail-cell :deep(a) {
  color: #409eff;
  text-decoration: underline;
}
</style>
