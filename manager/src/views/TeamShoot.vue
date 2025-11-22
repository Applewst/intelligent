<template>
  <div class="photo-management">
    <div class="header">
      <h2>照片管理</h2>
      <el-button type="primary" @click="handleAdd">新增</el-button>
    </div>

    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchTitle"
        placeholder="请输入主题搜索"
        clearable
        @clear="handleSearch"
        style="width: 300px; margin-right: 16px"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-button type="primary" @click="handleSearch">搜索</el-button>
    </div>

    <!-- 卡片列表 -->
    <div class="card-grid">
     <el-card 
          v-for="(item, index) in computedPhotoList" 
          :key="item.id" 
          class="photo-card"
          shadow="hover"
        >
          <div class="card-content">
            <el-image
              :src="item.file"
              fit="cover"
              class="photo-image"
              :preview-src-list="[item.file]"
            >
              <template #error>
                <div class="image-error">
                  <el-icon><Picture /></el-icon>
                  <span>图片加载失败</span>
                </div>
              </template>
            </el-image>
            
            <div class="card-info">
              <div class="id">ID: {{ item.displayId }}</div>
              <h3 class="title">{{ item.title }}</h3>
              <!-- <p class="detail">{{ item.detail }}</p> -->
              <div class="time">
                <el-icon><Clock /></el-icon>
                <span>{{ item.time }}</span>
              </div>
            </div>

            <div class="card-actions">
              <el-button type="primary" size="small" @click="handleEdit(item)">
                <el-icon><Edit /></el-icon>
                编辑
              </el-button>
              <el-button type="danger" size="small" @click="handleDelete(item.id)">
                <el-icon><Delete /></el-icon>
                删除
              </el-button>
            </div>
          </div>
        </el-card>
    </div>

    <!-- 分页组件 -->
    <div class="pagination-wrapper">
      <el-pagination
        v-model:current-page="pageNum"
        v-model:page-size="pageSize"
        :page-sizes="[8, 12, 24, 48]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 编辑/新增对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑照片' : '新增照片'"
      width="500px"
    >
      <el-form :model="formData" label-width="80px">
        <el-form-item label="主题">
          <el-input v-model="formData.title" placeholder="请输入主题" />
        </el-form-item>
        
        <el-form-item label="详细">
          <el-input
            v-model="formData.detail"
            type="textarea"
            :rows="4"
            placeholder="请输入详细信息"
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
          <div v-if="formData.file" style="margin-top: 10px">
            <el-image
              :src="formData.file"
              fit="cover"
              style="width: 100px; height: 80px; border-radius: 4px"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
    <!-- 删除确认对话框 -->
    <ConfirmDeleteDialog
      v-model="deleteDialogVisible"
      title="删除确认"
      message="确定要删除该人员吗？删除后将无法恢复。"
      @confirm="confirmDelete"
    />
  </div>
</template>

<script setup>
import { ref, computed, onMounted,watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Edit, Delete, Picture, Clock } from '@element-plus/icons-vue'
import {GetShootList, AddShoot, UpdateShoot, DeleteShoot} from '@/api/shoot.js'
import ConfirmDeleteDialog from "@/components/ConfirmDeleteDialog.vue";
import { uploadImage } from "@/api/upload";
const deleteDialogVisible = ref(false);
const currentDeleteRow = ref(null);
// 搜索关键词
const searchTitle = ref('')

// 分页相关状态
const pageNum = ref(1)
const pageSize = ref(8)
const total = ref(0)  // 总数
// 对话框状态
const dialogVisible = ref(false)
const isEdit = ref(false)
const formData = ref({
  id: '',
  title: '',
  file: '',
  time:'',
  detail: ''
})

// 照片列表数据
const photoList = ref([])

// 计算带连续ID的照片列表
const computedPhotoList = computed(() => {
  return photoList.value.map((item, index) => ({
    ...item,
    displayId: (pageNum.value - 1) * pageSize.value + index + 1
  }))
})


