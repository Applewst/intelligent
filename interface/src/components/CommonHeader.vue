<template>
  <div class="allheader">
    <div class="header-top">
      <div class="logo">
        <img
          src="../assets/images/hut.png"
          alt="Logo"
          style="width: 80px"
          @click="router.push('/')"
        />
      </div>

      <div class="header-actions">
        <!-- 后台管理按钮 - 仅管理员可见 -->
        <el-button
          v-if="store.isLogin && store.userType === 'admin'"
          type="primary"
          size="small"
          icon="el-icon-setting"
          @click="handleGoToBackend"
          class="backend-btn"
        >
          后台管理
          <ArrowRight style="margin-left: 4px; font-size: 14px" />
        </el-button>

        <el-dropdown trigger="click" @command="handleMenuSelect" :disabled="isLoading">
          <span
            class="el-dropdown-link"
            @click="handleUserClick"
            style="display: flex; align-items: center"
          >
            <span v-if="store.isLogin" class="user-role">
              {{ store.userType === 'admin' ? '管理员' : '普通用户' }}
            </span>
            <span v-else class="guest-text">登录</span>
            <i class="el-icon-arrow-down" style="margin-left: 5px; font-size: 12px"></i>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item v-if="!store.isLogin" command="login" icon="el-icon-login">
                登录
              </el-dropdown-item>
              <el-dropdown-item v-if="store.isLogin" disabled class="user-info-item">
                <span class="user-info-name">{{ store.userName }}</span>
              </el-dropdown-item>
              <el-dropdown-item v-if="store.isLogin" command="logout" icon="el-icon-logout" divided>
                退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>

        <!-- 移动端菜单按钮 -->
        <div class="mobile-menu-btn" @click="isMobileMenuOpen = !isMobileMenuOpen">
          <el-icon v-if="!isMobileMenuOpen"><Menu /></el-icon>
          <el-icon v-else><Close /></el-icon>
        </div>
      </div>
    </div>

    <!-- 桌面端导航菜单 -->
    <div class="header-menu-container desktop-menu">
      <el-menu
        class="header-menu"
        mode="horizontal"
        :default-active="activeKey"
        @select="handleMenuSelect"
        background-color="#dddee0"
        text-color="#fff"
        active-text-color="#ffd04b"
        router
      >
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/team/introduction">团队介绍</el-menu-item>
        <el-sub-menu index="/search">
          <template #title>研究成果</template>
          <el-menu-item index="/search/project">研究项目</el-menu-item>
          <el-menu-item index="/search/paper">发表论文</el-menu-item>
          <el-menu-item index="/search/award">获奖情况</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/news">
          <template #title>团队动态</template>
          <el-menu-item index="/news/activity">科研动态</el-menu-item>
          <el-menu-item index="/news/event">团队活动</el-menu-item>
          <el-menu-item index="/news/student">学生发展</el-menu-item>
          <el-menu-item index="/news/photo">照片墙</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="/member">
          <template #title>成员信息</template>
          <el-menu-item index="/member/teacher">师资队伍</el-menu-item>
          <el-menu-item index="/member/studentlist">在读学生</el-menu-item>
          <el-menu-item index="/member/alumni">桃李天下</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/link/contact">联系我们</el-menu-item>
        <!-- 直接使用 store.isLogin -->
        <el-menu-item index="/resource" v-show="store.isLogin">资源共享</el-menu-item>
      </el-menu>
    </div>

    <!-- 移动端导航菜单 -->
    <div class="mobile-menu" v-if="isMobileMenuOpen">
      <el-menu
        class="mobile-nav-menu"
        :default-active="activeKey"
        @select="handleMenuSelect"
        background-color="#fff"
        text-color="#333"
        active-text-color="#409EFF"
        router
      >
        <!-- 菜单内容同桌面端 -->
        <el-menu-item index="/">首页</el-menu-item>
        <el-menu-item index="/team/introduction">团队介绍</el-menu-item>
        <el-sub-menu index="/search">
          <template #title>研究成果</template>
          <el-menu-item index="/search/project">研究项目</el-menu-item>
          <el-menu-item index="/search/paper">发表论文</el-menu-item>
          <el-menu-item index="/search/award">获奖情况</el-menu-item>
        </el-sub-menu>
        <el-menu-item index="/resource" v-show="store.isLogin">资源共享</el-menu-item>
        <el-menu-item
          v-if="store.isLogin && store.userType === 'admin'"
          index="/admin/dashboard"
          class="mobile-backend-item"
        >
          <span>后台管理</span>
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted,computed } from 'vue'
import { ElMessage } from 'element-plus'
import { useCounterStore } from '../stores/counter'
import { useRouter, useRoute } from 'vue-router'
import { Menu, Close, ArrowRight } from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const store = useCounterStore()


// 组件内只需要定义与自身UI相关的状态
const isMobileMenuOpen = ref(false) // 移动端菜单是否展开
const isLoading = ref(false) // 加载状态

