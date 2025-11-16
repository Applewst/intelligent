<script setup>
import { onMounted, ref } from 'vue';
// import { useRouter } from 'vue-router';
import { GetSearchProjects } from '../api/search';
const props = defineProps({
  projectId: Number
})
console.log(props.projectId)

// const modules = ref([
//     {
//       title: '服务推荐',
//       images: [
//         { src: '../assets/images/1.jpg', time: '2023-01-01', name: '项目1' },
//         { src: 'path/to/image2.png', time: '2023-02-01', name: '项目2' },
//         { src: 'path/to/image3.png', time: '2023-03-01', name: '项目3' },
//         { src: 'path/to/image4.png', time: '2023-04-01', name: '项目4' },
//         { src: 'path/to/image5.png', time: '2023-05-01', name: '项目5' },
//         { src: 'path/to/image6.png', time: '2023-06-01', name: '项目6' },
//       ],
//       details: {
//         '1': { visible: true, content: '服务推荐详情 1' },
//         '2': { visible: false, content: '服务推荐详情 2' },
//         '3': { visible: false, content: '服务推荐详情 3' },
//         '4': { visible: false, content: '服务推荐详情 4' },
//         '5': { visible: false, content: '服务推荐详情 5' },
//         '6': { visible: false, content: '服务推荐详情 6' },
//       },
//     },
//     {
//       title: '自然语言处理',
//       images: [
//         { src: 'path/to/image7.png', time: '2023-07-01', name: '项目7' },
//         { src: 'path/to/image8.png', time: '2023-08-01', name: '项目8' },
//         { src: 'path/to/image9.png', time: '2023-09-01', name: '项目9' },
//         { src: 'path/to/image10.png', time: '2023-10-01', name: '项目10' },
//         { src: 'path/to/image11.png', time: '2023-11-01', name: '项目11' },
//         { src: 'path/to/image12.png', time: '2023-12-01', name: '项目12' },
//       ],
//       details: {
//         '1': { visible: true, content: '自然语言处理详情 1' },
//         '2': { visible: false, content: '自然语言处理详情 2' },
//         '3': { visible: false, content: '自然语言处理详情 3' },
//         '4': { visible: false, content: '自然语言处理详情 4' },
//         '5': { visible: false, content: '自然语言处理详情 5' },
//         '6': { visible: false, content: '自然语言处理详情 6' },
//       },
//     },
//     {
//       title: '图像处理',
//       images: [
//         { src: 'path/to/image13.png', time: '2023-01-15', name: '项目13' },
//         { src: 'path/to/image14.png', time: '2023-02-15', name: '项目14' },
//         { src: 'path/to/image15.png', time: '2023-03-15', name: '项目15' },
//         { src: 'path/to/image16.png', time: '2023-04-15', name: '项目16' },
//         { src: 'path/to/image17.png', time: '2023-05-15', name: '项目17' },
//         { src: 'path/to/image18.png', time: '2023-06-15', name: '项目18' },
//       ],
//       details: {
//         '1': { visible: true, content: '图像处理详情 1' },
//         '2': { visible: false, content: '图像处理详情 2' },
//         '3': { visible: false, content: '图像处理详情 3' },
//         '4': { visible: false, content: '图像处理详情 4' },
//         '5': { visible: false, content: '图像处理详情 5' },
//         '6': { visible: false, content: '图像处理详情 6' },
//       },
//     },
//   ]);

const tables = ref([])

const GetAllSearchProjects = async (id) => {
  const response = await GetSearchProjects(id);
  console.log('获取研究项目response',response)
  tables.value = response.data.data;
}

onMounted(() => {
  GetAllSearchProjects(props.projectId);
})

function showDetails(index, imageIndex) {
  tables.value.images[imageIndex].detailsVisible = true;
}

function hideDetails(index, imageIndex) {
  tables.value.images[imageIndex].detailsVisible = false;
}
</script>

<template>
  <div class="search-project">
    <div
      class="module"
    >
      <h2 class="module-title">{{ tables.title }}</h2>
      <div class="image-container">
        <div
          class="image-item"
          v-for="(image, imageIndex) in tables.images"
          :key="imageIndex"
          @mouseover="showDetails(index, imageIndex)"
          @mouseleave="hideDetails(index, imageIndex)"
        >
         <el-card>
          <img src="../assets/images/1.jpg" alt="">
          
          <template #footer>
            <div class="project-info">
              
              <div class="project-name">{{ image.name }}</div>
              <div class="project-time">{{ image.time }}</div>
              <!-- <div class="project-time">{{ image.time }}</div> -->
            </div>
          </template>
         </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.search-project {
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.module {
  margin: 0 auto; /* 居中 */
  width: 1200px; /* 控制模块宽度 */
  padding: 10px;
  margin-bottom: 50px;
  border-radius: 5px;
  position: relative;
}

.module-title {
  text-align: center;
  margin-bottom: 60px;
  font-weight: bold;
  font-size: 30px;
}

.image-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-item {
  position: relative;
  margin: 0 auto;
  margin-bottom: 20px;
  width: calc(30.33% - 20px);
  height: 320px; /* 固定卡片高度，确保内容不溢出 */
  border: 1px solid #ccc;
  border-radius: 5px;
  overflow: hidden; /* 去除多余部分 */
  cursor: pointer;
}

.el-card {
  height: 100%; /* 确保卡片高度填满 .image-item */
  display: flex;
  flex-direction: column;
}

img {
  width: 100%;
  height: auto; /* 图片自适应宽度 */
  max-height: 100%; /* 限制图片高度，确保留出文字空间 */
  object-fit: cover; /* 图片适配时裁剪 */
}

.project-info {
  flex: 1; /* 填充剩余空间 */
  display: flex;
  flex-direction: column;
  align-items: center; /* 项目名称居中 */
  justify-content: flex-end; /* 时间在底部 */
  padding: 10px; /* 内边距 */
}

.project-name {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px; /* 与时间保持间距 */
  color: #333; /* 确保项目名称清晰 */
}

.project-time {
  font-size: 14px;
  color: #666; /* 轻度的颜色以显示时间 */
}

.image-item:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

</style>
