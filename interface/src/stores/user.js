import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', () => {
  // 用户信息
  const userInfo = ref({
    id: null,
    username: '',
    email: '',
    avatar: '',
    role: '', // 用户角色：admin, teacher, student
    permissions: []
  })
  
  // 登录状态
  const isLoggedIn = ref(false)
  
  // 登录令牌
  const token = ref(localStorage.getItem('token') || '')
  
  // 计算属性：是否为管理员
  const isAdmin = computed(() => userInfo.value.role === 'admin')
  
  // 计算属性：是否为教师
  const isTeacher = computed(() => userInfo.value.role === 'teacher')
  
  // 计算属性：是否为学生
  const isStudent = computed(() => userInfo.value.role === 'student')
  
  // 设置用户信息
  const setUserInfo = (info) => {
    userInfo.value = { ...userInfo.value, ...info }
  }
  
  // 设置登录状态
  const setLoginStatus = (status) => {
    isLoggedIn.value = status
  }
  
  // 设置令牌
  const setToken = (newToken) => {
    token.value = newToken
    if (newToken) {
      localStorage.setItem('token', newToken)
    } else {
      localStorage.removeItem('token')
    }
  }
  
  // 登录
  const login = (userData, authToken) => {
    setUserInfo(userData)
    setToken(authToken)
    setLoginStatus(true)
  }
  
  // 登出
  const logout = () => {
    userInfo.value = {
      id: null,
      username: '',
      email: '',
      avatar: '',
      role: '',
      permissions: []
    }
    setToken('')
    setLoginStatus(false)
  }
  
  // 检查权限
  const hasPermission = (permission) => {
    return userInfo.value.permissions.includes(permission)
  }
  
  // 检查角色
  const hasRole = (role) => {
    return userInfo.value.role === role
  }
  
  return {
    userInfo,
    isLoggedIn,
    token,
    isAdmin,
    isTeacher,
    isStudent,
    setUserInfo,
    setLoginStatus,
    setToken,
    login,
    logout,
    hasPermission,
    hasRole
  }
})
