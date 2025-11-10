<script setup>
import { onMounted, ref } from 'vue'
import { getAllNews } from '@/api/news'

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
      name: '', // Always pass empty string for name parameter
    }
    const res = await getAllNews(params)
    if (res.code === 0) {
      list.value = res.data.list || res.data
      total.value = res.data.total || res.data.length
    }
  } finally {
    loading.value = false
  }
}

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
