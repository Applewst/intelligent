<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getnewList } from '@/api/new.js'

// 新闻列表数据
const newList = ref([])
// 加载状态
const loading = ref(true)
// 错误图片占位图
const errorImage = 'https://picsum.photos/error/120/120'
// 路由实例
const router = useRouter()

// 查看更多按钮点击事件
const handleViewMore = () => {
  router.push('/news') // 跳转到新闻列表页
}

// 新闻卡片点击事件 - 跳转到详情页
const handlenewClick = (newId) => {
  router.push(`/news/${newId}`) // 跳转到新闻详情页
}

// 组件挂载时获取数据
onMounted(async () => {
  try {
    loading.value = true
    // 调用后端接口获取实际数据
    const data = await getnewList()
    newList.value = data

    if (data.length === 0) {
      ElMessage.info('当前没有动态数据') // 更新提示文本
    }
  } catch (err) {
    ElMessage.error(err.message || '获取动态失败，请重试') // 更新提示文本
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="new-container">
    <!-- 标题栏：标题居中 + 查看更多按钮 -->
    <div class="new-header">
      <div class="header-content">
        <h2>最新动态</h2>
        <el-button type="text" class="view-more-btn" @click="handleViewMore">
          查看更多
          <el-icon class="btn-icon">
            <ArrowRight />
          </el-icon>
        </el-button>
      </div>
    </div>

    <!-- 动态网格 -->
    <div class="new-grid">
      <!-- 加载状态：显示骨架屏 -->
      <div v-if="loading" class="skeleton-container">
        <el-skeleton v-for="i in 4" :key="i" :rows="3" width="100%" class="skeleton-item" />
      </div>

      <!-- 动态卡片 -->
      <el-card
        v-for="item in newList"
        :key="item.id"
        v-else
        class="new-card"
        hoverable
        @click="handlenewClick(item.id)"
      >
        <!-- 动态图片 -->
        <el-image
          :src="item.qrCode"
          :alt="`${item.title}的图片`"
          class="new-image"
          fit="contain"
          lazy
          :error="errorImage"
        />

        <div class="new-info">
          <h3 class="new-title">{{ item.title }}</h3>
          <p class="new-desc">{{ item.desc }}</p>
          <p class="new-time">
            <el-icon size="14">
              <Clock />
            </el-icon>
            {{ item.time }}
          </p>
        </div>
      </el-card>
    </div>

    <!-- 空状态 -->
    <el-empty
      v-if="!loading && newList.length === 0"
      description="暂无动态数据"
      class="empty-state"
    />
  </div>
</template>

<style scoped>
.new-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

/* 标题栏样式 */
.new-header {
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.header-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}

.new-header h2 {
  margin: 0;
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

/* 查看更多按钮样式 */
.view-more-btn {
  color: #409eff;
  padding: 0;
  display: flex;
  align-items: center;
  gap: 4px;
  transition: color 0.2s;
}

.view-more-btn:hover {
  color: #66b1ff;
}

.btn-icon {
  font-size: 14px;
  transition: transform 0.2s;
}

.view-more-btn:hover .btn-icon {
  transform: translateX(2px);
}

/* 动态网格布局 */
.new-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 20px;
}

/* 骨架屏样式 */
.skeleton-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
  gap: 20px;
}

.skeleton-item {
  height: 320px;
}

/* 动态卡片样式 */
.new-card {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px;
  text-align: center;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.new-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.08);
}

.new-image {
  width: 120px;
  height: 120px;
  margin: 0 auto 16px;
  display: block;
  object-fit: contain;
  background-color: #f5f5f5;
  border-radius: 4px;
}

.new-info {
  width: 100%;
  text-align: center;
  flex-grow: 1; /* 让内容区域自适应填充 */
  display: flex;
  flex-direction: column;
}

.new-title {
  font-size: 16px;
  margin-bottom: 8px;
  font-weight: 500;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.new-desc {
  font-size: 14px;
  color: #666;
  margin-bottom: 12px;
  line-height: 1.5;
  height: 42px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  flex-grow: 1;
}

.new-time {
  font-size: 13px;
  color: #888;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  margin-top: auto;
}

/* 空状态样式 */
.empty-state {
  margin: 60px 0;
}

/* 适配不同屏幕尺寸 */
@media (max-width: 768px) {
  .new-grid {
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  }

  .skeleton-container {
    grid-template-columns: repeat(auto-fill, minmax(180px, 1fr));
  }
}
</style>
