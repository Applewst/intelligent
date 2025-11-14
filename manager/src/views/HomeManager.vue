<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'
import { getStudentList } from '@/api/student'
import { getGraduateList } from '@/api/graduate'
import { GetPaperList } from '@/api/SearchApi'

const currentDate = ref(new Date())
const studentChart = ref(null)
const paperChart = ref(null)
const activityChart = ref(null)
const studentDate = ref([])
const paperData = ref({ years: [], counts: [] })

const isToday = (date) => {
  const today = new Date()
  const checkDate = new Date(date)
  return today.toDateString() === checkDate.toDateString()
}

const GetStudentDate = async (pageNum,pageSize,name) => {
  const res1 = await getStudentList(pageNum,pageSize,name)
  const res2 = await getGraduateList(pageNum,pageSize,name)
  studentDate.value = [res2.data.total, res1.data.data.total]
  console.log(studentDate.value)
}

const GetPaperData = async (pageNum,pageSize,author,title) => {
  try {
    const res = await GetPaperList(pageNum,pageSize,author,title)
      console.log(res)
      
      const papers = res.data.data
      
      // 按年份统计论文数量
      const yearMap = {}
      papers.forEach(paper => {
        const year = paper.time.substring(0, 4) // 提取年份
        yearMap[year] = (yearMap[year] || 0) + 1
      })
      
      // 将结果转换为数组并排序
      const years = Object.keys(yearMap).sort()
      const counts = years.map(year => yearMap[year])
      
      paperData.value = { years, counts }
      console.log('论文按年份统计:', { years, counts })
    
  } catch (error) {
    console.error('获取论文数据失败:', error)
  }
}

onMounted(() => {
  GetStudentDate('','','')
  GetPaperData('','','','')
})

// 监听 studentDate 的变化，数据获取完成后初始化或更新图表
watch(studentDate, (newData) => {
  if (newData.length > 0) {
    initStudentChart()
  }
})

watch(paperData, (newData) => {
  if (newData.years.length > 0) {
    initPaperChart()
  }
}, { deep: true })

// 初始化学生图表 - 条形图
const initStudentChart = () => {
  if (!studentChart.value) return
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
      data: ['毕业生', '在校生']
    },
    series: [
      {
        name: '人数',
        type: 'bar',
        data: studentDate.value,
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

const initPaperChart = () => {
  if (!paperChart.value) return
  const chart = echarts.init(paperChart.value)
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['数量'],
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
      data: paperData.value.years.map(year => year + '年')
    },
    yAxis: {
      type: 'value',
      minInterval: 1
    },
    series: [
      {
        name: '数量',
        type: 'line',
        data: paperData.value.counts,
        smooth: true,
        itemStyle: { color: '#66C23A' },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(102, 194, 58, 0.3)' },
            { offset: 1, color: 'rgba(102, 194, 58, 0.05)' }
          ])
        }
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

// 在组件挂载时初始化其他图表
onMounted(() => {
  initActivityChart()
})
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
            <!-- 更新标签文本为按年份 -->
            <el-tag type="success">按年份统计</el-tag>
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
</style>
