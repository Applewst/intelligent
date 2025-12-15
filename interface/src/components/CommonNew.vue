<template>
  <section class="latest-news">
      <!-- 模板部分无需改动 -->

    <div class="new-header">
      <div class="header-content">
        <h2>最新动态</h2>
        <!-- <el-button type="text" class="view-more-btn" @click="handleViewMore">
          查看更多
          <el-icon class="btn-icon"><ArrowRight /></el-icon>
        </el-button> -->
      </div>
    </div>

    <div class="news-container">
      <!-- 第一行：学生获奖 + 团队活动 -->
      <div class="row">
        <!-- 学生获奖 -->
        <el-card class="card flex-1" shadow="hover">
          <template #header>
            <div class="card-header">
              <div>
                <h3 class="card-title purple">
                  <el-icon><Trophy /></el-icon>
                  奖励荣誉
                </h3>
                <p class="card-subtitle">REWARDS AND HONORS</p>
              </div>
              <el-link type="primary" :underline="false" href="#">
                更多 <el-icon><ArrowRight /></el-icon>
              </el-link>
            </div>
          </template>

          <div class="awards-list">
            <a v-for="(item, index) in awardsList" :key="index" href="#" class="award-item">
              <span class="date purple">{{ item.date }}</span>
              <span class="title">{{ item.title }}</span>
            </a>
          </div>

          <el-divider />

          <div class="featured-awards">
            <a v-for="(item, index) in featuredAwards" :key="index" href="#" class="featured-card">
              <div class="featured-date">
                <span class="month">{{ item.month }}</span>
                <span class="year">{{ item.year }}</span>
              </div>
              <div class="featured-content">
                <h4>{{ item.title }}</h4>
                <p>{{ item.description }}</p>
              </div>
            </a>
          </div>
        </el-card>

        <!-- 团队活动 -->
        <el-card class="card flex-1" shadow="hover">
          <template #header>
            <div class="card-header">
              <h3 class="card-title blue">
                <el-icon><User /></el-icon>
                团队活动
              </h3>
              <el-link type="primary" :underline="false" href="#">
                更多 <el-icon><ArrowRight /></el-icon>
              </el-link>
            </div>
          </template>

          <div class="activities-grid">
            <a v-for="(item, index) in activitiesList" :key="index" href="#" class="activity-item">
              <h4>{{ item.title }}</h4>
              <div class="activity-date">
                <el-icon><Calendar /></el-icon>
                {{ item.date }}
              </div>
            </a>
          </div>
        </el-card>
      </div>

      <!-- 第二行：论文发表 + 教学科研 -->
      <div class="row-flex">
        <!-- 论文发表 -->
        <el-card class="card flex-1 paper-card" shadow="hover">
          <div class="left-border"></div>
          <template #header>
            <div class="card-header">
              <div>
                <h3 class="card-title purple">
                  <el-icon><Document /></el-icon>
                  论文发表
                </h3>
                <p class="card-subtitle">PAPER PUBLICATIONS</p>
              </div>
              <el-link type="primary" :underline="false" href="#">
                更多 <el-icon><ArrowRight /></el-icon>
              </el-link>
            </div>
          </template>

          <div class="papers-list">
            <a
              v-for="(item, index) in papersList"
              :key="index"
              href="#"
              class="paper-item"
              :class="{ highlight: item.highlight }"
            >
              <div class="paper-date">
                <span class="day">{{ item.day }}</span>
                <span class="year-month">{{ item.yearMonth }}</span>
              </div>
              <p class="paper-title">{{ item.title }}</p>
            </a>
          </div>
        </el-card>

        <!-- 教学科研 -->
        <el-card class="card flex-1" shadow="hover">
          <template #header>
            <div class="card-header">
              <h3 class="card-title blue">
                <el-icon><Reading /></el-icon>
                教学科研
              </h3>
              <el-link type="primary" :underline="false" href="#">
                更多 <el-icon><ArrowRight /></el-icon>
              </el-link>
            </div>
          </template>

          <div class="research-top">
            <el-image
              src="https://images.unsplash.com/photo-1532094349884-543bc11b234d?w=200&h=140&fit=crop"
              fit="cover"
              class="research-image"
            />
            <div class="research-items">
              <a v-for="(item, index) in projectsList" :key="index" href="#" class="research-link">
                <span class="dot blue">·</span>{{ item }}
              </a>
            </div>
          </div>

          <el-divider />

          <div class="news-list">
            <a
              v-for="(item, index) in newsList"
              :key="index"
              href="#"
              class="news-item"
              :class="{ first: index === 0 }"
            >
              <span class="dot" :class="index === 0 ? 'blue' : 'gray'">·</span>{{ item }}
            </a>
          </div>
        </el-card>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import {
  Trophy,
  ArrowRight,
  User,
  Calendar,
  Document,
  Reading
} from '@element-plus/icons-vue'

