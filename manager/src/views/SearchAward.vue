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
      <el-table-column label="ID" width="80" align="center">
        <template #default="{ row }">
          {{ row.calculatedIndex }}
        </template>
      </el-table-column>
      <el-table-column prop="detail" label="获奖内容" min-width="200" />
      <el-table-column prop="author" label="论文作者" width="150" />
      <el-table-column prop="file" label="照片" width="120">
        <template #default="{ row }">
          <el-image
            :src="row.file"
            :preview-src-list="[row.file]"
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
          <el-input v-model="form.detail" placeholder="请输入获奖内容" />
        </el-form-item>

        <el-form-item label="获奖人">
          <el-input v-model="form.author" placeholder="请输入获奖人名称" />
        </el-form-item>
        <el-form-item label="获奖时间">
          <el-date-picker
            v-model="form.time"
            type="date"
            placeholder="选择日期"
            value-format="YYYY-MM-DD"
            format="YYYY-MM-DD"
            clearable
          />
        </el-form-item>
      <el-form-item label="封面图" prop="file">
          <el-upload
            class="upload-demo"
            :before-upload="beforeUpload"
            :show-file-list="false"
          >
            <el-button type="primary">选择图片</el-button>
          </el-upload>
          <!-- 实时显示上传的图片 -->
          <div v-if="form.file" style="margin-top: 10px">
            <el-image
              :src="form.file"
              fit="cover"
              style="width: 100px; height: 80px; border-radius: 4px"
            />
          </div>
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
import { ref, reactive, nextTick, onMounted, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { GetAwardList, AddAward, UpdateAward, DeleteAward } from '@/api/SearchApi.js'

import { uploadImage } from "@/api/upload";

import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";
const deleteDialogVisible = ref(false);
const currentDeleteRow = ref(null);



// 搜索表单
const searchForm = reactive({
  detail: '',
  author: '',
  time: ''
})

// 分页参数
const pageNum = ref(1)
const pageSize = ref(3)
const total = ref(5)

// 表格数据
const tableData = ref([])

// 获取表格数据
const GetAllAwardData = async (pageNum, pageSize, author) => {
  console.log('获取获奖列表文本处:', pageNum, pageSize, author)
  const response = await GetAwardList(pageNum, pageSize, author)
  console.log('response:',response)
  
  tableData.value = response.data.data.map((item, index) => ({
    ...item,
    calculatedIndex: (pageNum - 1) * pageSize + index + 1
  }))
  total.value = response.data.total  // 假设返回的总条数在 total 字段中

  if (response.code === 1) {
    ElMessage.success('获取获奖列表成功')
  } else {
    ElMessage.error('获取获奖列表失败')
  }
}

// 新增获奖
const AddAwardData = async (detail, author, file, time) => {
  console.log("获取新增获奖文本处:", detail, author, file, time)
  const response = await AddAward(detail, author, file, time)
  if (response.code === 1) {
    ElMessage.success('新增获奖成功')
    pageNum.value = 1  
    GetAllAwardData(pageNum.value,pageSize.value,'') // 刷新表格数据
  } else {
    ElMessage.error('新增获奖失败')
  }
}

// 编辑获奖
const EditAwardData = async (id, detail, author, file, time) => {
  console.log("获取编辑获奖文本处:", id, detail, author, file, time)
  const response = await UpdateAward(id, detail, author, file, time)
  if (response.code === 1) {
    ElMessage.success('编辑获奖成功')
    GetAllAwardData(1,pageSize.value,'') // 刷新表格数据
  } else {
    ElMessage.error('编辑获奖失败')
  }
}

// 删除获奖
const DeleteAwardData = async (id) => {
  console.log("获取删除获奖文本处:", id)
  const response = await DeleteAward(id)
  if (response.code === 1) {
    ElMessage.success('删除获奖成功')
    pageNum.value = 1  
    GetAllAwardData(pageNum.value,pageSize.value,'') // 刷新表格数据
  } else {
    ElMessage.error('删除获奖失败')
  }
}
const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
}
onMounted(() => {
  GetAllAwardData(pageNum.value, pageSize.value, searchForm.author)
})

watch([pageNum, pageSize], () => {

  GetAllAwardData(pageNum.value, pageSize.value, searchForm.author)
})


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
      form.file = response.data;
      ElMessage.success("图片上传成功!");
    }
  } catch (error) {
    ElMessage.error("图片上传失败!");
    console.error(error);
  }

  return false;
};




const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)
const editId = ref(null)

const form = reactive({
  detail: '',
  author: '',
  file: '',
  time: ''
})



// 搜索
const handleSearch = () => {
  pageNum.value = 1
  GetAllAwardData(pageNum.value, pageSize.value, searchForm.author)
  // 清空
  searchForm.detail = ''
  searchForm.author = ''
  searchForm.time = ''
}

const handleAdd = () => {
  dialogTitle.value = '新增获奖'
  isEdit.value = false
  form.detail = ''
  form.author = ''
  form.file = ''
  form.time = ''
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
  editId.value = row.id
  form.detail = row.detail
  form.author = row.author
  form.file = row.file || ''
  form.time = row.time || ''  // 添加这行代码
  dialogVisible.value = true
 
}

const handleDialogClose = () => {

  // Reset form
  form.detail = ''
  form.author = ''
  form.file = ''
  form.time = ''  // 添加这行代码
  // 确保日期格式正确
  if (form.time && typeof form.time === 'string') {
    // 如果已经是字符串格式，确保格式正确
    form.time = form.time
  } else if (form.time instanceof Date) {
    // 如果是Date对象，格式化为 YYYY-MM-DD
    const year = form.time.getFullYear()
    const month = String(form.time.getMonth() + 1).padStart(2, '0')
    const day = String(form.time.getDate()).padStart(2, '0')
    form.time = `${year}-${month}-${day}`
  }
}

const handleSubmit = () => {
  if (!form.detail || !form.author || !form.time) {
    ElMessage.warning('请填写获奖名称、获奖人和获奖时间')
    return
  }
  
  // 格式化日期为 YYYY-MM-DD 格式
  let formattedTime = form.time
  if (form.time && typeof form.time === 'string') {
    // 如果已经是字符串格式，确保格式正确
    formattedTime = form.time
  } else if (form.time instanceof Date) {
    // 如果是Date对象，格式化为 YYYY-MM-DD
    const year = form.time.getFullYear()
    const month = String(form.time.getMonth() + 1).padStart(2, '0')
    const day = String(form.time.getDate()).padStart(2, '0')
    formattedTime = `${year}-${month}-${day}`
  }
  

  
  if (isEdit.value) {
    EditAwardData(editId.value, form.detail, form.author, form.file, formattedTime)
  } else {
    AddAwardData(form.detail, form.author, form.file, formattedTime)
  }
  
  dialogVisible.value = false
}

// 删除
const confirmDelete = () => {
  DeleteAwardData(currentDeleteRow.value.id)
  deleteDialogVisible.value = false
}


// 每页条数变化
const handlePageSizeChange = (size) => {
  console.log('每页条数:', size)
  pageNum.value = 1  // 修正这里，应该是 pageNum 而不是 currentPage
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