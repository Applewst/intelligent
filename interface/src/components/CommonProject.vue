<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getProjectList } from '@/api/project'

// 路由实例
const router = useRouter()

// 状态管理
const directions = ref([]) // 存储项目数据
const loading = ref(true) // 加载状态
const error = ref('') // 错误信息
const errorImage = 'https://picsum.photos/error/200/200' // 图片加载失败占位图

const fetchDirections = async (params = {}) => {
  loading.value = true
  error.value = ''
  directions.value = []

  try {
    const response = await getProjectList(params)
    if (response.code === 0 && Array.isArray(response.data)) {
      // 只取前3个项目
      directions.value = response.data.slice(0, 3)
      if (directions.value.length === 0) {
        ElMessage.info('当前没有项目数据')
      }
    } else {
      throw new Error('数据格式异常')
    }
  } catch (err) {
    error.value = err.message || '获取项目数据失败，请重试'
    ElMessage.error(error.value)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchDirections()
})

// 卡片点击事件
const handleCardClick = (projectId) => {
  router.push(`/search/project/${projectId}`)
}
</script>

<template>
  <div class="research-directions-container">
    <!-- 大标题 -->
    <h2 class="section-title">项目研究方向</h2>

    <!-- 研究方向卡片容器 -->
    <div class="directions-grid">
      <div v-if="loading" class="skeleton-container">
        <div v-for="i in 3" :key="i" class="skeleton-item">
          <el-skeleton shape="circle" :width="140" :height="140" class="skeleton-avatar" />
        </div>
      </div>

      <div
        v-for="direction in directions"
        :key="direction.id"
        class="direction-card"
        v-else
        @click="handleCardClick(direction.id)"
      >
        <!-- 圆形图片 -->
        <div class="avatar-container">
          <div class="avatar-bg"></div>
          <el-avatar
            :src="direction.imageUrl"
            class="direction-avatar"
            shape="circle"
            :error="errorImage"
          />
        </div>

        <!-- 标题（悬浮显示） -->
        <div class="direction-title">{{ direction.title }}</div>
      </div>
    </div>

    <el-empty
      v-if="!loading && directions.length === 0"
      description="暂无项目数据"
      class="empty-state"
    />

    <div v-if="error" class="error-state">
      <el-icon class="error-icon"><WarningFilled /></el-icon>
      <p class="error-text">{{ error }}</p>
      <el-button type="text" @click="fetchDirections">重试</el-button>
    </div>
  </div>
</template>

<style scoped>
.research-directions-container {
  padding: 5rem;
  max-width: 1000px;
  margin: 20px auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(135deg, #f0f4f8, #d9e2ec);
}

/* 标题样式 */
.section-title {
  text-align: center;
  margin-bottom: 3rem;
  font-size: 1.8rem;
  font-weight: 700;
  color: #2c3e50;
  position: relative;
  padding-bottom: 0.8rem;
  width: 100%;
}

/* 网格布局 - 3个项目 */
.directions-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  width: 100%;
  justify-items: center;
}

/* 骨架屏样式 */
.skeleton-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 4rem;
  width: 100%;
  justify-items: center;
}

.skeleton-item {
  display: flex;
  flex-direction: column;
  align-items: center;
}

/* 卡片核心样式 */
.direction-card {
  width: 100%;
  max-width: 200px;
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
  position: relative;
  transition: all 0.3s ease;
  padding: 1rem;
}

/* 头像样式 */
.avatar-container {
  position: relative;
  width: 140px;
  height: 140px;
  margin-bottom: 1rem;
  transition: all 0.3s ease;
}

.avatar-bg {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #e0f2fe, #bae6fd);
  border-radius: 50%;
  z-index: 1;
  opacity: 0.6;
  transition: all 0.3s ease;
}

.direction-avatar {
  position: relative;
  width: 120px;
  height: 120px;
  border: 3px solid #ffffff;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  z-index: 2;
  transition: all 0.3s ease;
}

/* 标题样式（默认隐藏，悬浮显示） */
.direction-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #2c3e50;
  text-align: center;
  margin: 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  width: 100%;
  padding: 0.5rem 1rem;
  border-radius: 8px;
  background-color: rgba(255, 255, 255, 0.9);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  opacity: 0;
  visibility: hidden;
  transform: translateY(10px);
  transition: all 0.3s ease;
  position: absolute;
  bottom: -20px;
  left: 50%;
  transform: translateX(-50%) translateY(10px);
  z-index: 3;
}

/* 悬浮交互效果 */
.direction-card:hover {
  transform: translateY(-5px);
}

.direction-card:hover .avatar-bg {
  opacity: 0.8;
  transform: translate(-50%, -50%) scale(1.05);
}

.direction-card:hover .direction-avatar {
  transform: scale(1.05);
}

/* 悬浮时显示标题 */
.direction-card:hover .direction-title {
  opacity: 1;
  visibility: visible;
  transform: translateX(-50%) translateY(0);
}

/* 空状态样式 */
.empty-state {
  margin: 60px 0;
  width: 100%;
}

/* 错误状态样式 */
.error-state {
  margin: 60px 0;
  text-align: center;
  color: #f56c6c;
  width: 100%;
}

.error-icon {
  font-size: 2rem;
  margin-bottom: 0.5rem;
}

.error-text {
  margin-bottom: 1rem;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .directions-grid,
  .skeleton-container {
    grid-template-columns: repeat(2, 1fr);
    gap: 3rem;
  }

  .section-title {
    font-size: 1.5rem;
  }
}

@media (max-width: 480px) {
  .directions-grid,
  .skeleton-container {
    grid-template-columns: 1fr;
    gap: 3rem;
  }

  .direction-card {
    max-width: 250px;
  }
}
</style>
