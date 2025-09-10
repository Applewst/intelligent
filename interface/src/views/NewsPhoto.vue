<script setup>
import { ref } from 'vue';
    const List= ref([
      {
        title: '团队风采',
        content:[
          {
            image: "https://example.com/team1.jpg",
            description: "团队第一次会议",
            timestamp: "2023-10-01",
            width: "200px",
            height: "300px",
            rotate: 0,
            top: "200px",
            left: "600px",
          },
          {
            image: "https://example.com/team2.jpg",
            description: "团队外出活动",
            timestamp: "2023-10-02",
            width: "250px",
            height: "200px",
            rotate: 10,
            top: "50px",
            left: "900px",
          },
          {
            image: "https://example.com/team3.jpg",
            description: "团队技术分享会",
            timestamp: "2023-10-03",
            width: "300px",
            height: "250px",
            rotate: -5,
            top: "150px",
            left: "50px",
          },
        ]
      },
      {
        title: '讲座照片',
        content:[
          {
            image: "https://example.com/lecture1.jpg",
            description: "王教授讲座：机器学习基础",
            timestamp: "2023-10-05",
            width: "250px",
            height: "150px",
            rotate: 15,
            top: "200px",
            left: "150px",
          },
          {
            image: "https://example.com/lecture2.jpg",
            description: "李老师讲座：数据分析实战",
            timestamp: "2023-10-06",
            width: "300px",
            height: "200px",
            rotate: -10,
            top: "100px",
            left: "800px",
          },
          {
            image: "https://example.com/lecture3.jpg",
            description: "张博士讲座：前沿技术探索",
            timestamp: "2023-10-07",
            width: "200px",
            height: "250px",
            rotate: 5,
            top: "400px",
            left: "370px",
          },
        ]
      },
      {
        title: '学生风采',
        content:[
          {
           image: "https://example.com/student1.jpg",
            description: "校园文化节活动",
            timestamp: "2023-10-10",
            width: "300px",
            height: "100px",
            rotate: 0,
            top: "60px",
            left: "500px",
          },
          {
            image: "https://example.com/student2.jpg",
            description: "社团活动风采",
            timestamp: "2023-10-11",
            width: "200px",
            height: "200px",
            rotate: 80,
            top: "300px",
            left: "900px",
          },
          {
            image: "https://example.com/student3.jpg",
            description: "学生科研比赛",
            timestamp: "2023-10-12",
            width: "250px",
            height: "150px",
            rotate: -5,
            top: "350px",
            left: "50px",

          },
        ]
      }
    ])      

</script>
<template>
  <div class="team-introduce" v-for="(section, sectionIndex) in List" :key="sectionIndex">
    <!-- 团队风采 -->
    <h2 class="title">{{ section.title }}</h2>
    <a href="" class="more">查看更多></a>
    <div class="card-container" 
      v-for="(item, index) in section.content"
      :key="index">
      <div
        class="card"
        :style="{
          width: item.width,
          height: item.height,
          transform: `rotate(${item.rotate}deg)`,
          top: item.top,
          left: item.left,
        }"
      >
        <el-card
          shadow="hover"
        >
          <template v-slot:header>
            <div class="card-header">
              <el-image :src="item.image" class="image"></el-image>
            </div>
          </template>
          <div class="card-body">
            <p class="description">{{ item.description }}</p>
            <p class="timestamp">{{ item.timestamp }}</p>
          </div>
        </el-card>
      </div>
    </div>


  
  </div>
</template>


<style scoped lang="less">
.team-introduce {
  text-align: center;
  
  font-size: 24px;
  color: #333;
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  width: 1400px;
  margin: 0 auto;
  margin-top: 100px;
}

.title{
  font-size: 32px;
  margin-bottom: 20px;
  font-weight: bold;
}

.card-container {
  position: absolute;
  top: 0;
  left: 0;
  
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.card {
  position: absolute;
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;

  &:hover {
    transform: scale(1.1);
  }
}

.card-header {
  text-align: center;
}

.image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.card-body {
  text-align: center;
}

.description {
  font-size: 16px;
  margin: 10px 0;
}

.timestamp {
  font-size: 14px;
  color: #666;
}
.more {
  font-size: 14px;
  position: absolute;
  right: 0;
  top: 0;
}
.more:hover {
  color: #409eff;
}
</style>