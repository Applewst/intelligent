<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';


    const modules = ref([
      {
        title: '服务推荐',
        images: [
          { src: 'path/to/image1.png' },
          { src: 'path/to/image2.png' },
          { src: 'path/to/image3.png' },
          { src: 'path/to/image4.png' },
          { src: 'path/to/image5.png' },
          { src: 'path/to/image6.png' },
        ],
        details: {
          '1': { visible: false, content: '服务推荐详情 1' },
          '2': { visible: false, content: '服务推荐详情 2' },
          '3': { visible: false, content: '服务推荐详情 3' },
          '4': { visible: false, content: '服务推荐详情 4' },
          '5': { visible: false, content: '服务推荐详情 5' },
          '6': { visible: false, content: '服务推荐详情 6' },
        },
      },
      {
        title: '自然语言处理',
        images: [
          { src: 'path/to/image7.png' },
          { src: 'path/to/image8.png' },
          { src: 'path/to/image9.png' },
          { src: 'path/to/image10.png' },
          { src: 'path/to/image11.png' },
          { src: 'path/to/image12.png' },
        ],
        details: {
          '1': { visible: false, content: '自然语言处理详情 1' },
          '2': { visible: false, content: '自然语言处理详情 2' },
          '3': { visible: false, content: '自然语言处理详情 3' },
          '4': { visible: false, content: '自然语言处理详情 4' },
          '5': { visible: false, content: '自然语言处理详情 5' },
          '6': { visible: false, content: '自然语言处理详情 6' },
        },
      },
      {
        title: '图像处理',
        images: [
          { src: 'path/to/image13.png' },
          { src: 'path/to/image14.png' },
          { src: 'path/to/image15.png' },
          { src: 'path/to/image16.png' },
          { src: 'path/to/image17.png' },
          { src: 'path/to/image18.png' },
        ],
        details: {
          '1': { visible: false, content: '图像处理详情 1' },
          '2': { visible: false, content: '图像处理详情 2' },
          '3': { visible: false, content: '图像处理详情 3' },
          '4': { visible: false, content: '图像处理详情 4' },
          '5': { visible: false, content: '图像处理详情 5' },
          '6': { visible: false, content: '图像处理详情 6' },
        },
      },
    ]);

    const router = useRouter();

    const showDetails = (moduleIndex, imageIndex) => {
      modules.value[moduleIndex].details[`${imageIndex + 1}`].visible = true;
    };

    const hideDetails = (moduleIndex, imageIndex) => {
      modules.value[moduleIndex].details[`${imageIndex + 1}`].visible = false;
    };

    const goToDetails = (index) => {
      router.push({ path: `/search/project/${index}` });
    };


</script>

<template>
  <div class="search-project">
    <div
      class="module"
      v-for="(module, moduleIndex) in modules"
      :key="moduleIndex"
    >
      <h2 class="module-title">{{ module.title }}</h2>
      <div class="image-container">
        <div
          class="image-item"
          v-for="(image, imageIndex) in module.images"
          :key="imageIndex"
          @mouseover="showDetails(moduleIndex, imageIndex)"
          @mouseleave="hideDetails(moduleIndex, imageIndex)"
        >
          <img :src="image.src" alt="项目图片" />
          <p>我是文字</p>
          <p v-if="module.details[`${imageIndex + 1}`].visible">
            {{ module.details[`${imageIndex + 1}`].content }}
          </p>
        </div>
      </div>
      <el-button
        class="details-button"
        style="color: brown"
        @click="goToDetails(moduleIndex + 1)"
      >
        点击详情
    </el-button>
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
  /*  */
  margin: 0 auto;    /* 居中 */
  width: 1200px;     /* 控制模块宽度 */
  border: 1px solid #ccc;
  padding: 10px;
  margin-bottom: 50px;
  border-radius: 5px;
  position: relative;
}

.module h2 {
  text-align: center;
  margin-bottom: 20px;
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
  width: calc(33.33% - 10px);
  height: 150px;
  border: 1px solid #ccc;
  border-radius: 5px;
  overflow: hidden;
  transition: box-shadow 0.3s;
  cursor: pointer;
}

.image-item:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.image-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-item p {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  opacity: 0;
  transition: opacity 0.3s;
}

.image-item p[visible='true'] {
  opacity: 1;
}

.details-button {
  position: absolute;
  top: 10px;
  right: 10px;

  border: none;
  padding: 5px 10px;
  cursor: pointer;
}

.details-button:hover {
  background-color: rgba(255, 255, 255, 0.9);
}
</style>
