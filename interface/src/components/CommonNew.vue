<script setup>
import { ref, computed, onMounted } from 'vue'
import { ArrowRight } from '@element-plus/icons-vue'
import {GetsearchPapers} from '@/api/search'
import { ElMessage } from 'element-plus'

const activeLeftTab = ref('award')
const activeRightTab = ref('paper')

const leftTabs = [
  { key: 'award', label: '获奖荣誉' },
  { key: 'activity', label: '团队活动' }
]

const rightTabs = [
  { key: 'paper', label: '论文发表' },
  { key: 'project', label: '研究项目' }
]

// 左侧数据
const awardsNews = ref([
  {
    id: 1,
    title: '我院教师荣获国家自然科学基金优秀青年科学基金项目',
    description: '近日，国家自然科学基金委员会公布了2025年度优秀青年科学基金项目评审结果...',
    image: 'https://tse1-mm.cn.bing.net/th/id/OIP-C.9a4umpl2vucdEY3UAtgGWAHaGw?w=186&h=180&c=7&r=0&o=7&cb=ucfimg2&dpr=1.1&pid=1.7&rm=3&ucfimg=1',
    day: '15',
    month: '2025.12'
  },
  {
    id: 2,
    title: '团队成员获评省级优秀科技工作者称号',
    description: '在省科技厅组织的评选活动中，我院多名教师凭借突出的科研成果和学术贡献...',
    image: 'https://tse1-mm.cn.bing.net/th/id/OIP-C.9a4umpl2vucdEY3UAtgGWAHaGw?w=186&h=180&c=7&r=0&o=7&cb=ucfimg2&dpr=1.1&pid=1.7&rm=3&ucfimg=1',
    day: '12',
    month: '2025.12'
  },
  {
    id: 3,
    title: '研究成果入选年度十大科技进展',
    description: '由我院牵头完成的重大研究项目成功入选本年度十大科技进展评选...',
    image: 'https://tse1-mm.cn.bing.net/th/id/OIP-C.v2oANx86DIzTQrxI3C9-XgHaEJ?w=307&h=180&c=7&r=0&o=7&cb=ucfimg2&dpr=1.1&pid=1.7&rm=3&ucfimg=1',
    day: '08',
    month: '2025.12'
  },
  {
    id: 4,
    title: '青年教师在国际学术竞赛中获得金奖',
    description: '在刚刚结束的国际学术创新大赛中，我院青年教师团队脱颖而出...',
    image: 'https://tse1-mm.cn.bing.net/th/id/OIP-C.v2oANx86DIzTQrxI3C9-XgHaEJ?w=307&h=180&c=7&r=0&o=7&cb=ucfimg2&dpr=1.1&pid=1.7&rm=3&ucfimg=1',
    day: '05',
    month: '2025.12'
  },
  {
    id: 5,
    title: '实验室获批国家重点实验室建设项目',
    description: '经过多轮严格评审，我院实验室成功获批国家重点实验室建设项目...',
    image: '/placeholder.svg?height=200&width=300',
    day: '01',
    month: '2025.12'
  }
])

const activitiesNews = ref([
  {
    id: 1,
    title: '团队年度学术研讨会圆满举办',
    description: '2025年度团队学术研讨会在学术报告厅成功举办，来自全国各地的专家学者齐聚一堂...',
    image: './assets/images/1.jpg',
    day: '14',
    month: '2025.12'
  },
  {
    id: 2,
    title: '研究生团建活动顺利开展',
    description: '为增强团队凝聚力，丰富研究生课余生活，我院组织开展了户外团建活动...',
    image: '/placeholder.svg?height=200&width=300',
    day: '10',
    month: '2025.12'
  },
  {
    id: 3,
    title: '国际合作交流访问团来访',
    description: '来自欧洲知名高校的学术访问团一行来我院进行学术交流访问...',
    image: '/placeholder.svg?height=200&width=300',
    day: '06',
    month: '2025.12'
  },
  {
    id: 4,
    title: '实验室开放日活动成功举办',
    description: '为促进科学普及，实验室举办了面向公众的开放日活动...',
    image: '/placeholder.svg?height=200&width=300',
    day: '03',
    month: '2025.12'
  },
  {
    id: 5,
    title: '新生入学欢迎会暨导师见面会',
    description: '2025级研究生新生入学欢迎会暨导师见面会在多功能厅举行...',
    image: '/placeholder.svg?height=200&width=300',
    day: '28',
    month: '2025.11'
  }
])

// 右侧数据
const papersNews = ref([
  // { id: 1, title: '清华大学医院管理研究院与默克莱大学健康科学学院签署合作备忘录', day: '16', month: '2025.12' },
  // { id: 2, title: '校领导调研图书馆', day: '16', month: '2025.12' },
  // { id: 3, title: '北京清华长庚医院正式获得北京市科普基地授牌', day: '16', month: '2025.12' },
  // { id: 4, title: '清华附中教师李晟宇获得全国通用技术教学技能大赛一等奖', day: '16', month: '2025.12' },
  // { id: 5, title: '【清华育人故事】地学系王焯：从"小云杉"到"枝繁叶茂"', day: '16', month: '2025.12' },
  // { id: 6, title: '【清华育人故事】车辆学院聂冰冰：亦师亦友，托举青春梦想', day: '16', month: '2025.12' },
  // { id: 7, title: '国家自然科学基金"可解释、可通用的下一代人工智能方法"重大研究计划', day: '16', month: '2025.12' }
])

