import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useAppStore = defineStore('app', () => {
  // 应用加载状态
  const loading = ref(false)
  
  // 侧边栏折叠状态
  const sidebarCollapsed = ref(false)
  
  // 主题模式
  const theme = ref(localStorage.getItem('theme') || 'light')
  
  // 语言设置
  const locale = ref(localStorage.getItem('locale') || 'zh-CN')
  
  // 页面标题
  const pageTitle = ref('智能化软件与服务')
  
  // 面包屑导航
  const breadcrumbs = ref([])
  
  // 计算属性：是否为暗色主题
  const isDark = computed(() => theme.value === 'dark')
  
  // 设置加载状态
  const setLoading = (status) => {
    loading.value = status
  }
  
  // 切换侧边栏
  const toggleSidebar = () => {
    sidebarCollapsed.value = !sidebarCollapsed.value
  }
  
  // 设置侧边栏状态
  const setSidebarCollapsed = (collapsed) => {
    sidebarCollapsed.value = collapsed
  }
  
  // 切换主题
  const toggleTheme = () => {
    theme.value = theme.value === 'light' ? 'dark' : 'light'
    localStorage.setItem('theme', theme.value)
    // 应用主题到 document
    document.documentElement.setAttribute('data-theme', theme.value)
  }
  
  // 设置主题
  const setTheme = (newTheme) => {
    theme.value = newTheme
    localStorage.setItem('theme', newTheme)
    document.documentElement.setAttribute('data-theme', newTheme)
  }
  
  // 设置语言
  const setLocale = (newLocale) => {
    locale.value = newLocale
    localStorage.setItem('locale', newLocale)
  }
  
  // 设置页面标题
  const setPageTitle = (title) => {
    pageTitle.value = title
    document.title = title
  }
  
  // 设置面包屑
  const setBreadcrumbs = (crumbs) => {
    breadcrumbs.value = crumbs
  }
  
  // 添加面包屑
  const addBreadcrumb = (crumb) => {
    breadcrumbs.value.push(crumb)
  }
  
  // 清除面包屑
  const clearBreadcrumbs = () => {
    breadcrumbs.value = []
  }
  
  return {
    loading,
    sidebarCollapsed,
    theme,
    locale,
    pageTitle,
    breadcrumbs,
    isDark,
    setLoading,
    toggleSidebar,
    setSidebarCollapsed,
    toggleTheme,
    setTheme,
    setLocale,
    setPageTitle,
    setBreadcrumbs,
    addBreadcrumb,
    clearBreadcrumbs
  }
})
