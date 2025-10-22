import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  // 计数器相关
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  
  // 用户相关（这些功能已迁移到 user store，这里保留用于兼容）
  const userName = ref('')
  const userType = ref('')
  const isLogin = ref(false)
  
  // 计数器操作
  const increment = () => {
    count.value++
  }
  
  const decrement = () => {
    count.value--
  }
  
  const reset = () => {
    count.value = 0
  }
  
  const setCount = (value) => {
    count.value = value
  }
  
  // 用户操作（已迁移到 user store，这里保留用于兼容）
  const setUser = (name, type) => {
    userName.value = name
    userType.value = type
    isLogin.value = true
  }
  
  const clearUser = () => {
    userName.value = ''
    userType.value = ''
    isLogin.value = false
  }
  
  // 计算属性
  const isEven = computed(() => count.value % 2 === 0)
  const isPositive = computed(() => count.value > 0)
  const isNegative = computed(() => count.value < 0)
  const isZero = computed(() => count.value === 0)

  return { 
    // 计数器
    count, 
    doubleCount,
    isEven,
    isPositive,
    isNegative,
    isZero,
    increment,
    decrement,
    reset,
    setCount,
    
    // 用户（兼容性保留）
    userName,
    userType,
    isLogin,
    setUser,
    clearUser
  }
})