const researchNews = ref([
  { id: 1, title: '化工系唐城等在过氧化氢绿电合成领域取得新进展', day: '15', month: '2025.12' },
  { id: 2, title: '深圳国际研究生院耿洪亚团队在跨尺度生物样本冷冻保存领域取得新进展', day: '15', month: '2025.12' },
  { id: 3, title: '生命学院陈春来研究组合作揭示全长T-box核糖开关调控翻译起始的构象动态机制', day: '14', month: '2025.12' },
  { id: 4, title: '生医工程学院杜亚楠团队实现基于相分离调控的人工细胞智造和体内应用', day: '14', month: '2025.12' },
  { id: 5, title: '材料学院在新型热电材料研究方面取得重要突破', day: '13', month: '2025.12' },
  { id: 6, title: '计算机系团队在大语言模型安全性研究中取得新进展', day: '12', month: '2025.12' },
  { id: 7, title: '物理系研究组在量子计算领域发表重要论文', day: '11', month: '2025.12' }
])
const GetAllsearchPapers = async (pageNum,pageSize,author,title) => {
  const res = await GetsearchPapers(pageNum,pageSize,author,title)
  console.log(res)
  
  if(res.code === 1){
    res.data.data.filter(item => {
      //将时间time以中间的-分割，取出day和month
      const dateParts = item.time.split('-')
      const month = `${dateParts[0]}.${dateParts[1]}`
      const day = dateParts[2]  
      papersNews.value.push({
        id: item.id,
        title: item.title,
        day: day,
        month: month,
        author: item.author,
      })

      
  
    })
    console.log(papersNews.value)
    
  }else{
    ElMessage.error('获取论文列表失败')
  }
}
onMounted(() => {
  GetAllsearchPapers(1,5,'','')
})

const currentLeftNews = computed(() => {
  return activeLeftTab.value === 'award' ? awardsNews.value : activitiesNews.value
})

const currentRightNews = computed(() => {
  return activeRightTab.value === 'paper' ? papersNews.value : researchNews.value
})
</script>

<template>
  <!-- 顶部最新动态 -->
    <div class="top-title">
    <h2>最新动态</h2>
     </div>
  <div class="latest-news-container">
  
    <!-- 左侧区域 -->
    <div class="left-section">
      <!-- 左侧垂直导航 -->
      <div class="left-nav">
        <div
          v-for="tab in leftTabs"
          :key="tab.key"
          :class="['nav-item', { active: activeLeftTab === tab.key }]"
          @click="activeLeftTab = tab.key"
        >
          {{ tab.label }}
          <el-icon v-if="activeLeftTab === tab.key" class="arrow-icon"><ArrowRight /></el-icon>
        </div>
      </div>

      <!-- 左侧内容区域 -->
      <div class="left-content">
        <div class="news-list">
          <div
            v-for="item in currentLeftNews"
            :key="item.id"
            class="news-item-with-image"
          >
            <div class="image-wrapper">
              <img :src="item.image" :alt="item.title" />
              <div class="date-badge">
                <span class="day">{{ item.day }}</span>
                <span class="month">{{ item.month }}</span>
              </div>
            </div>
            <div class="news-info">
              <h3 class="news-title">{{ item.title }}</h3>
              <p class="news-desc">{{ item.description }}</p>
            </div>
          </div>
        </div>
        <div class="more-link">
          <router-link v-if="activeLeftTab==='award'" :to="`/search/${activeLeftTab}`">更多 &gt;</router-link>
          <router-link v-else :to="`/news/${activeLeftTab}`">更多 &gt;</router-link>
          <!-- <a href="" v-if="activeLeftTab === 'awards'">更多 &gt;</a> -->
        </div>
      </div>
    </div>

    <!-- 右侧区域 -->
    <div class="right-section">
      <!-- 右侧水平标签 -->
      <div class="right-tabs">
        <div
          v-for="tab in rightTabs"
          :key="tab.key"
          :class="['tab-item', { active: activeRightTab === tab.key }]"
          @mouseover="activeRightTab = tab.key"
        >
          {{ tab.label }}
        </div>
      </div>

      <!-- 右侧内容 -->
      <div class="right-content">
        <div class="text-news-list">
          <div
            v-for="item in currentRightNews"
            :key="item.id"
            class="text-news-item"
          >
            <div class="text-date">
              <span class="text-day">{{ item.day }}</span>
              <span class="text-month">{{ item.month }}</span>
            </div>
            <h3 class="text-title">{{ item.title }}</h3>
            <!-- 放作者 -->
            <p class="text-author">{{ item.author }}</p>
          </div>
        </div>
        <div class="right-more-link">
          <router-link :to="`/search/${activeRightTab}`">更多 &gt;</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="less">

