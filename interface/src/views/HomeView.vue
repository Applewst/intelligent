<script setup>


import CommonAside from '../components/CommonAside.vue'
import CommonHeader from '../components/CommonHeader.vue'
  // import ref from 'vue';

</script>

<template>
  <div class="common-layout">
    <el-container class="common-container">
      <el-aside width="180px" class="total-aside">
        <common-aside></common-aside>
      </el-aside>
      <el-container>
        <el-header class="total-header">
          <common-header></common-header>
        </el-header>
        <el-main class="total-main">Main</el-main>
      </el-container>
    </el-container>
  </div>
</template>

<style scoped lang="less">
.common-layout,.common-container{
  height: 100%;
}
.total-header{
  background-color: #333;
}
.total-aside{

 
}
.total-main{

  background-color: yellow;
}

</style>
