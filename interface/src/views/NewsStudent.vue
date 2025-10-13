<script setup>
import { ref } from 'vue'
const introduceList = ref([
  {
    title: '推荐交流',
    content: '这是具体的描述，这里可以写一些推荐信息，比如推荐的讲座、活动、比赛等等。',
    card:[
      {
        cardTitle: '张三赴美交流',
        img: '../assets/images/1.jpg',
        tooltip: '张三的交流详情'
      },
      {
        cardTitle: '李四赴美交流',
        img: '../assets/images/2.jpg',
        tooltip: '李四的交流详情'
      },
      
    ],
    id: 1
  },
  {
    title: '实习实践',
    content: '这是具体的描述，这里可以写一些实习实践的信息，比如实习单位、实习内容、实习时间等等。',
    card:[
      {
        cardTitle: '王五实习',
        img: '../assets/images/3.jpg',
        tooltip: '王五的实习详情'
      },
      {
        cardTitle: '赵六实习',
        img: '../assets/images/4.jpg',
        tooltip: '赵六的实习详情'
      },
    ],
    id: 2
  },
  
])

//会议列表
const meetingList = ref([
  {
    title: '实验室论文被期刊INFORMATION SCIENCES录用',
    description: '实验室论文 “Finding Weighted K-Truss Communities I”',
    date: '24 八月, 2017',
    image: '../assets/images/meetings/1.jpg'
  },
  {
    title: '实验室论文被服务计算领域旗舰会议ICSOC 2017录用',
    description: '实验室论文 “An Embedding based Factorization Machine”',
    date: '28 七月, 2017',
    image: '../assets/images/meetings/2.jpg'
  },
  {
    title: '实验室成员获2017 CCF “大数据与计算智能大赛” 两项二等奖及一项三等奖',
    description: '2017年12月24日，CCF第五届“大数据与计算智能大赛 (Big Data & AI 2017)',
    date: '2017年12月22日-24日',
    image: '../assets/images/meetings/3.jpg'
  },
  {
    title: '实验室成员获“2017 IBM区块链技术黑客马拉松全国校园大赛”三等奖',
    description: '2017年12月22日-24日，IBM区块链技术黑客马拉松全国校园大赛',
    date: '2017年12月22日-24日',
    image: '../assets/images/meetings/4.jpg'
  },
]);
</script>
<template>
  <div class="team-introduce">
    <h1>学生发展</h1>
    <div class="student-introduce">
      <div class="introduce-item" v-for="item in introduceList" :key="item.id">
        <div class="introduce-left">
          <h3 class="introduce-title">{{item.title}}</h3>
          <p class="introduce-content">
           {{item.content}}
          </p>
        </div>
        <div class="introduce-right" v-for="(card, index) in item.card" :key="index">
          <!-- 第一个 el-card -->
          <el-card
            style="max-width: 480px; max-height: 260px;"
            shadow="hover"
            class="card-with-tooltip"
          >
            <template #header >{{card.cardTitle}}</template>
            <img
              class="image"
              :src="card.img"
              style="width: 100%"
            />
            <!-- 提示框 -->
            <div class="tooltip">{{card.tooltip}}</div>
          </el-card>

        
        </div>
      </div>
      
    </div>
    
     <div class="international-meetings">
    <h1 class="meetings-title">参加国际会议</h1>
    <el-row :gutter="20" class="international-row">
      <el-col :span="6" v-for="(meeting, index) in meetingList" :key="index" class="internationnal-content">
        <el-card :body-style="{ padding: '0px' }" shadow="hover" class="international-card">
          <img :src="meeting.image" class="image" />
          <div style="padding: 14px;">
            <h3>{{ meeting.title }}</h3>
            <p>{{ meeting.description }}</p>
            <p class="date">{{ meeting.date }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
  </div>
</template>

<style scoped lang="less">
.team-introduce {
  text-align: center;
  margin-top: 100px;
  font-size: 24px;
  color: #333;
}
h1 {
  margin-bottom: 40px;
}
.student-introduce {
  margin-top: 50px;
  max-width: 1200px;
  margin: 0 auto;
  .introduce-item {
    display: flex;
    height: 300px;
    margin-bottom: 30px;
    justify-content: space-around;
    padding: 20px;
    .introduce-left {
      width: 50%;
      background-color: #f0f0f0;
      margin-right: 10px;
      .introduce-title {
        font-size: 28px;
        font-weight: bold;
        margin-bottom: 10px;
      }
    }
  }
  .shixi-item {
    height: 300px;
  }
}
.introduce-content {
  font-size: 14px;
  margin-bottom: 10px;
  margin: 10px auto;
}
.introduce-right {
  display: flex;
  justify-content: space-between;
}

/* el-card 添加相对定位 */
.card-with-tooltip {
  font-size: 20px;
  width: 180px;
  position: relative; /* 使 el-card 成为提示框的定位容器 */
}

/* 图片样式 */
.image {
  width: 200px; /* 根据需要调整图片宽度 */
  object-fit: cover;
}

/* 提示框样式 */
.tooltip {
  visibility: hidden; /* 默认隐藏提示框 */
  background-color: rgba(0, 0, 0, 0.8); /* 黑色背景，半透明 */
  color: white; /* 白色文字 */
  padding: 5px 10px; /* 内边距 */
  border-radius: 4px; /* 圆角 */
  position: absolute;
  bottom: 0%; /* 初始时位置在卡片底部 */
  left: 0%; /* 水平居中 */
  transform: translateY(100%) scale(0.8); /* 初始时缩小并往下偏移 */
  z-index: 1; /* 确保提示框在卡片上方 */
  opacity: 0; /* 初始时透明度为 0 */
  transition: opacity 0.3s, transform 0.3s; /* 动画过渡效果 */
  pointer-events: none; /* 确保提示框不阻碍鼠标事件 */
}

/* 鼠标悬停时显示提示框（从下往上出现） */
.card-with-tooltip:hover .tooltip {
  visibility: visible; /* 显示提示框 */
  transform: translateY(0) scale(1); /* 提示框回到原位并放大 */
  opacity: 1; /* 透明度变为 1，显示完全 */
}
// 新增国际会议部分样式
.international-meetings {
  
  max-width: 1200px;
  margin: 0 auto;
  margin-top: 50px;
  padding: 20px;
  background-color: #fff;
  text-align: center;
}

.meetings-title {
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
}

.international-row {
  margin-bottom: 20px;
}

.internationnal-content {
  text-align: center;
  .international-card{
  .image {
    width: 100%;
    height: 200px;
    display: block;
  }

  h3 {
    font-size: 16px;
    margin: 10px 0;
  }

  p {
    margin: 0;
  }

  .date {
    font-size: 12px;
    color: #999;
  }
}
}


</style>