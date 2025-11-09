<template>
  <div class="team-intro-container">
    <!-- 加载状态 -->
    <div v-if="loading" class="loading-wrapper">
      <el-skeleton :rows="6" animated />
    </div>

    <!-- 内容展示 -->
    <div v-else-if="teamData" class="team-content">
      <!-- 团队图片 -->
      <div class="team-image-wrapper">
        <el-image
          :src="teamData.imageUrl"
          :alt="teamData.teamName"
          fit="cover"
          class="team-image"
          :preview-src-list="[teamData.imageUrl]"
        >
          <template #error>
            <div class="image-error">
              <el-icon :size="50"><Picture /></el-icon>
              <span>图片加载失败</span>
            </div>
          </template>
        </el-image>
      </div>

      <!-- 团队信息 -->
      <div class="team-info">
        <h1 class="team-name">{{ teamData.teamName }}</h1>
        <div class="divider"></div>
        <p class="team-introduction">{{ teamData.introduction }}</p>
      </div>
    </div>

    <!-- 错误状态 -->
    <el-empty v-else description="暂无团队介绍信息" :image-size="120">
      <el-button type="primary" @click="fetchTeamData">
        <el-icon><Refresh /></el-icon>
        重新加载
      </el-button>
    </el-empty>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getTeamIntroduction } from '@/api/introduce.js'

// 响应式数据
const loading = ref(false)
const teamData = ref(null)

/**
 * 获取团队介绍数据
 */
const fetchTeamData = async () => {
  loading.value = true
  try {
    const response = await getTeamIntroduction()

    if (response.code === 200 && response.data) {
      teamData.value = response.data
    } else {
      ElMessage.error(response.message || '获取团队介绍失败')
    }
  } catch (error) {
    console.error('获取团队介绍失败:', error)
    ElMessage.error('获取团队介绍失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 组件挂载时获取数据
onMounted(() => {
  fetchTeamData()
})
</script>

<style scoped>
.team-intro-container {
  text-align: center;
  margin-top: 100px;
  font-size: 24px;
  color: #333;

  width: 100%;
  max-width: 1200px;
  padding: 2rem;
  min-height: 60vh;
}

.loading-wrapper {
  padding: 2rem;
}

.team-content {
  display: flex;
  flex-direction: column;
  gap: 2rem;
  background: linear-gradient(to bottom, #f8fafc, #ffffff);
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1), 0 2px 4px -2px rgb(0 0 0 / 0.1);
}

.team-image-wrapper {
  width: 100%;
  height: 400px;
  overflow: hidden;
  position: relative;
}

.team-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.team-image:hover {
  transform: scale(1.05);
}

.image-error {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  gap: 1rem;
  color: #94a3b8;
  background-color: #f1f5f9;
}

.team-info {
  padding: 2rem 3rem 3rem;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.team-name {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1e293b;
  margin: 0;
  line-height: 1.2;
  text-align: center;
}

.divider {
  width: 80px;
  height: 4px;
  background: linear-gradient(to right, #3b82f6, #8b5cf6);
  border-radius: 2px;
  margin: 0 auto;
}

.team-introduction {
  font-size: 1.125rem;
  line-height: 1.8;
  color: #475569;
  margin: 0;
  text-align: justify;
  white-space: pre-wrap;
  word-wrap: break-word;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .team-intro-container {
    padding: 1rem;
  }

  .team-image-wrapper {
    height: 250px;
  }

  .team-info {
    padding: 1.5rem;
  }

  .team-name {
    font-size: 1.875rem;
  }

  .team-introduction {
    font-size: 1rem;
  }
}

@media (min-width: 1024px) {
  .team-content {
    flex-direction: row;
    align-items: stretch;
  }

  .team-image-wrapper {
    width: 45%;
    height: auto;
    min-height: 500px;
  }

  .team-info {
    width: 55%;
    padding: 3rem;
    justify-content: center;
  }

  .team-name {
    text-align: left;
  }

  .divider {
    margin: 0;
  }
}
</style>
