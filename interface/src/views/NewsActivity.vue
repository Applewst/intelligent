<script setup>
import { onMounted, ref } from 'vue'
// 1. 修改导入的函数名
import { getNewsList } from '@/api/news'

const list = ref([])
const loading = ref(true)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const loadData = async () => {
  loading.value = true
  try {
    const params = {
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      name: '', // 如果接口支持按名称搜索，可以保留
    }
    // 2. 调用统一的函数
    const res = await getNewsList(params)
    if (res.code === 0) {
      // 注意：现在 res.data 是一个分页对象
      list.value = res.data.list || []
      total.value = res.data.total || 0
    }
  } finally {
    loading.value = false
  }
}

// ... (handlePageChange, handleSizeChange, onMounted 等逻辑不变)
const handlePageChange = (page) => {
  currentPage.value = page
  loadData()
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  loadData()
}

onMounted(loadData)
</script>

<template>
  <div class="news-wrap">
    <!-- 列表内容 -->
    <el-row v-loading="loading" :gutter="20">
      <!-- 注意：这里的 v-for 遍历的是 list，而不是 list.data -->
      <el-col v-for="item in list" :key="item.id" :xs="24" :sm="12" class="mb-20">
        <el-card shadow="hover" class="news-card">
          <div class="flex-box">
            <!-- 这里的图片路径也需要统一，假设 mock 和真实接口都用 image 字段 -->
            <img :src="item.image" class="news-img" :alt="item.title" />
            <div class="text-box">
              <h3 class="news-title">{{ item.title }}</h3>
              <p class="news-desc">{{ item.desc || item.detail }}</p>
              <!-- 兼容 desc 和 detail -->
              <div class="news-time">{{ item.time }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 分页组件 -->
    <div class="pagination-wrap">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
      />
    </div>
  </div>
</template>

<!-- style 部分不变 -->

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
  transition: transform 0.2s;

  &:hover {
    transform: translateY(-4px);
  }
}

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
  min-width: 0;
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
  white-space: pre-wrap;
}

.news-time {
  font-size: 12px;
  color: #909399;
}

.pagination-wrap {
  margin-top: 32px;
  display: flex;
  justify-content: center;
}
</style>
