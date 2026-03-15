<template>
  <div class="admission-container">
    <div class="admission-header">
      <h1 class="admission-title">招生信息</h1>
    </div>
    
    <div class="admission-content">
      <div class="admission-sidebar">
        <el-menu
          class="admission-menu"
          :default-active="activeSideMenu"
          @select="handleSideMenuSelect"
        >
          <el-menu-item index="admission">招生简章</el-menu-item>
          <el-menu-item index="supervisor">导师组名单</el-menu-item>
          <el-menu-item index="faq">招生问答</el-menu-item>
        </el-menu>
      </div>
      
      <div class="admission-main">
        <div v-if="activeSideMenu === 'admission'" class="admission-list">
          <div class="admission-item" v-for="(item, index) in admissionList" :key="index">
            <div class="admission-item-content">
              <a href="#" class="admission-item-title">{{ item.title }}</a>
              <span class="admission-item-date">{{ item.date }}</span>
            </div>
          </div>
        </div>
        
        <div v-if="activeSideMenu === 'supervisor'" class="supervisor-list">
          <div class="supervisor-item" v-for="(supervisor, index) in supervisorList" :key="index">
            <div class="supervisor-header">
              <img :src="supervisor.avatar" :alt="supervisor.name" class="supervisor-avatar">
              <div class="supervisor-info">
                <h3 class="supervisor-name">{{ supervisor.name }}</h3>
                <p class="supervisor-title">{{ supervisor.title }}</p>
                <p class="supervisor-email">{{ supervisor.email }}</p>
              </div>
            </div>
            <div class="supervisor-research">
              <h4>研究方向</h4>
              <p>{{ supervisor.research }}</p>
            </div>
          </div>
        </div>
        
        <div v-if="activeSideMenu === 'faq'" class="faq-list">
          <el-collapse v-model="activeFaq">
            <el-collapse-item v-for="(faq, index) in faqList" :key="index" :name="index">
              <template #title>
                <span class="faq-question">{{ faq.question }}</span>
              </template>
              <div class="faq-answer">{{ faq.answer }}</div>
            </el-collapse-item>
          </el-collapse>
        </div>
        
        <div v-if="activeSideMenu === 'admission'" class="admission-pagination">
          <el-pagination
            background
            layout="prev, pager, next"
            :total="60"
            :page-size="10"
            :current-page="currentPage"
            @current-change="handleCurrentChange"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const activeSideMenu = ref('admission')
const currentPage = ref(1)
const activeFaq = ref([])

const admissionList = [
  {
    title: '2026年鹏城国家实验室-中山大学联合培养博士研究生专项计划招生简章',
    date: '2025-12-28'
  },
  {
    title: '鹏城国家实验室-北京邮电大学2026年联合培养博士研究生专项计划招生简章',
    date: '2025-12-23'
  },
  {
    title: '2026年鹏城国家实验室-东南大学联合培养博士研究生专项计划招生简章',
    date: '2025-11-18'
  },
  {
    title: '鹏城国家实验室-复旦大学2026年联合培养博士研究生专项计划招生简章',
    date: '2025-11-18'
  },
  {
    title: '2026年鹏城国家实验室-南方科技大学联合培养博士研究生专项计划招生简章',
    date: '2025-11-14'
  },
  {
    title: '鹏城国家实验室-中山大学2026年联合培养博士研究生专项计划项目简介',
    date: '2025-10-23'
  },
  {
    title: '2026年哈尔滨工业大学-鹏城国家实验室联合培养博士研究生招生项目简介',
    date: '2025-09-26'
  },
  {
    title: '鹏城国家实验室-哈尔滨工业大学关于2026年优秀本科应届毕业生免试攻读联合培养博士的通知',
    date: '2025-09-12'
  },
  {
    title: '鹏城国家实验室-清华大学联合培养博士专项计划2026年招生简章',
    date: '2025-07-07'
  },
  {
    title: '2025年鹏城国家实验室-哈尔滨工业大学联合培养博士研究生招生项目简介',
    date: '2025-03-11'
  }
]

