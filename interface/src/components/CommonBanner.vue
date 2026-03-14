<template>
  <div class="banner-container">
    <el-carousel :interval="5000" height="400px" arrow="always">
      <el-carousel-item v-for="(item, index) in bannerList" :key="index">
        <div class="banner-item">
          <img :src="item.image" :alt="item.title" class="banner-image">
          <div class="banner-content">
            <h3 class="banner-title">{{ item.title }}</h3>
            <p class="banner-subtitle">{{ item.subtitle }}</p>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup>
const bannerList = [
  {
    image: "/src/assets/images/1.jpg",
    title: "创新研究",
    subtitle: "致力于培养具有国际视野的计算机科学研究人才"
  },
  {
    image: "/src/assets/images/2.jpg",
    title: "团队活动",
    subtitle: "学术交流与团队建设活动"
  },
  {
    image: "/src/assets/images/3.jpg",
    title: "实验室环境",
    subtitle: "先进的科研环境与设备"
  },
  {
    image: "/src/assets/images/4.jpg",
    title: "学术成果",
    subtitle: "学术会议与获奖成果展示"
  }
];
</script>

<style scoped>
.banner-container {
  width: 100%;
  margin-bottom: 40px;
  padding: 0;
  overflow: hidden;
}

.banner-item {
  position: relative;
  width: 100%;
  height: 400px;
  overflow: hidden;
}

.banner-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

/* 确保轮播容器占满宽度 */
:deep(.el-carousel) {
  width: 100%;
}

:deep(.el-carousel__container) {
  width: 100%;
}

:deep(.el-carousel__item) {
  width: 100% !important;
}

.banner-item:hover .banner-image {
  transform: scale(1.05);
}

.banner-content {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0,0,0,0.7), transparent);
  color: white;
  padding: 60px 40px;
  z-index: 10;
  text-align: center;
}

.banner-title {
  font-size: 28px;
  font-weight: 700;
  margin: 0 0 15px;
  text-shadow: 0 2px 4px rgba(0,0,0,0.5);
}

.banner-subtitle {
  font-size: 18px;
  margin: 0;
  opacity: 0.95;
  text-shadow: 0 1px 2px rgba(0,0,0,0.5);
}

/* 自定义轮播指示器样式 */
:deep(.el-carousel__indicators) {
  bottom: 30px;
}

:deep(.el-carousel__button) {
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.6);
  margin: 0 6px;
  transition: all 0.3s ease;
}

:deep(.el-carousel__button--active) {
  background: #ffffff;
  width: 24px;
  border-radius: 5px;
}

/* 自定义箭头样式 */
:deep(.el-carousel__arrow) {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.2);
  transition: all 0.3s ease;
  top: 50%;
  transform: translateY(-50%);
}

:deep(.el-carousel__arrow:hover) {
  background: rgba(255, 255, 255, 0.3);
}

:deep(.el-carousel__arrow i) {
  font-size: 24px;
  color: #ffffff;
}

:deep(.el-carousel__arrow--left) {
  left: 20px;
}

:deep(.el-carousel__arrow--right) {
  right: 20px;
}

@media (max-width: 768px) {
  .banner-item {
    height: 300px;
  }
  
  .banner-content {
    padding: 40px 20px;
  }
  
  .banner-title {
    font-size: 24px;
  }
  
  .banner-subtitle {
    font-size: 16px;
  }
  
  :deep(.el-carousel__arrow) {
    width: 40px;
    height: 40px;
  }
  
  :deep(.el-carousel__arrow i) {
    font-size: 20px;
  }
}

@media (max-width: 480px) {
  .banner-item {
    height: 200px;
  }
  
  .banner-content {
    padding: 30px 15px;
  }
  
  .banner-title {
    font-size: 20px;
  }
  
  .banner-subtitle {
    font-size: 14px;
  }
  
  :deep(.el-carousel__arrow) {
    width: 35px;
    height: 35px;
  }
  
  :deep(.el-carousel__arrow i) {
    font-size: 18px;
  }
}
</style>