.top-title {
  margin-top: 100px;
  font-size: 30px;
  font-weight: bold;
}
.latest-news-container {
  display: flex;
  gap: 24px;
  max-width: 1400px;
  margin: 10px auto;
  padding: 24px;
  font-family: 'Microsoft YaHei', 'PingFang SC', sans-serif;
}

/* 左侧区域 */
.left-section {
  display: flex;
  flex: 1;
  background: #fff;
}

.left-nav {
  width: 130px;
  flex-shrink: 0;
  /* border-right: 1px solid #eee; */
}

/* 左侧导航项：添加0.5秒过渡动画 */
.nav-item {
  padding: 20px 16px;
  cursor: pointer;
  color: #666;
  font-size: 15px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  min-width: 30px;
  transition: all 0.5s ease;
  border-bottom: 1px solid #eee;
}

/* 左侧导航悬停效果 */
.nav-item:hover {
  color: #729cf0;
  font-size: 18px;
  background: linear-gradient(to right, rgba(114, 156, 240, 0.15), transparent);
  border-right: #729cf0 4px solid;
}

.nav-item.active {
  color: #729cf0;
  font-weight: 600;
  background: linear-gradient(to right, rgba(114, 156, 240, 0.1), transparent);
}

.arrow-icon {
  color: #729cf0;
}

.left-content {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.news-list {
  flex: 1;
  padding: 16px;
}

.news-item-with-image {
  display: flex;
  gap: 16px;
  padding: 16px 0;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background 0.5s ease;
}

.news-item-with-image:hover {
  background: #fafafa;
}

.news-item-with-image:last-child {
  border-bottom: none;
}

.image-wrapper {
  position: relative;
  width: 200px;
  height: 130px;
  flex-shrink: 0;
  border-radius: 4px;
  overflow: hidden;
}

.image-wrapper img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.date-badge {
  position: absolute;
  bottom: 8px;
  left: 8px;
  background: #729cf0;
  color: #fff;
  padding: 6px 10px;
  border-radius: 4px;
  text-align: center;
  line-height: 1.2;
}

.date-badge .day {
  display: block;
  font-size: 20px;
  font-weight: bold;
}

.date-badge .month {
  display: block;
  font-size: 11px;
  opacity: 0.9;
}

.news-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.news-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0 0 10px 0;
  line-height: 1.5;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  transition: color 0.5s ease;
}

.news-title:hover {
  color: #729cf0;
}

.news-desc {
  font-size: 13px;
  color: #888;
  margin: 0;
  line-height: 1.6;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.more-link {
  padding: 16px;
  text-align: right;
  border-top: 1px solid #eee;
}

.more-link a {
  color: #666;
  text-decoration: none;
  font-size: 14px;
  transition: color 0.5s ease;
}

.more-link a:hover {
  color: #729cf0;
}

/* 右侧区域 */
.right-section {
  width: 420px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.right-tabs {
  display: flex;
}

/* 右侧标签：非当前项为灰色背景，添加0.5秒过渡动画 */
.tab-item {
  flex: 1;
  padding: 16px;
  text-align: center;
  cursor: pointer;
  font-size: 16px;
  font-weight: 500;
  background: #e5e5e5;
  color: #666;
  transition: all 0.5s ease;
}

/* 右侧标签悬停效果 */
.tab-item:hover {
  background: #d0d0d0;
  color: #333;
}

/* 右侧标签当前项为蓝色 */
.tab-item.active {
  background: #729cf0;
  color: #fff;
}

.tab-item:first-child {
  border-radius: 4px 0 0 0;
}

.tab-item:last-child {
  border-radius: 0 4px 0 0;
}


.right-content {
  flex: 1;
  background: #729cf0;
  display: flex;
  flex-direction: column;
  border-radius: 0 0 4px 4px;
}

.text-news-list {
  flex: 1;
  padding: 8px 16px;
}

.text-news-item {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  position: relative;
  padding: 14px 0;
  border-bottom: 1px solid rgba(255, 255, 255, 0.15);
  /* cursor: pointer; */
  transition: opacity 0.5s ease;
  // 作者
  .text-author {
    font-size: 13px;
    color: #ddd;
    //放在右下角
    position: absolute;
    bottom: 8px;
    right: 8px;
    margin: 4px 0 0 0;
    line-height: 1.4;
  }
}

/* .text-news-item:hover {
  opacity: 0.85;
} */

.text-news-item:last-child {
  border-bottom: none;
}

.text-date {
  flex-shrink: 0;
  text-align: center;
  color: #fff;
  line-height: 1.2;
}

.text-day {
  display: block;
  font-size: 28px;
  font-weight: bold;
}

.text-month {
  display: block;
  font-size: 12px;
  opacity: 0.85;
}

.text-title {
  font-size: 15px;
  color: #fff;
  margin: 0;
  line-height: 1.6;
  font-weight: normal;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.right-more-link {
  padding: 12px 16px;
  text-align: right;
  border-top: 1px solid rgba(255, 255, 255, 0.15);
}

.right-more-link a {
  color: #fff;
  text-decoration: none;
  font-size: 14px;
  transition: opacity 0.5s ease;
}

.right-more-link a:hover {
  opacity: 0.85;
}
</style>