//获取照片数据
const GetAllhotoList = async (pageNum, pageSize, title) => {
  console.log('获取照片数据文本处：',pageNum, pageSize, title)
  
  const response = await GetShootList(pageNum, pageSize, title)
  console.log('后端传回的数据',response.data.data);
  
  total.value = response.data.total
  photoList.value = response.data.data
}
//新增照片数据
const AddPhotoList = async (title,file,detail) => {
  console.log('新增照片数据文本处：',title,file,detail)
  const response = await AddShoot(title,file,detail)
  console.log(response)
  
  if (response.code === 1) {
    ElMessage.success('新增成功')
    pageNum.value = 1
    GetAllhotoList(pageNum.value, pageSize.value, searchTitle.value)
  } else {
    ElMessage.error('新增失败')
  }
}
//编辑照片数据
const UpdatePhotoList = async (id,title,file,detail) => {
  console.log('编辑照片数据文本处：',id,title,file,detail)
  const response = await UpdateShoot(id,title,file)
  if (response.code === 1) {
    ElMessage.success('编辑成功')
    GetAllhotoList(pageNum.value, pageSize.value, searchTitle.value)
  } else {
    ElMessage.error('编辑失败')
  }
}
//删除照片数据
const DeletePhotoList = async (id) => {
  console.log('删除照片数据文本处：',typeof id)
  const response = await DeleteShoot(id)
  if (response.code === 1) {
    ElMessage.success('删除成功')
    pageNum.value = 1
    GetAllhotoList(pageNum.value, pageSize.value, searchTitle.value)
  } else {
    ElMessage.error('删除失败')
  }
}
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
      formData.value.file = response.data;
      // console.log('查看图像是否渲染成功',formData.file)
      
      ElMessage.success("图片上传成功!");
    }
  } catch (error) {
    ElMessage.error("图片上传失败!");
    console.error(error);
  }
  return false;
};

onMounted(() => {
  GetAllhotoList(pageNum.value, pageSize.value, searchTitle.value)
})
watch([pageNum, pageSize],()=>{
  GetAllhotoList(pageNum.value, pageSize.value, searchTitle.value)
})


// 页码改变时的处理
const handleCurrentChange = (page) => {
  pageNum.value = page
}

// 每页大小改变时的处理
const handleSizeChange = (size) => {
  pageSize.value = size
  pageNum.value = 1 // 重置到第一页
}

// 搜索处理
const handleSearch = () => {
  pageNum.value = 1 // 搜索后重置到第一页
  GetAllhotoList(pageNum.value, pageSize.value, searchTitle.value)
  //重置
  searchTitle.value = ''
}

// 新增
const handleAdd = () => {
  isEdit.value = false
  
  formData.value = {
    id: null,
    title: '',
    file: '',
    detail: ''
  }
  dialogVisible.value = true
}

// 编辑
const handleEdit = (item) => {
  isEdit.value = true
  formData.value = {...item}
  dialogVisible.value = true
}

// 删除
const handleDelete = (row) => {
  currentDeleteRow.value = row;
  deleteDialogVisible.value = true;
};
const confirmDelete = async () => {
  DeletePhotoList(currentDeleteRow.value)
  deleteDialogVisible.value = false;
};


// 保存
const handleSave = () => {
  if (!formData.value.title || !formData.value.file || !formData.value.detail) {
    ElMessage.warning('请填写完整信息')
    return
  }

  if (isEdit.value) {
    // 编辑
    UpdatePhotoList(formData.value.id, formData.value.title, formData.value.file, formData.value.detail)
  } else {
    // 新增
    AddPhotoList(formData.value.title, formData.value.file, formData.value.detail)
  }

  dialogVisible.value = false
}
</script>

<style scoped>
.photo-management {
  padding: 24px;
  min-height: 100vh;
  background: #f5f7fa;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.header h2 {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: #303133;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 24px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.card-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 24px;
  margin-bottom: 24px;
}

.photo-card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.photo-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.card-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.photo-image {
  width: 100%;
  height: 200px;
  border-radius: 4px;
  overflow: hidden;
}

.image-error {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
}

.image-error .el-icon {
  font-size: 48px;
  margin-bottom: 8px;
}

.card-info {
  flex: 1;
}

.card-info .id {
  font-size: 14px;
  color: #666;
  margin-bottom: 8px;
}

.card-info .title {
  margin: 0;
  font-size: 18px;
  font-weight: 500;
  color: #333;
}

.card-info .time {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
  color: #999;
  margin-top: 8px;
}

.card-actions {
  display: flex;
  gap: 8px;
  justify-content: flex-end;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 24px;
}
</style>
