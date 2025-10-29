<script setup>
import { ref, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { useCounterStore } from '../stores/counter'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

const activeMenuIndex = computed(() => {
  const fullPath = route.path

  const parentMatch = ['/search/project', '/member/teacher'].find((p) => fullPath.startsWith(p))

  return parentMatch || fullPath // 没找到就按原样匹配
})

const store = useCounterStore()
const activeIndex = ref('home')
const userType = ref('') // internal 或 admin
const userName = ref('')
// store.setUser('三', 'internal') // 设置用户信息

const isLogin = ref(true) // 是否已登录
onMounted(() => {
  // 初始化用户信息
  isLogin.value = store.isLogin
  userName.value = store.userName
  userType.value = store.userType
})
function handleAvatarClick() {
  if (!isLogin.value) {
    // 模拟登录
    router.push('/login')
  }
}

function handleMenuSelect(key) {
  if (key === 'logout') {
    isLogin.value = false
    userName.value = ''
    
    //本地储存的token删除
    localStorage.removeItem('token')
    localStorage.removeItem('username')
    ElMessage.success('已退出登录')
  }
}

// import { useStore } from '../stores/counter'

// const store = useStore()
</script>

<template>
  <div class="allheader">
    <div class="header-top">
        <div class="logo"><img src="../assets/images/hut.png" alt="" style="width: 100px" /></div>
        <div class="search-center">
          <el-input placeholder="搜索" size="small" class="search-input" :prefix-icon="Search" />
        </div>
        <div class="header-actions">
          <el-button type="primary" size="small">English</el-button>
          <el-dropdown trigger="click" @command="handleMenuSelect">
            <span class="el-dropdown-link">
              <el-avatar
                v-if="isLogin"
                :style="userType === 'admin' ? 'border:2px solid #54475e' : 'border:2px solid gold'"
                size="large"
                >{{ userName ? userName : '' }}</el-avatar
              >
              <el-avatar v-else size="large" icon="el-icon-user"></el-avatar>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item v-if="!isLogin" command="login" @click="handleAvatarClick"
                  >登录</el-dropdown-item
                >
                <el-dropdown-item v-else command="logout" >退出登录</el-dropdown-item>
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
          :default-active="activeMenuIndex"
          @select="(key) => (activeIndex = key)"
          background-color="#409EFF"
          text-color="#fff"
          active-text-color="#ffd04b"
          router="true"
        >
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="/team/introduction">团队介绍</el-menu-item>
          <el-sub-menu index="search">
            <template #title>研究成果</template>
            <el-menu-item index="/search/project">研究项目</el-menu-item>
            <el-menu-item index="/search/paper">发表论文</el-menu-item>
            <el-menu-item index="/search/award">获奖情况</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="news">
            <template #title>团队动态</template>
            <el-menu-item index="/news/activity">科研动态</el-menu-item>
            <el-menu-item index="/news/event">团队活动</el-menu-item>
            <el-menu-item index="/news/student">学生发展</el-menu-item>
            <el-menu-item index="/news/photo">照片墙</el-menu-item>
          </el-sub-menu>
          <el-sub-menu index="member">
            <template #title>成员信息</template>
            <el-menu-item index="/member/teacher">师资队伍</el-menu-item>
            <el-menu-item index="/member/studentlist">在读学生</el-menu-item>
            <el-menu-item index="/member/alumni">桃李天下</el-menu-item>
          </el-sub-menu>
          <el-menu-item index="/link/contact">联系我们</el-menu-item>
          <el-menu-item index="/resource">资源共享</el-menu-item>
        </el-menu>
      </div>
  </div>
</template>

<style scoped lang="less">
.allheader {
  width: 100%;
  box-shadow: 0 2px 8px #e0e7ef;
  background: #fff;
}

.header-top {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #dddee0;
  padding: 0 32px;
  height: 100px;
  position: fixed; /* 固定定位 */
  top: 0; /* 固定在顶部 */
  width: 100%; /* 宽度占满整个屏幕 */
  z-index: 1000; /* 确保导航栏在其他内容之上 */
}

.logo {
  font-size: 36px;
  color: #fff;
  font-weight: bold;
  img {
    object-fit: fill;
  }
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

.header-menu-container {
  margin-top: 100px; /* 为避免内容被固定导航栏遮挡，添加一个与导航栏高度相等的上边距 */
}

.header-menu {
  border-bottom: none;
  background: #dddee0;
  // max-width: 1200px;
  width: 100%;
  margin: 0 auto;
  // border-radius: 8px;
  display: flex;
  justify-content: center;
  min-width: 0;
  // box-shadow: 0 2px 8px #e0e7ef;
  font-size: 20px;
   position: fixed; /* 固定定位 */
  top: 100px;
   z-index: 1000;
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
