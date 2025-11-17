<template>
  <div class="photo-management">
    <div class="header">
      <div class="header-left">
        <el-button
          type="primary"
          :icon="ArrowLeft"
          circle
          @click="routerBack"
          class="back-button"
        />
        <h2>照片管理</h2>
      </div>
    </div>

    <!-- 卡片列表 -->
    <div class="card-grid">
      <el-card v-for="item in photoList" :key="item.id" class="photo-card" shadow="hover">
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
            <h3 class="title">{{ item.title }}</h3>
            <p class="detail">{{ item.detail }}</p>
            <div class="time">
              <el-icon><Clock /></el-icon>
              <span>{{ item.time }}</span>
            </div>
          </div>
        </div>
      </el-card>
    </div>

    <!-- 分页组件 -->
    <div class="pagination-wrapper">
      <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        :page-sizes="[8, 12, 24, 48]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { getPhotoWallImages } from '@/api/photo'
import { useRouter } from 'vue-router'

const router = useRouter()

const pageNum = ref(1)
const pageSize = ref(8)
const allPhotos = ref([])
const photoList = computed(() => {
  const start = (pageNum.value - 1) * pageSize.value
  const end = start + pageSize.value
  return allPhotos.value.slice(start, end)
})
const total = computed(() => allPhotos.value.length)

const GetPhotoData = async () => {
  try {
    const res = await getPhotoWallImages(1, 1000)
    // console.log(res)

    if (res.code === 1) {
      allPhotos.value = res.data.data || []
      ElMessage.success('获取成功')
    } else {
      ElMessage.error('获取失败')
    }
  } catch (error) {
    console.error('API call failed:', error)
    ElMessage.error('网络请求失败')
  }
}

const routerBack = () => {
  router.back()
}

onMounted(() => {
  GetPhotoData()
})

const handleCurrentChange = (page) => {
  pageNum.value = page
}

const handleSizeChange = (size) => {
  pageSize.value = size
  pageNum.value = 1
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

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.back-button {
  flex-shrink: 0;
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

.title {
  margin: 0 0 8px 0;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.detail {
  margin: 0;
  font-size: 14px;
  color: #606266;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.time {
  display: flex;
  align-items: center;
  gap: 4px;
  margin-top: 8px;
  font-size: 12px;
  color: #909399;
}

.card-actions {
  display: flex;
  gap: 8px;
  padding-top: 8px;
  border-top: 1px solid #ebeef5;
}

.card-actions .el-button {
  flex: 1;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  padding: 24px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

@media (max-width: 768px) {
  .card-grid {
    grid-template-columns: 1fr;
  }

  .search-bar {
    flex-direction: column;
    gap: 12px;
  }

  .search-bar .el-input {
    width: 100% !important;
  }
}
</style>
