<!-- NewsActivity.vue -->
<script setup>
import { onMounted, ref } from 'vue'
import { getAllNews } from '@/api/news'

const list = ref([])
const loading = ref(true)

const loadAll = async () => {
  loading.value = true
  try {
    const res = await getAllNews({ category: 'all' })
    if (res.code === 0) list.value = res.data
  } finally {
    loading.value = false
  }
}

onMounted(loadAll)
</script>


<template>
  <div class="news-wrap">
    <el-row v-loading="loading" :gutter="20">
      <el-col v-for="item in list" :key="item.id" :xs="24" :sm="12" class="mb-20">
        <el-card shadow="hover" class="news-card">
          <div class="flex-box">
            <img :src="item.qrCode.match(/https?:\/\/[^\s]+/)[0]" class="news-img" />
            <div class="text-box">
              <h3 class="news-title">{{ item.title }}</h3>
              <p class="news-desc">{{ item.desc }}</p>
              <div class="news-time">{{ item.time }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="less">
.news-wrap {
  max-width: 1200px;
  margin: 100px auto;
  padding: 0 20px;
}

.mb-20 {
  margin-bottom: 20px;
}

.news-card {
  border-radius: 8px;
  overflow: hidden;
}

/* 左侧图片 + 右侧文字 */
.flex-box {
  display: flex;
  align-items: flex-start;
  gap: 16px;
}

.news-img {
  width: 100px;
  height: 100px;
  object-fit: cover;
  border-radius: 6px;
  flex-shrink: 0;
}

.text-box {
  flex: 1;
  min-width: 0; /* 防止 flex 子项溢出 */
}

.news-title {
  margin: 0 0 6px;
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.news-desc {
  margin: 0 0 8px;
  font-size: 14px;
  color: #606266;
  line-height: 1.6;
  white-space: pre-wrap; /* 保留换行并自动换行 */
}

.news-time {
  font-size: 12px;
  color: #909399;
}
</style>
