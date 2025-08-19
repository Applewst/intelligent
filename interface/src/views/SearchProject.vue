<template>
  <div class="search-project">
    <!-- 顶部标题 -->
    <div class="title-container">
      <h1 class="title">研究成果</h1>
    </div>

    <!-- 研究方向 -->
    <div class="research-direction">
      <h2 class="research-direction-title">研究方向</h2>
      <div class="tabs-container">
        <!-- 三个圆形标签 -->
        <div
          v-for="(tab, index) in tabs"
          :key="index"
          :class="['circle-tab', { active: activeTab === index }]"
          @click="selectTab(index)"
          @mouseover="hoverTab(index)"
          @mouseleave="unhoverTab(index)"
        >
          {{ tab.title }}
        </div>
      </div>
    </div>

    <!-- 研究方向对应内容 -->
    <div class="content-container">
      <!-- 第一个tab的内容：图片和文字 -->
      <div
        v-for="(item, index) in selectedItems"
        :key="index"
        class="item"
        @mouseover="hoverImage(index)"
        @mouseleave="unhoverImage(index)"
        @click="highlightImage(index)"
      >
        <div class="image-container">
          <img :src="item.image" :alt="item.title" class="image" />
        </div>
        <div class="title">{{ item.title }}</div>
      </div>
    </div>
  </div>

  <div class="project-description">文字说明</div>
</template>

<script setup>
import { ref } from 'vue';

// 定义状态
const activeTab = ref(0); // 当前激活的标签
const tabs = ref([
  { title: '服务推荐' },
  { title: '自然语言，大模型' },
  { title: '图像处理，计算机视觉' },
]);

const activeImageIndex = ref(-1); // 当前高亮的图片索引

// 初始化显示的内容
const selectedItems = ref([
  { title: '介观活体显微', image: '../assets/images/1.jpg' },
  { title: '计算成像', image: '../assets/images/2.jpg' },
  { title: '光电计算', image: '../assets/images/3.jpg' },
  { title: '智能成像', image:'../assets/images/4.jpg' },
  { title: 'AI for Science', image: '../assets/images/5.jpg' },
  { title: '脑智能', image: '../assets/images/6.jpg' },
]);

// 选择标签
const selectTab = (index) => {
  activeTab.value = index;
  console.log(`切换到标签：${tabs.value[index].title}`);
};

// 鼠标移入标签添加阴影
const hoverTab = (index) => {
  tabs.value[index].hover = true;
};

// 鼠标移出标签移除阴影
const unhoverTab = (index) => {
  tabs.value[index].hover = false;
};

// 鼠标移入图片添加阴影
const hoverImage = (index) => {
  selectedItems.value[index].hover = true;
};

// 鼠标移出图片移除阴影
const unhoverImage = (index) => {
  selectedItems.value[index].hover = false;
};

// 点击图片高亮显示
const highlightImage = (index) => {
  activeImageIndex.value = index;
};
</script>

<style scoped lang="less">
/* 全局样式 */
.search-project {
  padding: 20px;
  box-sizing: border-box;
  max-width: 1000px; /* 调整为 1000px，减少两边空白 */
  margin: 0 auto; /* 水平居中 */
}

/* 标题样式 */
.title-container {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  text-align: center; /* 标题水平居中 */
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  border-bottom: 2px solid #9ac142; /* 绿色下划线 */
  padding-bottom: 5px;
}

/* 研究方向样式 */
.research-direction {
  margin-bottom: 20px;
  text-align: center; /* 研究方向标题水平居中 */
}

.research-direction-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

.tabs-container {
  display: flex;
  justify-content: center; /* 圆形标签容器水平居中 */
}

.circle-tab {
  width: 100px;
  height: 100px;
  background-color: #fff;
  border: 2px solid #ccc;
  border-radius: 50%; /* 圆形样式 */
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: box-shadow 0.3s;
}

.circle-tab.active {
  background-color: #1890ff; /* 点击后高亮背景色 */
  color: #fff;
}

.circle-tab:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

/* 内容容器样式 */
.content-container {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin: 0 auto; /* 居中对齐 */
  max-width: 700px; /* 调整为 700px，使方形模块更紧凑 */
}

.item {
  position: relative;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 默认阴影 */
  overflow: hidden;
  transition: box-shadow 0.3s;
  cursor: pointer;
}

.item:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3); /* 鼠标移入增加阴影 */
}

/* 图片样式 */
.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
  height: 150px; /* 确保图片容器的高度固定 */
}

.image {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 保证图片自适应容器大小 */
}

.title {
  text-align: center;
  font-size: 16px;
  margin-top: 10px;
}

/* 高亮样式 */
.item.active {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* 高亮阴影 */
}

.project-description {
  margin-top: 100px;
}
</style>