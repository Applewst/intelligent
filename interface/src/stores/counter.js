import { ref, computed ,onMounted,onUnmounted} from 'vue'
import { defineStore } from 'pinia'
import { jwtDecode } from 'jwt-decode';

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }

  const userName = ref('')
  const userType = ref('') // 存储解析后的 role
  const isLogin = ref(false)
  const token = ref(sessionStorage.getItem('token') || '')

  // 解析 Token 并提取用户信息
  const decodeToken = (authToken) => {
    if (!authToken) {
      return { username: '', role: '' }
    }
    try {
      const decoded = jwtDecode(authToken)
      const userInfo = typeof decoded.user === 'string' ? JSON.parse(decoded.user) : decoded.user
      return {
        username: userInfo.username || '',
        role: userInfo.identity === 'admin' ? 'admin' : 'user'
      }
    } catch (error) {
      console.error("解析 Token 失败:", error)
      return { username: '', role: '' }
    }
  }

  // 初始化时解析
  if (token.value) {
    const userInfo = decodeToken(token.value)
    userName.value = userInfo.username
    userType.value = userInfo.role
    isLogin.value = true
  }

  // 登录时设置 Token 并解析
  const setUser = (authToken) => {
    // console.log(authToken);

    token.value = authToken
    sessionStorage.setItem('token', authToken)

    const userInfo = decodeToken(authToken)
    userName.value = userInfo.username
    userType.value = userInfo.role
    isLogin.value = !!userInfo.username
    console.log(userInfo);
  }

  // 登出
  const logout = () => {
    token.value = ''
    userName.value = ''
    userType.value = ''
    isLogin.value = false
    sessionStorage.removeItem('token')
  }

  return {
    count,
    doubleCount,
    increment,
    userName,
    userType,
    isLogin,
    token,
    setUser,
    logout
  }
})
