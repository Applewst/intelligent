
<script setup>
  import { ref,computed } from 'vue'
// import { useStore } from '../stores/counter'

  // const store = useStore()

</script>

<template>
  <div class="header">
    <div class="l-content">
      <el-button size="small" class="el-button" @click="handleCollapse">
        <component class="icons" :is="menu"></component>
      </el-button>
      <el-breadcrumb separator="/" class="bread">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>

      </el-breadcrumb>
    </div>
    <div class="r-content">
      <el-dropdown>
        <span class="el-dropdown-link">
          <img src="../assets/images/user.jpg" class="user">
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
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

<style scoped>
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100%;
    height: 100%;
    background-color:#333;
  }
  .icons{
    width: 20px;
    height: 20px;

  }
  .r-content{
    .user{
      width: 40px;
      height: 40px;
      border-radius: 50%;
    }
  }
  .l-content{
    display: flex;
    align-items: center;
    .el-button{
      margin-right: 20px;
    }
  }
  :deep(.bread span){
    color: #fff !important;
    cursor:   pointer !important;

  }
</style>