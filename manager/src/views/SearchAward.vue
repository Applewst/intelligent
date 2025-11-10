<template>
  <div class="paper-management">
    <!-- 顶部标题和新增按钮 -->
    <div class="header">
      <h2 class="title">学生获奖管理</h2>
      <el-button type="primary" @click="handleAdd">新增</el-button>
    </div>

    <!-- 搜索表单 -->
    <div class="search-form">
      <div class="search-item">
        <label>名称</label>
        <el-input
          v-model="searchForm.detail"
          placeholder="请输入获奖名称"
          clearable
        />
      </div>
      <div class="search-item">
        <label>作者</label>
        <el-input
          v-model="searchForm.author"
          placeholder="请输入获奖人名称"
          clearable
        />
      </div>
      <el-button type="primary" @click="handleSearch" class="search-btn">
        搜索
      </el-button>
    </div>

    <!-- 数据表格 -->
    <el-table :data="tableData" border class="data-table">
      <el-table-column prop="id" label="ID" width="80" align="center" />
      <el-table-column prop="detail" label="获奖内容" min-width="200" />
      <el-table-column prop="author" label="论文作者" width="150" />
      <el-table-column prop="image" label="照片" width="120">
        <template #default="{ row }">
          <el-image
            :src="row.image"
            :preview-src-list="[row.image]"
            fit="cover"
            style="width: 60px; height: 60px; border-radius: 4px"
          />
        </template>
      </el-table-column>   
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
      <span class="total">Total {{ total }}</span>
      <el-select v-model="pageSize" class="page-size-select" @change="handlePageSizeChange">
        <el-option label="3/page" :value="3" />
        <el-option label="5/page" :value="5" />
        <el-option label="10/page" :value="10" />
        <el-option label="20/page" :value="20" />
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
        <el-form-item label="获奖内容">
          <el-input v-model="form.detail" placeholder="请输入论文名称" />
        </el-form-item>
        <el-form-item label="获奖人">
          <el-input v-model="form.author" placeholder="请输入作者名称" />
        </el-form-item>
        <el-form-item label="照片" prop="image">
          <el-input v-model="form.image" placeholder="请输入照片URL" />
          <div v-if="form.image" style="margin-top: 10px">
            <el-image
              :src="form.image"
              fit="cover"
              style="width: 100px; height: 100px; border-radius: 4px"
            />
          </div>
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
import { GetAwardList, AddAward, UpdateAward, DeleteAward  } from '@/api/SearchApi.js'
import Quill from 'quill'
import 'quill/dist/quill.snow.css'

// 搜索表单
const searchForm = reactive({
  detail: '',
  author: ''
})

// 分页参数
const pageNum = ref(1)
const pageSize = ref(3)
const total = ref(5)

// 表格数据
const tableData = ref([
])

// 获取表格数据
const GetAllAwardData = async () => {
  console.log('获取获奖列表文本处:', pageNum.value,pageSize.value,searchForm.detail,searchForm.author)
  const response = await GetAwardList(pageNum.value,pageSize.value,searchForm.detail,searchForm.author)
  tableData.value = response.data.rows
  if (response.code === 1) {
    ElMessage.success('获取获奖列表成功')
  }else{
    ElMessage.error('获取获奖列表失败')
  }
}
//新增获奖
const AddAwardData = async (...data) => {
  console.log("获取新增获奖文本处:", ...data)
  const response = await AddAward(...data)
  if (response.code === 1) {
    ElMessage.success('新增获奖成功')
  }else{
    ElMessage.error('新增获奖失败')
  }
}
//编辑获奖
const EditAwardData = async (...data) => {
  console.log("获取编辑获奖文本处:", ...data)
  const response = await UpdateAward(...data)
  if (response.code === 1) {
    ElMessage.success('编辑获奖成功')
  }else{
    ElMessage.error('编辑获奖失败')
  }
}
//删除获奖
const DeleteAwardData = async (id) => {
  console.log("获取删除获奖文本处:", id)
  const response = await DeleteAward(id)
  if (response.code === 1) {
    ElMessage.success('删除获奖成功')
  }else{
    ElMessage.error('删除获奖失败')
  }
}

onMounted(() => {
  GetAllAwardData()
})
watch([pageNum,pageSize],()=>{
  GetAllAwardData()
})


const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const editId = ref(null)
const quillEditor = ref(null)
let quillInstance = null

const form = reactive({
  detail: '',
  author: '',
  image: ''
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
  pageNum.value = 1
  GetAllAwardData()
  //清空
  searchForm.detail = ''
  searchForm.author = ''
}


const handleAdd = () => {
  dialogTitle.value = '新增获奖'
  isEdit.value = false
  form.detail = ''
  form.author = ''
  form.image = ''
  dialogVisible.value = true
  initQuillEditor()
  nextTick(() => {
    if (quillInstance) {
      quillInstance.setContents([])
    }
  })
}

const handleEdit = (row) => {
  dialogTitle.value = '编辑获奖'
  isEdit.value = true
  editId.id = row.id
  form.detail = row.detail
  form.author = row.author
  form.image = row.image || ''
  dialogVisible.value = true
  initQuillEditor()
  nextTick(() => {
    if (quillInstance) {
      quillInstance.clipboard.dangerouslyPasteHTML(form.content)
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
  form.detail = ''
  form.author = ''
  form.image = ''
}

const handleSubmit = () => {
  if (!form.detail || !form.author) {
    ElMessage.warning('请填写获奖名称和获奖人')
    return
  }
  
  if (quillInstance) {
    
  }
  
  if (isEdit.value) {
   
    EditAwardData(editId.id, form.detail, form.author, form.image)
  } else {
    console.log("新增Bug");
    AddAwardData(form.detail, form.author, form.image)
  }
  
  dialogVisible.value = false
}

// 删除
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除论文"${row.detail}"吗？`,
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    DeleteAwardData(row.id)
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
  currentPage.value = 1
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
