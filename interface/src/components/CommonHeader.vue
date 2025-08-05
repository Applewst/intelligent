<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { useCounterStore } from '../stores/counter'
const store = useCounterStore()
const activeIndex = ref('home')
const userType = ref('') // internal 或 admin
const userName = ref('')
store.setUser('三', 'internal') // 设置用户信息

const isLogin = ref(true) // 是否已登录

function handleAvatarClick() {
  if (!isLogin.value) {
    // 模拟登录
    isLogin.value = true
    userName.value = store.userName
    userType.value = store.userType
    ElMessage.success('登录成功')
  }
}

function handleMenuSelect(key) {
  if (key === 'logout') {
    isLogin.value = false
    userName.value = ''
    ElMessage.success('已退出登录')
  }
}

// import { useStore } from '../stores/counter'

// const store = useStore()
</script>

<template>
  <div class="header-top">
    <div class="logo"><img src="../assets/images/OIP-C.jpg" alt="" style="width: 180px" /></div>
    <div class="search-center">
      <el-input placeholder="搜索" size="small" class="search-input" :prefix-icon="Search" />
    </div>
    <div class="header-actions">
      <el-button type="primary" size="small">English</el-button>
      <el-dropdown trigger="click" @command="handleMenuSelect">
        <span class="el-dropdown-link" @click="handleAvatarClick">
          <el-avatar
            v-if="isLogin"
            :style="userType === 'admin' ? 'border:2px solid #409EFF' : 'border:2px solid gold'"
            size="large"
            >{{ userName ? userName[0] : '' }}</el-avatar
          >
          <el-avatar v-else size="large" icon="el-icon-user"></el-avatar>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item v-if="!isLogin" command="login">登录</el-dropdown-item>
            <el-dropdown-item v-else command="logout">退出登录</el-dropdown-item>
            <el-dropdown-item v-if="isLogin" disabled>
              {{ userType === 'admin' ? '管理员' : '内部人员' }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>

  <div class="header-menu-container">
    <el-menu
      class="header-menu"
      mode="horizontal"
      :default-active="activeIndex"
      @select="(key) => (activeIndex = key)"
      background-color="#409EFF"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="home">首页</el-menu-item>
      <el-menu-item index="team">团队介绍</el-menu-item>
      <el-sub-menu index="result">
        <template #title>研究成果</template>
        <el-menu-item index="project">研究项目</el-menu-item>
        <el-menu-item index="paper">发表论文</el-menu-item>
        <el-menu-item index="award">获奖情况</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="news">
        <template #title>团队动态</template>
        <el-menu-item index="activity">科研动态</el-menu-item>
        <el-menu-item index="event">团队活动</el-menu-item>
        <el-menu-item index="student">学生发展</el-menu-item>
        <el-menu-item index="photo">照片墙</el-menu-item>
      </el-sub-menu>
      <el-sub-menu index="member">
        <template #title>成员信息</template>
        <el-menu-item index="teacher">师资队伍</el-menu-item>
        <el-menu-item index="student-list">在读学生</el-menu-item>
        <el-menu-item index="alumni">桃李天下</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="contact">联系我们</el-menu-item>
      <el-sub-menu index="resource">
        <template #title>资源共享</template>
        <el-menu-item index="data">实验数据</el-menu-item>
        <el-menu-item index="doc">内部文档</el-menu-item>
        <el-menu-item index="code">代码库</el-menu-item>
      </el-sub-menu>
    </el-menu>
  </div>
</template>

<style scoped lang="less">
.header-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #6cb6f5;
  padding: 0 32px;
  height: 80px;
  position: relative;
}

.logo {
  font-size: 36px;
  color: #fff;
  font-weight: bold;
  width: 180px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.search-center {
  flex: 1;
  display: flex;
  justify-content: center;
}

.search-input {
  width: 300px;
  height: 40px;
  border-radius: 20px;
  box-shadow: none;
  transition: box-shadow 0.2s;
  background: #fff;
}

.search-input :deep(.el-input__wrapper) {
  border-radius: 20px;
  background: #fff;
  box-shadow: none;
  transition: box-shadow 0.2s;
}

.search-input :deep(.el-input__wrapper):hover,
.search-input :deep(.el-input__wrapper):focus-within {
  box-shadow: 0 2px 8px #b3d8ff;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 16px;
}

.el-dropdown-link {
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-around;
}

.header-menu {
  border-bottom: none;
  background: #6cb6f5;
  // max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  // border-radius: 8px;
  display: flex;
  justify-content: center;
  min-width: 0;
  // box-shadow: 0 2px 8px #e0e7ef;
  font-size: 20px;
}

.header-menu :deep(.el-menu-item),
.header-menu :deep(.el-sub-menu__title) {
  border-radius: 6px;
  margin: 0 4px;
  transition: background 0.2s;
}

.header-menu :deep(.el-menu-item:hover),
.header-menu :deep(.el-sub-menu__title:hover) {
  background: #66b1ff;
}
</style>