const supervisorList = [
  {
    name: '张教授',
    title: '教授、博士生导师',
    email: 'zhang@example.com',
    research: '人工智能、机器学习、深度学习、计算机视觉',
    avatar: 'https://via.placeholder.com/150'
  },
  {
    name: '李教授',
    title: '教授、博士生导师',
    email: 'li@example.com',
    research: '自然语言处理、知识图谱、信息检索',
    avatar: 'https://via.placeholder.com/150'
  },
  {
    name: '王教授',
    title: '副教授、硕士生导师',
    email: 'wang@example.com',
    research: '数据挖掘、大数据分析、推荐系统',
    avatar: 'https://via.placeholder.com/150'
  },
  {
    name: '赵教授',
    title: '教授、博士生导师',
    email: 'zhao@example.com',
    research: '计算机视觉、图像处理、模式识别',
    avatar: 'https://via.placeholder.com/150'
  },
  {
    name: '陈教授',
    title: '副教授、博士生导师',
    email: 'chen@example.com',
    research: '机器学习、强化学习、智能决策',
    avatar: 'https://via.placeholder.com/150'
  }
]

const faqList = [
  {
    question: '如何申请博士研究生？',
    answer: '申请人需在规定时间内通过学校研究生招生系统提交申请材料，包括个人简历、研究计划、推荐信等。具体申请流程请参考各高校的招生简章。'
  },
  {
    question: '导师组名单如何选择？',
    answer: '申请人可以在申请系统中选择意向导师，建议提前与导师进行沟通，了解研究方向和招生计划。导师组名单会定期更新，请关注最新信息。'
  },
  {
    question: '联合培养项目的优势是什么？',
    answer: '联合培养项目可以让学生同时享受两个平台的资源，获得更广阔的学术视野和科研机会。学生将在鹏城国家实验室和合作高校进行学习和研究，获得双导师指导。'
  },
  {
    question: '招生对象有哪些要求？',
    answer: '招生对象为应届本科毕业生、硕士毕业生或具有同等学力的人员。具体要求包括学术成绩、科研能力、英语水平等，具体标准请参考各高校的招生简章。'
  },
  {
    question: '学费和奖学金政策如何？',
    answer: '联合培养博士研究生享受合作高校的学费政策和奖学金政策。鹏城国家实验室提供额外的生活补贴和科研奖励，具体金额请咨询招生办公室。'
  },
  {
    question: '毕业要求是什么？',
    answer: '毕业要求包括完成规定的课程学习、通过博士资格考试、发表高水平学术论文、完成博士论文并通过答辩。具体要求请参考合作高校的博士培养方案。'
  },
  {
    question: '如何联系招生办公室？',
    answer: '您可以通过以下方式联系我们：邮箱：admission@example.com，电话：0731-XXXXXXX，地址：湖南省株洲市天元区泰山西路88号。'
  }
]

const handleSideMenuSelect = (key) => {
  activeSideMenu.value = key
  activeFaq.value = []
}

const handleCurrentChange = (val) => {
  currentPage.value = val
}
</script>

