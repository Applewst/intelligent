<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ArrowRight } from '@element-plus/icons-vue'

// 引入API方法（从JS文件获取数据）
import { getPhotoWallImages } from '@/api/photo' // 假设此API返回照片数据

// 路由实例
const router = useRouter()

// 响应式数据（从API接收数据）
const photoWall = ref(null)
const displayedPhotos = ref([]) // 存储从API获取的照片数据
const isLoading = ref(true) // 加载状态
const errorMessage = ref('') // 错误信息
const rotationSpeed = 0.8 // 固定旋转速度
let currentRotation = 0
let animationId = null

// 计算照片位置样式（动态适配API返回的数量）
const getPhotoStyle = (index) => {
  const count = displayedPhotos.value.length
  if (count === 0) return {}

  const angle = (360 / count) * index
  const radius = 400 // 环形半径

  return {
    transform: `rotateY(${angle}deg) translateZ(${radius}px)`,
    transition: 'transform 0.3s ease',
  }
}

// 自动旋转动画
const animate = () => {
  if (photoWall.value) {
    currentRotation += rotationSpeed * 0.1
    photoWall.value.style.transform = `rotateY(${currentRotation}deg)`
  }
  animationId = requestAnimationFrame(animate)
}

// 从API加载数据
const loadPhotos = async () => {
  try {
    isLoading.value = true
    errorMessage.value = ''

    // 调用API获取数据（核心：使用JS传过来的数据）
    const apiData = await getPhotoWallImages(1, 6, '') // 从JS/API获取数据
    // console.log(apiData)

    // 验证API返回格式
    if (Array.isArray(apiData.data.data)) {
      displayedPhotos.value = apiData.data.data // 将API数据赋值给显示列表
    } else {
      throw new Error('API返回数据格式错误')
    }
  } catch (error) {
    console.error('加载照片失败:', error)
    errorMessage.value = '照片加载失败，请稍后重试'
    ElMessage.error(errorMessage.value)
  } finally {
    isLoading.value = false // 结束加载状态
  }
}

// 查看更多按钮点击事件
const handleViewMore = () => {
  router.push('/news/photo').catch((err) => {
    ElMessage.error('跳转失败')
  })
}

// 生命周期
onMounted(() => {
  loadPhotos() // 组件挂载后立即加载API数据
  animate() // 启动旋转动画
})

onBeforeUnmount(() => {
  if (animationId) {
    cancelAnimationFrame(animationId) // 清理动画
  }
})
</script>

<template>
  <div class="photo-wall-container">
    <!-- 顶部区域：标题居左，查看更多居右 -->
    <div class="top-bar">
      <h1 class="main-title">照片墙</h1>
      <div class="action-area">
        <el-button @click="handleViewMore" type="primary" class="view-more-btn">
          查看更多
          <ArrowRight class="btn-icon" />
        </el-button>
      </div>
    </div>

    <!-- 照片墙主体 -->
    <div class="photo-wall" ref="photoWall">
      <!-- 加载状态 -->
      <div v-if="isLoading" class="loading-state">
        <div class="spinner"></div>
        <p>加载照片中...</p>
      </div>

      <!-- 错误提示 -->
      <div v-else-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>

      <!-- 照片列表（使用API数据） -->
      <div
        v-else
        v-for="(photo, index) in displayedPhotos"
        :key="index"
        class="photo-item"
        :style="getPhotoStyle(index)"
      >
        <img :src="photo.url" :alt="`照片 ${index + 1}`" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.photo-wall-container {
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: linear-gradient(135deg, #f0f4f8, #d9e2ec);
  overflow: hidden;
  perspective: 1200px;
  padding: 20px;
  max-width: 1400px;
  margin: 50px auto;
}

/* 顶部栏布局 */
.top-bar {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 30px;
  margin-bottom: -30px;
}

.main-title {
  color: #2d3748;
  font-size: 2.5rem;
  font-weight: 700;
  margin: 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.action-area {
  display: flex;
  align-items: center;
}

.view-more-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
}

.btn-icon {
  width: 1em;
  height: 1em;
}

/* 照片墙样式 */
.photo-wall {
  position: relative;
  width: 100%;
  height: 300px;
  margin: auto;
  transform-style: preserve-3d;
  transition: transform 0.3s ease;
}

/* 加载状态样式 */
.loading-state {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: #666;
}

/* 自定义加载动画 */
.spinner {
  width: 40px;
  height: 40px;
  border: 4px solid #f3f3f3;
  border-top: 4px solid #3498db;
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin-bottom: 10px;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}

/* 错误提示样式 */
.error-message {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #e74c3c;
  font-size: 1.2rem;
}

/* 照片项样式 */
.photo-item {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 300px;
  height: 200px;
  margin: -100px 0 0 -150px;
  backface-visibility: visible;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
  border-radius: 8px;
  overflow: hidden;
  cursor: default;
}

.photo-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .top-bar {
    flex-direction: column;
    align-items: flex-start;
    gap: 15px;
    margin-bottom: 30px;
  }

  .main-title {
    font-size: 2rem;
  }

  .photo-item {
    width: 220px;
    height: 150px;
    margin: -75px 0 0 -110px;
  }

  .photo-wall {
    height: 400px;
  }
}
</style>
