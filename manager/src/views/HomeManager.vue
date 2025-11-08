<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const currentDate = ref(new Date())
const studentChart = ref(null)
const paperChart = ref(null)
const activityChart = ref(null)

const isToday = (date) => {
  const today = new Date()
  const checkDate = new Date(date)
  return today.toDateString() === checkDate.toDateString()
}

onMounted(() => {
  initStudentChart()
  initPaperChart()
  initActivityChart()
})

// 初始化学生图表 - 条形图
const initStudentChart = () => {
  const chart = echarts.init(studentChart.value)
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: { type: 'shadow' }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
      boundaryGap: [0, 0.01]
    },
    yAxis: {
      type: 'category',
      data: ['毕业生','在校生']
    },
    series: [
      {
        name: '人数',
        type: 'bar',
        data: [8, 15, 5, 3],
        itemStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: '#409EFF' },
            { offset: 1, color: '#79bbff' }
          ])
        },
        barWidth: '50%'
      }
    ]
  }
  chart.setOption(option)
  window.addEventListener('resize', () => chart.resize())
}

// 初始化论文图表 - 折线图
const initPaperChart = () => {
  const chart = echarts.init(paperChart.value)
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['SCI论文', 'EI论文', '核心期刊'],
      top: '0%',
      left: 'center'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '15%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['1月', '2月', '3月', '4月', '5月', '6月']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: 'SCI论文',
        type: 'line',
        data: [3, 5, 4, 7, 6, 8],
        smooth: true,
        itemStyle: { color: '#67C23A' }
      },
      {
        name: 'EI论文',
        type: 'line',
        data: [2, 3, 4, 3, 5, 6],
        smooth: true,
        itemStyle: { color: '#409EFF' }
      },
      {
        name: '核心期刊',
        type: 'line',
        data: [4, 4, 5, 6, 7, 5],
        smooth: true,
        itemStyle: { color: '#E6A23C' }
      }
    ]
  }
  chart.setOption(option)
  window.addEventListener('resize', () => chart.resize())
}

// 初始化科研活动图表 - 折线图
const initActivityChart = () => {
  const chart = echarts.init(activityChart.value)
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['学术会议', '项目申报', '成果转化'],
      top: '0%',
      left: 'center'
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '15%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '学术会议',
        type: 'line',
        data: [2, 3, 2, 4, 3, 5, 4, 3, 6, 5, 4, 3],
        smooth: true,
        itemStyle: { color: '#F56C6C' }
      },
      {
        name: '项目申报',
        type: 'line',
        data: [1, 2, 3, 2, 4, 3, 2, 3, 4, 5, 3, 2],
        smooth: true,
        itemStyle: { color: '#909399' }
      },
      {
        name: '成果转化',
        type: 'line',
        data: [1, 1, 2, 1, 2, 3, 2, 2, 3, 4, 3, 2],
        smooth: true,
        itemStyle: { color: '#9370DB' }
      }
    ]
  }
  chart.setOption(option)
  window.addEventListener('resize', () => chart.resize())
}
</script>

<template>
  <div class="dashboard-container">
    <div class="main-content">
      <!-- 个人卡片 -->
      <el-card class="user-card" shadow="hover">
        <div class="user-info">
          <el-avatar :size="80" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png" />
          <div class="user-details">
            <h2>张教授</h2>
            <p class="user-role">研究生导师</p>
            <p class="user-dept">计算机科学与技术学院</p>
          </div>
          <div class="user-stats">
            <div class="stat-item">
              <div class="stat-value">28</div>
              <div class="stat-label">在读学生</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">156</div>
              <div class="stat-label">发表论文</div>
            </div>
            <div class="stat-item">
              <div class="stat-value">42</div>
              <div class="stat-label">科研项目</div>
            </div>
          </div>
        </div>
      </el-card>

      <!-- 学生图表 - 条形图 -->
      <el-card class="chart-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <span class="card-title">学生统计</span>
            <el-tag type="primary">本学期</el-tag>
          </div>
        </template>
        <div ref="studentChart" class="chart"></div>
      </el-card>

      <!-- 论文图表 - 折线图 -->
      <el-card class="chart-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <span class="card-title">论文发表趋势</span>
            <el-tag type="success">近6个月</el-tag>
          </div>
        </template>
        <div ref="paperChart" class="chart"></div>
      </el-card>

      <!-- 科研活动图表 - 折线图 -->
      <el-card class="chart-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <span class="card-title">科研活动统计</span>
            <el-tag type="warning">本年度</el-tag>
          </div>
        </template>
        <div ref="activityChart" class="chart"></div>
      </el-card>
    </div>

    <!-- 右侧日历 -->
    <div class="sidebar-calendar">
      <el-card shadow="always">
        <el-calendar v-model="currentDate">
          <template #date-cell="{ data }">
            <div :class="['calendar-day', { 'is-today': isToday(data.day) }]">
              {{ data.day.split('-').slice(2).join('-') }}
            </div>
          </template>
        </el-calendar>
      </el-card>
    </div>
  </div>
</template>


<style scoped lang="less">
.dashboard-container {
  display: flex;
  gap: 20px;
  padding: 20px;
  background: #f5f7fa;
  min-height: 100vh;
}

.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.user-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
}

.user-card :deep(.el-card__body) {
  padding: 30px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 30px;
}

.user-details {
  flex: 1;
}

.user-details h2 {
  margin: 0 0 8px 0;
  font-size: 28px;
  font-weight: 600;
}

.user-role {
  margin: 0 0 4px 0;
  font-size: 16px;
  opacity: 0.9;
}

.user-dept {
  margin: 0;
  font-size: 14px;
  opacity: 0.8;
}

.user-stats {
  display: flex;
  gap: 40px;
}

.stat-item {
  text-align: center;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 14px;
  opacity: 0.9;
}

.chart-card {
  transition: transform 0.3s;
}

.chart-card:hover {
  transform: translateY(-4px);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.chart {
  width: 100%;
  height: 350px;
}

.sidebar-calendar {
  width: 320px;
  position: sticky;
  top: 20px;
  height: fit-content;
  max-height: 420px;
}

.sidebar-calendar :deep(.el-card__body) {
  padding: 8px;
}

.calendar-day {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  font-size: 12px;
}

.calendar-day.is-today {
  background: #409EFF;
  color: white;
  border-radius: 50%;
  width: 28px;
  height: 28px;
  margin: 0 auto;
  font-weight: bold;
}

:deep(.el-calendar-table .el-calendar-day) {
  padding: 4px;
  height: 45px;
}

:deep(.el-calendar__header) {
  padding: 8px 12px;
  border-bottom: 1px solid #ebeef5;
  font-size: 14px;
}

:deep(.el-calendar__title) {
  font-size: 14px;
}

:deep(.el-calendar-table thead th) {
  padding: 6px 0;
  font-size: 12px;
  font-weight: 500;
}

:deep(.el-calendar__button-group) {
  display: flex;
  gap: 4px;
}

@media (max-width: 1200px) {
  .dashboard-container {
    flex-direction: column;
  }
  
  .sidebar-calendar {
    width: 100%;
    position: static;
    max-height: none;
  }
}
</style>
