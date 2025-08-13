<script setup>
import CommonHeader from '../components/CommonHeader.vue'
import CommonNew from '@/components/CommonNew.vue'
import CommonProject from '@/components/CommonProject.vue'
import CommonTeacher from '@/components/CommonTeacher.vue'
import CommonPhoto from '@/components/CommonPhoto.vue'
</script>

<template>
  <div class="common-layout">
    <el-container class="common-container">
      <el-header class="common-header">
        <common-header></common-header>
      </el-header>
      <el-main class="common-main">
      <router-view></router-view>
        <div v-if="$route.name === 'home'">
          <common-new />
          <common-project />
          <!-- 引用组件影响样式，外加标题 -->
          <div class="simple-title-container">
            <h2 class="main-title">教师队伍</h2>
          </div>
          <common-teacher />
        </div>
      </el-main>
    </el-container>
    
  </div>
</template>

<style scoped lang="less">
.common-container {
  height: 100%;
  display: flex; // 确保容器是垂直排列的
  flex-direction: column; // 垂直排列
}
// .common-header {
//   height: 80px; // 确保 header 有固定高度
// }

.simple-title-container {
  text-align: center;
  margin-bottom: 30px; /* 与下方轮播保持间距 */
  padding: 10px 0;
}
// .common-main {
//   flex: 1; // 允许 main 占用剩余空间
// }

/* 主标题样式 */
.main-title {
  font-size: 28px;
  font-weight: 600;
  color: #303133; /* Element 主文本色 */
  margin: 0 0 15px; /* 底部留出装饰线空间 */
  position: relative;
  display: inline-block;
  letter-spacing: 0.5px;
}
</style>