<style scoped>
.admission-container {
  width: 100%;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.admission-header {
  margin-bottom: 30px;
}

.admission-title {
  font-size: 24px;
  font-weight: 600;
  color: #333;
  border-bottom: 2px solid #409eff;
  padding-bottom: 10px;
  margin: 0;
}

.admission-content {
  display: flex;
  gap: 30px;
}

.admission-sidebar {
  width: 200px;
  flex-shrink: 0;
}

.admission-menu {
  border-right: none;
  background-color: #f8f9fa;
  border-radius: 4px;
  overflow: hidden;
}

.admission-menu :deep(.el-menu-item) {
  height: 50px;
  line-height: 50px;
  border-bottom: 1px solid #e9ecef;
  color: #555;
  transition: all 0.3s;
}

.admission-menu :deep(.el-menu-item:hover) {
  background-color: #e3f2fd;
  color: #409eff;
}

.admission-menu :deep(.el-menu-item.is-active) {
  background-color: #409eff;
  color: #fff;
}

.admission-main {
  flex: 1;
  background-color: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 20px;
}

.admission-list {
  margin-bottom: 20px;
}

.admission-item {
  border-bottom: 1px solid #e9ecef;
  padding: 15px 0;
  transition: all 0.3s;
}

.admission-item:hover {
  background-color: #f8f9fa;
  padding-left: 10px;
}

.admission-item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.admission-item-title {
  font-size: 16px;
  color: #333;
  text-decoration: none;
  flex: 1;
  margin-right: 20px;
  line-height: 1.4;
  transition: color 0.3s;
}

.admission-item-title:hover {
  color: #409eff;
  text-decoration: underline;
}

.admission-item-date {
  font-size: 14px;
  color: #999;
  white-space: nowrap;
}

.admission-pagination {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.supervisor-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.supervisor-item {
  background-color: #fff;
  border: 1px solid #e9ecef;
  border-radius: 8px;
  padding: 20px;
  transition: all 0.3s;
}

.supervisor-item:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.supervisor-header {
  display: flex;
  gap: 15px;
  margin-bottom: 15px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e9ecef;
}

.supervisor-avatar {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid #409eff;
}

.supervisor-info {
  flex: 1;
}

.supervisor-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0 0 5px 0;
}

.supervisor-title {
  font-size: 14px;
  color: #666;
  margin: 0 0 5px 0;
}

.supervisor-email {
  font-size: 13px;
  color: #999;
  margin: 0;
}

.supervisor-research {
  margin-top: 15px;
}

.supervisor-research h4 {
  font-size: 14px;
  font-weight: 600;
  color: #409eff;
  margin: 0 0 8px 0;
}

.supervisor-research p {
  font-size: 13px;
  color: #666;
  line-height: 1.6;
  margin: 0;
}

.faq-list {
  background-color: #fff;
  border-radius: 4px;
  padding: 20px;
}

.faq-list :deep(.el-collapse) {
  border: none;
}

.faq-list :deep(.el-collapse-item) {
  margin-bottom: 10px;
  border: 1px solid #e9ecef;
  border-radius: 4px;
  overflow: hidden;
}

.faq-list :deep(.el-collapse-item__header) {
  background-color: #f8f9fa;
  padding: 15px 20px;
  font-weight: 500;
  color: #333;
  transition: all 0.3s;
}

.faq-list :deep(.el-collapse-item__header:hover) {
  background-color: #e3f2fd;
  color: #409eff;
}

.faq-list :deep(.el-collapse-item__wrap) {
  background-color: #fff;
  border: none;
}

.faq-list :deep(.el-collapse-item__content) {
  padding: 15px 20px;
  color: #666;
  line-height: 1.8;
}

.faq-question {
  font-size: 15px;
}

.faq-answer {
  font-size: 14px;
  color: #666;
  line-height: 1.8;
}

@media (max-width: 768px) {
  .admission-content {
    flex-direction: column;
  }
  
  .admission-sidebar {
    width: 100%;
  }
  
  .admission-menu {
    display: flex;
    border-radius: 4px;
    overflow-x: auto;
  }
  
  .admission-menu :deep(.el-menu-item) {
    border-bottom: none;
    border-right: 1px solid #e9ecef;
    white-space: nowrap;
  }
  
  .admission-item-content {
    flex-direction: column;
    align-items: flex-start;
    gap: 5px;
  }
  
  .admission-item-date {
    align-self: flex-end;
  }
  
  .supervisor-list {
    grid-template-columns: 1fr;
  }
  
  .supervisor-header {
    flex-direction: column;
    align-items: center;
    text-align: center;
  }
  
  .supervisor-avatar {
    width: 100px;
    height: 100px;
  }
  
  .faq-list :deep(.el-collapse-item__header) {
    padding: 12px 15px;
    font-size: 14px;
  }
  
  .faq-list :deep(.el-collapse-item__content) {
    padding: 12px 15px;
    font-size: 13px;
  }
}
</style>