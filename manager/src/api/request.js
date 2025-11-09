import axios from "axios"

// 创建axios实例
const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "/api",
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
})

// 请求拦截器
request.interceptors.request.use(
  (config) => {
    // 可以在这里添加token等认证信息
    const token = localStorage.getItem("token")
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    console.error("请求错误：", error)
    return Promise.reject(error)
  },
)

// 响应拦截器
request.interceptors.response.use(
  (response) => {
    // 统一处理响应数据
    const res = response.data

    // 根据后端返回的状态码进行处理
    if (res.code !== undefined && res.code !== 200) {
      console.error("业务错误：", res.message || "请求失败")
      return Promise.reject(new Error(res.message || "请求失败"))
    }

    // 返回实际数据
    return res.data || res
  },
  (error) => {
    console.error("响应错误：", error.message)

    // 处理HTTP状态码错误
    if (error.response) {
      switch (error.response.status) {
        case 401:
          console.error("未授权，请重新登录")
          break
        case 403:
          console.error("拒绝访问")
          break
        case 404:
          console.error("请求的资源不存在")
          break
        case 500:
          console.error("服务器内部错误")
          break
        default:
          console.error(`连接错误：${error.response.status}`)
      }
    } else {
      console.error("网络连接失败")
    }

    return Promise.reject(error)
  },
)

export default request
