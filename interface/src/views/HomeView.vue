<script setup>
import CommonHeader from '../components/CommonHeader.vue'
// import ref from 'vue';
import CommonNew from '@/components/CommonNew.vue'
</script>

<template>
  <div class="common-layout">
    <el-container class="common-container">
      <el-header class="common-header">
        <common-header></common-header>
      </el-header>
      <el-main class="common-main">
        <common-new />
      </el-main>
      <el-footer class="common-footer">Footer</el-footer>
    </el-container>
  </div>
</template>

<style scoped lang="less">
.common-container {
  height: 100%;
}
</style>
