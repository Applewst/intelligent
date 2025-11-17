import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useCounterStore = defineStore('counter', () => {
  const count = ref(0)
  const doubleCount = computed(() => count.value * 2)
  function increment() {
    count.value++
  }
  const userName = ref('')   //用户名
  const userType = ref('')    //用户类型
  const isLogin = ref(false)  //是否登录


  if(localStorage.getItem('token')){
    isLogin.value=true;
    userName.value=localStorage.getItem('username')||'';
  }
  const setUser = (name, type) => {
    userName.value = name
    userType.value = type
  }  //修改用户名和用户类型

  const token = ref('')   //token
  return {
    count,
    doubleCount,
    increment ,
    userName,
    userType,
    isLogin,
    setUser,
    token

  }
})