const awardsList = ref([
  { date: '06-24', title: '张三荣获2024年度xx奖学金' },
  { date: '02-01', title: '基于融合网络的流媒体新技术' },
  { date: '01-01', title: '新一代立体视觉关键技术及产业化' }
])

const featuredAwards = ref([
  {
    month: '02',
    year: '2015-01',
    title: '动态立体视觉系统关键技术及应用',
    description: '省部级，科技进步奖，一等奖'
  },
  {
    month: '01',
    year: '2012-01',
    title: '立体视频重建与显示技术及装置',
    description: '国家技术发明奖，一等奖'
  }
])

const papersList = ref([
  { day: '12', yearMonth: '2025.12', title: '聚焦绿色与智能包装，共话产教融合新篇章' },
  { day: '11', yearMonth: '2025.12', title: '调研摸实情 谋良策促发展 校长曾祥君带队开展密集调研', highlight: true },
  { day: '08', yearMonth: '2025.12', title: '湖南工业大学第八次学生代表大会顺利召开' }
])

const projectsList = ref([
  'aaaaaaaaaaaaaaaa',
  'bbbbbbbbbbbbbbbb',
  'ccccccccccccccccccccccccccccccc'
])

const newsList = ref([
  '安理会降低我就打啊是点击开始了就阿喀琉斯点击是阿达按时 按时的',
  '爱护这可是擦脸上按市场ask来查看老师曾经撒开绿灯骄傲洒大赛',
  '拉我i会搭理我很多爱乐维的话艾伦我的哈撒先擦达瓦',
  '奥委会库达哈卡舞对话框我哈伍德和啊的话我的客户吖'
])

const activitiesList = ref([
  { title: '李建成：担当教育科技人才一体化发展的中南责任', date: '2025-12-15' },
  { title: '易红率队参加2025年度对口支援新疆医科大学工作会议', date: '2025-12-13' },
  { title: '中南学子获2025全国大学生外语能力大赛4项金奖', date: '2025-12-13' },
  { title: '长沙银行向中南大学捐赠1000万元', date: '2025-12-12' },
  { title: '中南大学举行2025年新当选院士师生座谈会', date: '2025-12-11' },
  { title: '黄伯云院士将100万元奖金捐赠中南大学', date: '2025-12-11' }
])
</script>

