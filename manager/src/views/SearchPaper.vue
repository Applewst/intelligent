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
      <el-table-column prop="id" label="ID" width="60" align="center" />
      <el-table-column prop="title" label="论文名称" width="240" />
      <el-table-column prop="author" label="论文作者" width="100" />
      <el-table-column prop="detail" label="论文内容" width="600" />
      <el-table-column prop="files" label="论文文件" width="60">
        <template #default="{ row }">
          <el-link v-if="row.files" type="primary" :href="row.files" target="_blank" download>下载</el-link>
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

    <!-- Added dialog for add/edit with Quill editor and file upload -->
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
        <el-form-item label="照片" prop="file">
          <el-input v-model="form.file" placeholder="请输入照片URL" />
          <div v-if="form.file" style="margin-top: 10px">
            <el-image
              :src="form.file"
              fit="cover"
              style="width: 100px; height: 100px; border-radius: 4px"
            />
          </div>
        </el-form-item>
        <!-- <el-form-item label="论文文件" prop="files">
          <el-upload
            class="upload-demo"
            action=""
            :http-request="uploadFile"
            :before-upload="beforeUploadFile"
            :on-success="handleSuccessFile"
            :on-error="handleErrorFile"
            :file-list="form.files"
            accept=".pdf"
            :limit="1"
            :on-exceed="handleExceedFile"
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                只能上传PDF文件，且不超过100MB
              </div>
            </template>
          </el-upload>
        </el-form-item> -->
        <el-form-item label="论文内容" prop="detail" class="editor-form-form">
          <div ref="quillEditor" class="quill-editor"></div>
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
import { ref, reactive, nextTick, onMounted, watch } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import Quill from 'quill';
import 'quill/dist/quill.snow.css';
import axios from 'axios';
import { GetPaperList, AddPaper, UpdatePaper, DeletePaper } from '@/api/SearchApi.js';

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
  console.log('获取论文列表文本处',response)
  
  tableData.value = response.data.data;
  total.value = response.data.total;
};

// 新增论文
const AddSearchPaper = async (title, author, detail, file, files) => {
  console.log('新增论文:', title, author, detail, file, files);
  const response = await AddPaper(title, author, detail, file, files);
  if (response.code === 1) {
    ElMessage.success('新增成功');
    GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
  } else {
    ElMessage.error('新增失败');
  }
};

// 修改论文
const EditSearchPaper = async (id, title, author, detail, file, files) => {
  console.log('修改论文文本中:', id, title, author, detail, file, files);
  const response = await UpdatePaper(id, title, author, detail, file, files);
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
    GetAllSearchProject(pageNum.value, pageSize.value, searchForm.author, searchForm.title);
  } else {
    ElMessage.error('删除失败');
  }
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
const quillEditor = ref(null);
let quillInstance = null;

const form = reactive({
  title: '',
  author: '',
  detail: '',
  file: '', // 论文图片列表
  files: [] // 论文文件列表
});

const rules = reactive({
  title: [{ required: true, message: '请输入论文名称', trigger: 'blur' }],
  author: [{ required: true, message: '请输入作者', trigger: 'blur' }],
  file: [{ required: true, message: '请上传论文图片', trigger: 'change' }],
  files: [{ required: true, message: '请上传论文文件', trigger: 'change' }],
});

// 上传文件之前的验证
const beforeUploadFile = (file) => {
  const isPDF = file.type === 'application/pdf';
  const isLt100M = file.size / 1024 / 1024 < 100;
  if (!isPDF) {
    ElMessage.error('只能上传PDF文件!');
  }
  if (!isLt100M) {
    ElMessage.error('文件大小不能超过100MB!');
  }
  return isPDF && isLt100M;
};

// 自定义上传文件逻辑
const uploadFile = (options) => {
  const formData = new FormData();
  formData.append('file', options.file);
  // 假设这是你的上传API地址
  axios.post('/api/paper/uploadFile', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  }).then((response) => {
    if (response.data.code === 1) {
      ElMessage.success('上传文件成功');
      // 将上传成功的文件信息添加到表单中
      form.files = [{ name: options.file.name, url: response.data.data.url }];
    } else {
      ElMessage.error('上传文件失败');
    }
  }).catch((error) => {
    ElMessage.error('上传文件失败');
    console.error(error);
  });
};

// 文件上传成功的处理
const handleSuccessFile = (response, file, fileList) => {
  ElMessage.success('文件上传成功');
  form.files = fileList;
};

// 文件上传失败的处理
const handleErrorFile = (err, file, fileList) => {
  ElMessage.error('文件上传失败');
  console.error(err);
};

// 文件超出限制时的处理
const handleExceedFile = (files, fileList) => {
  ElMessage.warning(`当前限制选择1个文件，本次选择了 ${files.length} 个文件`);
};

