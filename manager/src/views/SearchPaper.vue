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
      <el-table-column prop="id" label="ID" width="40" align="center" />
      <el-table-column prop="title" label="论文名称" width="240" />
      <el-table-column prop="author" label="论文作者" width="150" />
      <el-table-column prop="detail" label="论文内容" width="670" />
      <el-table-column prop="time" label="上传时间" width="150" sortable />
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

    <!-- Added dialog for add/edit with Quill editor -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="800px"
      @close="handleDialogClose"
    >
      <el-form :model="form" label-width="80px">
        <el-form-item label="论文名称">
          <el-input v-model="form.title" placeholder="请输入论文名称" />
        </el-form-item>
        <el-form-item label="论文作者">
          <el-input v-model="form.author" placeholder="请输入作者名称" />
        </el-form-item>
        <el-form-item label="论文内容" v-model="form.detail" class="editor-form-item">
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
import { ref, reactive, nextTick, onMounted,watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import Quill from 'quill'
import 'quill/dist/quill.snow.css'
import {GetPaperList,AddPaper,UpdatePaper,DeletePaper} from '@/api/SearchApi.js'
// 搜索表单
const searchForm = reactive({
  title: '',
  author: ''
})

// 分页参数
const pageNum = ref(1)
const pageSize = ref(3)
const total = ref(5)


// 表格数据
const tableData = ref([])
//获取论文列表
const GetAllSearchProject = async ()=>{
    
  const response = await GetPaperList(pageNum.value,pageSize.value,searchForm.title,searchForm.author)
  tableData.value = response.data.rows;
  total.value = response.data.total;
}
//新增论文
const AddSearchPaper = async (...data)=>{
  console.log('新增论文:',data)
  const response = await AddPaper(data)
  if(response.code === 1){
    ElMessage.success('新增成功')
  }else{
    ElMessage.error('新增失败')
  }
}
//修改论文
const EditSearchPaper = async (...data)=>{
  console.log('修改论文文本中:',data)
  const response = await UpdatePaper(data)
  if(response.code === 1){
    ElMessage.success('修改成功')
  }else{
    ElMessage.error('修改失败')
  }
}
//删除论文
const DeleteSearchPaper = async (id)=>{
  console.log('删除论文文本中',id)
  const response = await DeletePaper(id)
  if(response.code === 1){
    ElMessage.success('删除成功')
  }else{
    ElMessage.erroe('删除失败')
  }
}


onMounted( ()=>{
  GetAllSearchProject()
})
//监视每页页数和当前页数
watch([pageNum, pageSize], () => {
  GetAllSearchProject();
})


const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const editId = ref(null)
const quillEditor = ref(null)
let quillInstance = null

const form = reactive({
  title: '',
  author: '',
  detail: ''
})

const initQuillEditor = () => {
  nextTick(() => {
    if (quillEditor.value) {
      // Destroy existing Quill instance if it exists
      if (quillInstance) {
        quillInstance.off('text-change')
        const toolbar = quillInstance.getModule('toolbar')
        if (toolbar) {
          toolbar.container.remove()
        }
        quillInstance = null
      }
      
      // Clear the DOM element completely
      quillEditor.value.innerHTML = ''
      
      // Create new Quill instance
      quillInstance = new Quill(quillEditor.value, {
        theme: 'snow',
        modules: {
          toolbar: [
            [{ 'header': [1, 2, 3, false] }],
            ['bold', 'italic', 'underline', 'strike'],
            [{ 'list': 'ordered'}, { 'list': 'bullet' }],
            [{ 'color': [] }, { 'background': [] }],
            [{ 'align': [] }],
            ['link', 'image'],
            ['clean']
          ]
        },
        placeholder: '请输入论文内容...'
      })
    }
  })
}

// 搜索
const handleSearch = () => {
  GetAllSearchProject();
}

const handleAdd = () => {
  dialogTitle.value = '新增论文'
  isEdit.value = false
  form.title = ''
  form.author = ''
  form.detail = ''
  dialogVisible.value = true
  initQuillEditor()
  nextTick(() => {
    if (quillInstance) {
      quillInstance.setContents([])
    }
  })
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑论文'
  isEdit.value = true
  editId.value = row.id
  form.title = row.title
  form.author = row.author
  form.detail = row.detail || ''
  dialogVisible.value = true
 
  
  initQuillEditor()
  nextTick(() => {
    if (quillInstance) {
      quillInstance.clipboard.dangerouslyPasteHTML(form.detail)
    }
  })
  
}

const handleDialogClose = () => {
  if (quillInstance) {
    // Remove all event listeners
    quillInstance.off('text-change')
    // Get and remove toolbar
    const toolbar = quillInstance.getModule('toolbar')
    if (toolbar && toolbar.container) {
      toolbar.container.remove()
    }
    // Set instance to null
    quillInstance = null
  }
  // Clear the editor div content
  if (quillEditor.value) {
    quillEditor.value.innerHTML = ''
  }
  // Reset form
  form.title = ''
  form.author = ''
  form.detail = ''
}


// 提交新增论文
const handleSubmit = () => {
  if (!form.title || !form.author) {
    ElMessage.warning('请填写论文名称和作者')
    return
  }
  
  if (quillInstance) {
    form.detail = quillInstance.root.innerHTML
  }
  console.log("新增论文参数",form)
  //清除form.detail的HTML标签，保留文本内容a
  const tempDiv = document.createElement('div');
  tempDiv.innerHTML = form.detail;
  form.detail = tempDiv.textContent || tempDiv.innerText || '';
  //如果是编辑则调用修改接口
  if(isEdit.value){
    //编辑接口
    EditSearchPaper(editId.value,form.title,form.author,form.detail)
  }else{
    AddSearchPaper(form.title,form.author,form.detail);
  }
  

  dialogVisible.value = false
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
    DeleteSearchPaper(row.id)
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 分页变化
const handlePageChange = (page) => {
  console.log('当前页:', page)
}

// 每页条数变化
const handlePageSizeChange = (size) => {
  console.log('每页条数:', size)
  pageNum.value = 1
}
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
:deep(.editor-form-item) {
  display: block;
}

:deep(.editor-form-item .el-form-item__content) {
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
