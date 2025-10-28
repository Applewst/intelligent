<script setup>
  import {ref,computed} from 'vue';
  import { useDataStore } from '@/stores'
  const store = useDataStore()
  const getImageUrl=(user)=>{
    return new URL(`../assets/${user}.jpg`,import.meta.url).href;
  }

  //折叠
  const handleCollapse=()=>{
    store.state.isCollapse=!store.state.isCollapse
  }
</script>

<template>
  <div class="common-header">
    <div class="left">
      <el-button icon="menu" size="small" @click="handleCollapse"></el-button>
      <el-breadcrumb separator="/" class="bread">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
    </el-breadcrumb>
    </div>
    <div class="right">
      <el-dropdown>
        <span class="el-dropdown-link">
          <!-- 这里动态放置用户头像 -->
           <el-avatar :size="50" :src="getImageUrl('user')" class="user-avatar" />
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人中心</el-dropdown-item>
            <el-dropdown-item>退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<style scoped lang="less">
  .common-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color: #333;
  }
  .icons{
    width: 20px;
    height: 20px;
  }
  .right{
    .user-avatar{
      width: 40px;
      height: 40px;
    }
  }
  .left{
    display: flex;
    align-items: center;
    .el-button{
      margin-right: 20px;
    }
  }
  :deep(.bread span){
    color: #fff !important;
    cursor: pointer !important;
  }

</style>