// 上传图片之前的验证
const beforeUploadImage = (file) => {
  const isImage = file.type.startsWith('image/');
  const isLt10M = file.size / 1024 / 1024 < 10;
  if (!isImage) {
    ElMessage.error('只能上传图片文件!');
  }
  if (!isLt10M) {
    ElMessage.error('文件大小不能超过10MB!');
  }
  return isImage && isLt10M;
};

// 自定义上传图片逻辑
const uploadImage = (options) => {
  const formData = new FormData();
  formData.append('image', options.file);
  // 假设这是你的上传API地址
  axios.post('/api/paper/uploadImage', formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  }).then((response) => {
    if (response.data.code === 1) {
      ElMessage.success('上传图片成功');
      // 将上传成功的文件信息添加到表单中
      form.file = [{ name: options.file.name, url: response.data.data.url }];
    } else {
      ElMessage.error('上传图片失败');
    }
  }).catch((error) => {
    ElMessage.error('上传图片失败');
    console.error(error);
  });
};

// 图片上传成功的处理
const handleSuccessImage = (response, file, fileList) => {
  ElMessage.success('图片上传成功');
  form.file = fileList;
};

// 图片上传失败的处理
const handleErrorImage = (err, file, fileList) => {
  ElMessage.error('图片上传失败');
  console.error(err);
};

// 图片超出限制时的处理
const handleExceedImage = (files, fileList) => {
  ElMessage.warning(`当前限制选择1个文件，本次选择了 ${files.length} 个文件`);
};

// 提交表单
const handleSubmit = () => {
  
      if (isEdit.value) {
        // 编辑接口
        EditSearchPaper(editId.value, form.title, form.author, form.detail, form.file);
      } else {
        AddSearchPaper(form.title, form.author, form.detail, form.file);
      }
      dialogVisible.value = false;
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除论文"${row.title}"吗？`,
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    DeleteSearchPaper(row.id);
  }).catch(() => {
    ElMessage.info('已取消删除');
  });
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

// 新增论文
const handleAdd = () => {
  dialogTitle.value = '新增论文';
  isEdit.value = false;
  form.title = '';
  form.author = '';
  form.file = '';
  form.files = [];
  form.detail = '';
  dialogVisible.value = true;
  initQuillEditor();
  nextTick(() => {
    if (quillInstance) {
      quillInstance.setContents([]);
    }
  });
};

// 编辑论文
const handleEdit = (row) => {
  dialogTitle.value = '编辑论文';
  isEdit.value = true;
  editId.value = row.id;
  form.title = row.title;
  form.file = row.file 
  form.files = row.files ? [{ name: row.files, url: row.files }] : [];
  form.author = row.author;
  form.detail = row.detail || '';
  dialogVisible.value = true;

  initQuillEditor();
  nextTick(() => {
    if (quillInstance) {
      quillInstance.clipboard.dangerouslyPasteHTML(form.detail);
    }
  });
};

// 关闭对话框
const handleDialogClose = () => {
  if (quillInstance) {
    // Remove all event listeners
    quillInstance.off('text-change');
    // Get and remove toolbar
    const toolbar = quillInstance.getModule('toolbar');
    if (toolbar && toolbar.container) {
      toolbar.container.remove();
    }
    // Set instance to null
    quillInstance = null;
  }
  // Clear the editor div content
  if (quillEditor.value) {
    quillEditor.value.innerHTML = '';
  }
  // Reset form
  form.title = '';
  form.author = '';
  form.detail = '';
  form.file = [];
  form.files = [];
};

// 初始化 Quill 编辑器
const initQuillEditor = () => {
  nextTick(() => {
    if (quillEditor.value) {
      // Destroy existing Quill instance if it exists
      if (quillInstance) {
        quillInstance.off('text-change');
        const toolbar = quillInstance.getModule('toolbar');
        if (toolbar) {
          toolbar.container.remove();
        }
        quillInstance = null;
      }

      // Clear the DOM element completely
      quillEditor.value.innerHTML = '';

      // Create new Quill instance
      quillInstance = new Quill(quillEditor.value, {
        theme: 'snow',
        modules: {
          toolbar: [
            [{ 'header': [1, 2, 3, false] }],
            ['bold', 'italic', 'underline', 'strike'],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'align': '' }],
            ['link', 'image'],
            ['clean']
          ]
        },
        placeholder: '请输入论文内容...'
      });
    }
  });
};
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

/* Added styles to fix el-form-item flex layout issue */
:deep(.editor-form-form) {
  display: block;
}

:deep(.editor-form-form .el-form-item__content) {
  display: block;
  margin-left: 0 !important;
}

/* Added styles for Quill editor */
.quill-editor {
  height: 300px;
  /* Added width to ensure full width */
  width: 100%;
}

:deep(.ql-container) {
  height: 250px;
  font-size: 14px;
}

:deep(.ql-toolbar) {
  border-top-left-radius: 4px;
  border-top-right-radius: 4px;
}

:deep(.ql-container) {
  border-bottom-left-radius: 4px;
  border-bottom-right-radius: 4px;
}
</style>
