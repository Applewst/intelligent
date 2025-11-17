<template>
  
  <div class="team-introduce">
    <h2 class="title">照片墙</h2>
    <!-- 新增一个按钮用于测试添加数据 -->
    <div class="card-container">
      <div
        v-for="(item, index) in List"
        :key="index"
        class="card"
        :style="{
          width: item.style.width,
          height: item.style.height,
          transform: `rotate(${item.style.rotate}deg)`,
          top: item.style.top,
          left: item.style.left,
        }"
      >
        <el-card shadow="hover">
          <template v-slot:header>
            <div class="card-header">
              <el-image :src="item.file" class="image"></el-image>
            </div>
          </template>
          <div class="card-body">
            <p class="description">{{ item.title }}</p>
            <p class="timestamp">{{ item.time }}</p>
          </div>
        </el-card>
      </div>
    </div>
   <a href="" class="more" @click="toPhotoWall">查看更多</a>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";
import {getPhotoWallImages} from "@/api/photo";
import { useRouter } from 'vue-router';
const router = useRouter();

// 生成随机样式的函数
const generateRandomStyle = () => {
  return {
    width: `${Math.floor(Math.random() * 400) + 100}px`, // 宽度：100-500px
    height: `${Math.floor(Math.random() * 300) + 100}px`, // 高度：100-400px
    rotate: Math.floor(Math.random() * 40) - 20, // 旋转角度：-20到20度
    top: `${Math.floor(Math.random() * 1000)}px`, // 顶部距离：0-1000px
    left: `${Math.floor(Math.random() * 1500)}px`, // 左侧距离：0-1500px
  };
};
//跳转
const toPhotoWall = () => {
  router.push('/new/photos');
}


// 初始数据：每个项直接包含自己的样式
const List = ref([
]);
// 获取照片墙数据
const getPhotoWallData = async (pageNum,pageSize,title='') => {
  // console.log('获取照片墙数据文本处', pageNum,pageSize,title)
  const res = await getPhotoWallImages(pageNum,pageSize,title);
  console.log('获取照片墙数据文本处',res )
  if (res.code === 1) {
    List.value = res.data.data.map((item) => {
      return {
        ...item,
         style: generateRandomStyle(),
      };
    })
    console.log('照片墙数据', List.value)
  }
}
const title = ref('')
onMounted(() => {
  getPhotoWallData(1,'',title.value);
})
</script>

<style scoped lang="less">
/* 样式保持不变 */
.team-introduce {
  text-align: center;
  font-size: 24px;
  color: #333;
  position: relative;
  width: 100%;
  height: 100vh;
  overflow: hidden;
  width: 1400px;
  height: 1000px;
  margin: 0 auto;
  margin-top: 100px;
}

.title {
  font-size: 32px;
  margin-bottom: 20px;
  font-weight: bold;
}

.card-container {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 100%; /* 确保容器占满父元素，避免卡片溢出不可见 */
}

.card {
  position: absolute;
  background-color: #fff;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease; /* 同时监听transform和其他样式变化 */

  &:hover {
    transform: scale(1.1) rotate(0deg); /* hover时取消旋转，突出显示 */
    z-index: 10; /* 确保hover的卡片在最上层 */
  }
}

/* 其他样式保持不变 */
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
  //鼠标变为手型
  cursor: pointer;
}


.more:hover {
  color: #409eff;
}
</style>