const activeKey = ref('')
// 根据路径设置激活的key
const setActiveKeyByPath = (path) => {
  const menuItems = [
    '/',
    '/team/introduction',
    '/search/project',
    '/search/paper',
    '/search/award',
    '/news/activity',
    '/news/event',
    '/news/student',
    '/news/photo',
    '/member/teacher',
    '/member/studentlist',
    '/member/alumni',
    '/link/contact',
    '/resource',
    '/admin/dashboard',
  ]

  // 1. 优先匹配完全相等的路径
  let matchedKey = menuItems.find((item) => path === item)

  // 2. 如果没有完全匹配，再匹配父路径
  if (!matchedKey) {
    const parentPaths = [
      { path: '/search', key: '/search' },
      { path: '/news', key: '/news' },
      { path: '/member', key: '/member' },
    ]
    const matchedParent = parentPaths.find((item) => path.startsWith(item.path))
    matchedKey = matchedParent ? matchedParent.key : path
  }

  // 3. 【新增】处理动态路由，让 /member/teacher/xxx 也高亮 /member/teacher
  if (path.startsWith('/member/teacher/')) {
    matchedKey = '/member/teacher'
  }

  activeKey.value = matchedKey
}
watch(
  () => route.path,
  (newPath) => {
    setActiveKeyByPath(newPath)
  },
  { immediate: true } // 立即执行一次
)

// 处理菜单选择
const handleMenuSelect = (key) => {
  if (key === 'logout') {
    handleLogout()
  } else {
    activeKey.value = key
    router.push(key)
    isMobileMenuOpen.value = false
  }
}

// 处理登出：调用 store 的 action
const handleLogout = () => {
  isLoading.value = true
  setTimeout(() => {
    store.logout()
    ElMessage.success('已退出登录')
    // 登出后重置激活状态
    activeKey.value = ''
    if (route.path !== '/') {
      router.push('/')
    }
    isLoading.value = false
  }, 500)
}

// 处理“后台管理”跳转
const handleGoToBackend = () => {
  if (store.userType === 'admin') {
    const userName = computed(() => store.$state.userName)
    const backendUrl = 'http://localhost:5174/home'
    const token = sessionStorage.getItem('token')
    window.open(`${backendUrl}?token=${token}&userName=${userName.value}`, '_blank')
  }
}

// 处理用户区域点击
const handleUserClick = () => {
  if (!store.isLogin) {
    router.push('/login')
  }
}

// 在组件挂载后刷新页面，确保状态正确
onMounted(() => {
  // 使用一个标记来防止无限刷新
  const hasRefreshed = sessionStorage.getItem('headerRefreshed')
  if (!hasRefreshed) {
    // 标记为已刷新
    sessionStorage.setItem('headerRefreshed', 'true')
    // 刷新页面
    location.reload()
  } else {
    // 如果已经刷新过，清除标记，以便下次登录时能再次触发刷新
    sessionStorage.removeItem('headerRefreshed')
  }
})
</script>

<style scoped lang="less">
.allheader {
  width: 100%;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.08);
  background: #fff;
  position: relative;
  z-index: 1001;

  .header-top {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    height: 80px;
    background: #fff;

    .logo {
      cursor: pointer;
      img {
        object-fit: contain;
      }
    }

    .search-center {
      flex: 1;
      display: flex;
      justify-content: center;
      padding: 0 20px;
      @media (max-width: 768px) {
        display: none; // 移动端隐藏搜索框
      }
    }

    .search-input {
      width: 350px;
      max-width: 100%;
      height: 38px;
      border-radius: 20px;
      box-shadow: none;
      background: #f5f7fa;
    }

    .header-actions {
      display: flex;
      align-items: center;
      gap: 12px;

      .backend-btn {
        background-color: #41b883;
        border-color: #41b883;
        &:hover {
          background-color: #36a273;
          border-color: #36a273;
        }
      }

      // 用户身份文本样式
      .user-role {
        padding: 5px 10px;
        border-radius: 4px;
        background-color: #f0f9f0;
        color: #41b883;
        font-size: 14px;
        font-weight: 500;
      }
      .guest-text {
        padding: 5px 10px;
        border-radius: 4px;
        border: 1px solid #ddd;
        color: #666;
        font-size: 14px;
        cursor: pointer;
        &:hover {
          background-color: #f5f5f5;
        }
      }
    }
  }

  // 桌面端菜单容器
  .desktop-menu {
    @media (max-width: 992px) {
      display: none;
    }
  }

  .header-menu {
    border-bottom: none;
    width: 100%;
    margin: 0 auto;
    display: flex;
    justify-content: center;
    font-size: 15px;
    background-color: #f8f9fa !important;

    & :deep(.el-menu-item),
    & :deep(.el-sub-menu__title) {
      border-radius: 4px;
      margin: 0 8px;
      height: 45px;
      line-height: 45px;
      color: #555;
      &:hover {
        background: #e9ecef;
        color: #409eff;
      }
    }
    & :deep(.el-menu-item.is-active),
    & :deep(.el-sub-menu__title.is-active) {
      color: #409eff;
      font-weight: 500;
    }
  }

  // 移动端菜单
  .mobile-menu-btn {
    display: none;
    font-size: 24px;
    cursor: pointer;
    color: #555;
    @media (max-width: 992px) {
      display: block;
    }
  }

  .mobile-menu {
    display: none;
    background-color: #fff;
    border-top: 1px solid #eee;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
    @media (max-width: 992px) {
      display: block;
    }
    &.mobile-nav-menu {
      width: 100%;
      border-right: none;
    }
    .mobile-backend-item {
      background-color: #f0f9f0;
      color: #41b883 !important;
      &:hover {
        background-color: #e0f2e0 !important;
      }
    }
  }

  // 用户信息下拉项
  & :deep(.user-info-item) {
    .user-info-name {
      font-weight: 500;
    }
  }
}
</style>
