import axios from 'axios';
import { ElMessage } from 'element-plus'; // 引入 Element Plus 的消息提示组件
import 'element-plus/theme-chalk/el-message.css'; // 引入对应的样式文件

const baseURL = 'http://blog.kkstar.online/api'

const service = axios.create({
  baseURL,
  // timeout: 10000
})

// 添加请求拦截器
service.interceptors.request.use(config => {
    // 在发送请求之前做些什么
    const TOKEN = new URLSearchParams(window.location.search);
    const token1 = TOKEN.get('token');    
   if (token1) {
      sessionStorage.setItem('token', token1);
    }
    console.log('是否能拿到token',token1);
    const token = sessionStorage.getItem('token');
    console.log(token);
    
    // 为所有请求添加 token
    if (token) {
      config.headers['token'] = token;
    }
    
    // 确保 Content-Type 为 application/json (对于非 FormData 请求)
    // 注意：当上传文件时，axios 会自动设置 Content-Type 为 multipart/form-data，这里的代码不会覆盖它
    if (!config.headers['Content-Type']) {
      config.headers['Content-Type'] = 'application/json';
    }
    
    return config;
  }, error => {
    // 对请求错误做些什么
    console.error('请求发送失败:', error);
    ElMessage.error('请求发送失败，请检查网络连接');
    return Promise.reject(error);
  });

// 添加响应拦截器
service.interceptors.response.use(response => {
    // 2xx 范围内的状态码都会触发该函数。
    const res = response.data;

    // 如果后端返回的 code 不是 1 (假设 1 代表成功)，则认为是业务逻辑错误
    if (res.code !== 1) {
      // 显示后端返回的错误信息，如果没有，则显示默认信息
      ElMessage.error(res.message || '操作失败，请稍后重试');
      
      // 将错误向下传递，让调用方可以通过 try...catch 捕获
      return Promise.reject(new Error(res.message || 'Error'));
    }
    console.log(res);
    
    // 如果 code 是 1，说明成功，直接返回数据
    return res;

  }, error => {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    console.log('响应错误:', error);

    // 从 error.response 中获取状态码和错误信息
    const status = error.response?.status;
    const errorMsg = error.response?.data?.message;

    switch (status) {
      case 401:
        // 未授权，通常是 Token 过期或无效
        ElMessage.error('登录状态已过期，请重新登录');
        // 可以在这里添加跳转到登录页的逻辑
        // router.push('/login');
        break;
      case 403:
        // 禁止访问，权限不足
        ElMessage.error('您没有足够的权限执行此操作');
        break;
      case 404:
        // 资源未找到
        ElMessage.error('请求的资源不存在');
        break;
      case 500:
        // 服务器内部错误
        ElMessage.error('服务器内部错误，请稍后重试');
        break;
      default:
        // 其他所有错误
        ElMessage.error(errorMsg || '请求失败，请稍后重试');
    }
    
    // 将错误向下传递
    return Promise.reject(error);
  });

export default service;
export { baseURL };