<style scoped>

  .new-header {
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.header-content {
  display: flex;
  font-size: 28px;
  font-weight: 600;
  justify-content: space-between;
  align-items: center;
  max-width: 1200px;
  margin: 0 auto;
}
.latest-news {
  
  max-width: 1200px;
  margin: 0 auto;
  margin-top: 80px;
  padding: 16px;
}

.section-title {
  font-size: 18px;
  font-weight: 700;
  color: #1f2937;
  padding-bottom: 8px;
  margin-bottom: 16px;
  border-bottom: 3px solid #7c3aed;
}

.news-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.row {
  display: flex;
  gap: 16px;
}

.row-flex {
  display: flex;
  flex-direction: row;
  gap: 16px;
}

.flex-1 {
  flex: 1;
}

.card {
  border-radius: 8px;
}

.card :deep(.el-card__header) {
  padding: 10px 14px;
}

.card :deep(.el-card__body) {
  padding: 10px 14px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  font-weight: 600;
  margin: 0;
}

.card-title.purple {
  color: #7c3aed;
}

.card-title.blue {
  color: #2563eb;
}

.card-subtitle {
  font-size: 9px;
  color: #9ca3af;
  text-transform: uppercase;
  letter-spacing: 1px;
  margin: 2px 0 0 0;
}

/* 学生获奖样式 */
.awards-list {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.award-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 4px 6px;
  text-decoration: none;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.award-item:hover {
  background-color: #f3f4f6;
}

.award-item .date {
  font-size: 12px;
  font-weight: 700;
  min-width: 40px;
}

.award-item .date.purple {
  color: #7c3aed;
}

.award-item .title {
  font-size: 12px;
  color: #374151;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.featured-awards {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 10px;
}

.featured-card {
  display: flex;
  gap: 8px;
  padding: 10px;
  border: 1px solid #e5e7eb;
  border-radius: 6px;
  text-decoration: none;
  transition: box-shadow 0.2s;
}

.featured-card:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.featured-date {
  text-align: center;
}

.featured-date .month {
  display: block;
  font-size: 20px;
  font-weight: 700;
  color: #7c3aed;
}

.featured-date .year {
  display: block;
  font-size: 9px;
  color: #9ca3af;
}

.featured-content h4 {
  font-size: 11px;
  font-weight: 500;
  color: #1f2937;
  margin: 0 0 4px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.featured-content p {
  font-size: 9px;
  color: #6b7280;
  margin: 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 团队活动样式 */
.activities-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 8px 16px;
}

.activity-item {
  display: block;
  padding: 6px 0;
  border-bottom: 1px solid #f3f4f6;
  text-decoration: none;
}

.activity-item:hover h4 {
  text-decoration: underline;
}

.activity-item h4 {
  font-size: 12px;
  color: #2563eb;
  font-weight: 400;
  margin: 0 0 4px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.5;
}

.activity-date {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 10px;
  color: #9ca3af;
}

/* 论文发表样式 */
.paper-card {
  position: relative;
  overflow: hidden;
}

.left-border {
  position: absolute;
  left: 0;
  top: 0;
  width: 4px;
  height: 100%;
  background: linear-gradient(to bottom, #7c3aed, #a78bfa, #ddd6fe);
}

.papers-list {
  display: flex;
  flex-direction: column;
}

.paper-item {
  display: flex;
  align-items: flex-start;
  gap: 10px;
  padding: 8px 6px;
  border-bottom: 1px solid #f3f4f6;
  text-decoration: none;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.paper-item:last-child {
  border-bottom: none;
}

.paper-item:hover {
  background-color: #faf5ff;
}

.paper-item.highlight {
  background-color: #faf5ff;
}

.paper-date {
  text-align: center;
  min-width: 40px;
}

.paper-date .day {
  display: block;
  font-size: 20px;
  font-weight: 700;
  color: #7c3aed;
}

.paper-item.highlight .paper-date .day {
  color: #6d28d9;
}

.paper-date .year-month {
  display: block;
  font-size: 9px;
  color: #9ca3af;
}

.paper-title {
  font-size: 12px;
  color: #374151;
  margin: 4px 0 0 0;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.paper-item.highlight .paper-title {
  color: #581c87;
  font-weight: 500;
}

/* 教学科研样式 */
.research-top {
  display: flex;
  gap: 10px;
}

.research-image {
  width: 100px;
  height: 70px;
  border-radius: 4px;
  flex-shrink: 0;
}

.research-items {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.research-link {
  font-size: 11px;
  color: #4b5563;
  text-decoration: none;
  display: flex;
  align-items: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.research-link:hover {
  color: #2563eb;
}

.dot {
  margin-right: 4px;
  font-weight: bold;
}

.dot.blue {
  color: #2563eb;
}

.dot.gray {
  color: #9ca3af;
}

.news-list {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.news-item {
  font-size: 11px;
  color: #374151;
  text-decoration: none;
  display: flex;
  align-items: center;
  padding: 3px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.news-item:hover {
  color: #2563eb;
}

.news-item.first {
  color: #2563eb;
  font-weight: 500;
}

:deep(.el-divider) {
  margin: 10px 0;
}

@media (max-width: 768px) {
  .row,
  .row-flex {
    flex-direction: column;
  }

  .featured-awards,
  .activities-grid {
    grid-template-columns: 1fr;
  }
}
</style>