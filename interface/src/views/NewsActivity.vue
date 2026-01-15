<script setup>
// 逻辑部分完全不变，保留所有功能
import { onMounted, ref } from 'vue'
import { getNewsList } from '@/api/news'
import { GetsearchPapers } from '@/api/search'
import { getProjectList } from '@/api/project'

const academicList = ref([])
const projectList = ref([])
const thesisList = ref([])
const loading = ref(true)

const loadData = async () => {
  loading.value = true
  try {
    const [academicRes, projectRes, thesisRes] = await Promise.all([
      getNewsList({ pageNum: 1, pageSize: 10, title: '' }),
      getProjectList({ pageNum: 1, pageSize: 10, name: '' }),
      GetsearchPapers(1, 10, '', ''),
    ])
    academicList.value = academicRes.code === 1 ? (academicRes.data?.data || []).slice(0, 10) : []
    projectList.value = projectRes.code === 1 ? (projectRes.data?.data || []).slice(0, 10) : []
    thesisList.value = thesisRes.code === 1 ? (thesisRes.data?.data || []).slice(0, 10) : []
  } catch (err) {
    console.error('数据加载异常：', err)
  } finally {
    loading.value = false
  }
}

onMounted(loadData)
</script>

<template>
  <div class="news-wrap">
    <div
      v-loading="loading"
      element-loading-text="加载中..."
      element-loading-background="rgba(255,255,255,0.85)"
    >
      <!-- 学术交流模块 -->
      <section class="news-section">
        <h2 class="section-title">学术交流</h2>
        <div class="news-list">
          <div class="news-item" v-for="item in academicList" :key="item.id">
            <span class="dot">●</span>
            <span class="news-content">{{ item.title }}</span>
          </div>
          <div class="empty-tip" v-if="!academicList.length">暂无学术交流内容</div>
        </div>
      </section>

      <!-- 项目进展模块 -->
      <section class="news-section">
        <h2 class="section-title">项目进展</h2>
        <div class="news-list">
          <div class="news-item" v-for="item in projectList" :key="item.id">
            <span class="dot">●</span>
            <span class="news-content">
              <span class="label">{{ item.title }}</span>
            </span>
          </div>
          <div class="empty-tip" v-if="!projectList.length">暂无项目进展内容</div>
        </div>
      </section>

      <!-- 论文发表模块 -->
      <section class="news-section">
        <h2 class="section-title">论文发表</h2>
        <div class="news-list">
          <div class="news-item" v-for="item in thesisList" :key="item.id">
            <span class="dot">●</span>
            <span class="news-content">
              <span class="label">作者：{{ item.author || '未知' }}</span> {{ item.title }}
            </span>
          </div>
          <div class="empty-tip" v-if="!thesisList.length">暂无论文发表内容</div>
        </div>
      </section>
    </div>
  </div>
</template>

<style scoped lang="less">
.news-wrap {
  margin-top: 100px;
  max-width: 1000px;
  padding: 0 24px;
  box-sizing: border-box;
}

/* 模块标题 */
.news-section {
  margin-bottom: 32px;
}
.section-title {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  margin: 0 0 16px 0;
  padding-bottom: 8px;
  border-bottom: 2px solid #2563eb;
}

/* 新闻列表 - 一行一条 */
.news-list {
  line-height: 2;
}
.news-item {
  padding: 4px 0;
  font-size: 15px;
  color: #333;
  transition: color 0.2s;
  &:hover {
    color: #2563eb;
  }
}

/* 项目符号 */
.dot {
  color: #2563eb;
  margin-right: 8px;
  font-size: 12px;
}

/* 标签样式（项目ID/作者） */
.label {
  color: #64748b;
  font-size: 14px;
  margin-right: 12px;
}

/* 空数据提示 */
.empty-tip {
  color: #94a3b8;
  font-size: 14px;
  padding: 8px 0;
}

/* 响应式适配手机 */
@media (max-width: 768px) {
  .news-wrap {
    margin: 20px auto;
    padding: 0 16px;
  }
  .section-title {
    font-size: 18px;
  }
  .news-item {
    font-size: 14px;
  }
}
</style>
