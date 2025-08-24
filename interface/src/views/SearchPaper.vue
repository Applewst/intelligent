<script setup>
import { ref } from 'vue';

// 模拟中间部分的数据
const researchItems = ref([
  {
    name: 'Researcher 1',
    description: 'This is a description of Researcher 1 and their publications.',
    image: 'https://via.placeholder.com/150',
  },
  {
    name: 'Researcher 2',
    description: 'This is a description of Researcher 2 and their publications.',
    image: 'https://via.placeholder.com/150',
  },
  {
    name: 'Researcher 3',
    description: 'This is a description of Researcher 3 and their publications.',
    image: 'https://via.placeholder.com/150',
  },
]);

// 模拟下方论文列表的数据
const papers = ref([
  {
    id: 1,
    title: 'Physica-driven self-supervised learning for fast high-resolution robust 3D reconstruction',
    authors: '周飞, 主教练: 王思聪, 第一作: 团体的, Nature Methods (2023)',
    image: 'https://via.placeholder.com/150',
  },
  {
    id: 2,
    title: 'Quantum Computing Techniques in Neural Networks',
    authors: '团队, 主教练: 王思聪, 第一作: 团体的, Nature Methods (2023)',
    image: 'https://via.placeholder.com/150',
  },
  {
    id: 3,
    title: 'Machine Learning Algorithms for AI Applications',
    authors: '团队, 主教练: 王思聪, 第一作: 团体的, Nature Methods (2023)',
    image: 'https://via.placeholder.com/150',
  },
]);

// 点击论文图片跳转到论文详情页
const viewPaperDetail = (paperId) => {
  console.log(`Viewing paper details for ID: ${paperId}`);
  // 这里可以实现路由跳转逻辑
};

// 点击查看更多按钮
const viewMorePapers = () => {
  console.log('Viewing more papers');
  // 这里可以实现路由跳转逻辑
};
</script>

<template>
  <div class="team-introduce">


    <!-- 中间部分：三个长方形块 -->
    <div class="middle-section">
      <div v-for="(item, index) in researchItems" :key="index" class="research-item">
        <div class="left-content">
          <h3>{{ item.name }}</h3>
          <p>{{ item.description }}</p>
        </div>
        <div class="right-content">
          <img :src="item.image" alt="Headshot" class="headshot">
        </div>
      </div>
    </div>

    <!-- 下方的大盒子 -->
       <!-- 下方的大盒子 -->
    <div class="big-box">
      <el-row v-for="(paper, index) in papers" :key="index" :gutter="20" class="paper-item">
        <el-col :span="24">
          <div class="paper-content">
            <div class="left-content">
              <p>{{ paper.title }}</p>
              <p class="paper-info">{{ paper.authors }}</p>
            </div>
            <div class="right-content">
              <img :src="paper.image" alt="Paper Image" class="paper-image" @click="viewPaperDetail(paper.id)" />
            </div>
          </div>
        </el-col>
      </el-row>
     
    </div>
  </div>
</template>



<style scoped lang="less">
.team-introduce {
  text-align: center;
  margin-top: 100px;
}


.middle-section {
  margin-bottom: 60px;
  width: 1000px;
  // height: 700px;
  margin: 0 auto;
  .research-item {
    display: flex;
    height: 200px;
    align-items: center;
    background-color: #3298db;
    padding: 20px;
    margin-bottom: 20px;
    border-radius: 8px;

    .left-content {
      flex: 1;
      h3 {
        margin-top: 0;
        margin-bottom: 10px;
      }
      p {
        margin: 0;
        font-size: 14px;
        color: #333;
      }
    }

    .right-content {
      width: 150px;
      background-color: #ff9900;
      height: 150px;
      .headshot {
        // width: 100%;
        // height: 150px;
        object-fit: cover;
        // border-radius: 50%;
        background-color: #ff9900;
      }
    }
  }
}


.big-box {
  .paper-item {
    .paper-content {
      display: flex;
      margin: 0 auto;
      margin-top: 50px;
      height: 100px;
      align-items: center;
      width: 1000px;
      padding: 20px;
      border-radius: 8px;
      background-color: #fff;
    }

    .left-content {
      flex: 1;
      p {
        margin: 0;
        font-size: 16px;
        color: #333;
      }
      .paper-info {
        font-size: 12px;
        color: #666;
      }
    }

    .right-content {
      .paper-image {
        width: 100px;
        height: 100px;
        object-fit: cover;
        cursor: pointer;
      }
    }
  }

  .more-button {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
  }
}
</style>