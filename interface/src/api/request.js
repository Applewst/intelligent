import axios from 'axios';
import { ElMessage } from 'element-plus'; // 1. 引入 Element Plus 的消息提示组件
import 'element-plus/theme-chalk/el-message.css'; // 2. 引入对应的样式文件

const baseURL = 'http://blog.kkstar.online/api'

const service = axios.create({
  baseURL,
  timeout: 10000
})

// 添加请求拦截器
service.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    const token = localStorage.getItem('token')
    if (token) {
      // 注意：这里的 header 字段是 'token'，请确保与后端约定一致
      // 有些后端可能使用 'Authorization': `Bearer ${token}`
      config.headers['token'] = token
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么
    console.error('请求发送失败:', error);
    ElMessage.error('请求发送失败，请检查网络连接'); // 3. 请求发送失败时的提示
    return Promise.reject(error);
  });

// 添加响应拦截器
service.interceptors.response.use(function (response) {
 if (response.config.responseType === 'blob') {
  console.log(123);

      // 直接返回整个 response 对象，而不是 response.data
      // 这样调用方可以同时获取到 blob 数据和响应头（例如文件名可能在 headers 里）
      return response;
    }

    // 2xx 范围内的状态码都会触发该函数。
    const res = response.data;

    // 4. 统一处理业务逻辑错误
    // 如果后端返回的 code 不是 1 (假设 1 代表成功)，则认为是失败
    if (res.code !== 1) {
      // 显示后端返回的错误信息，如果没有，则显示默认信息
      ElMessage.error(res.message || '操作失败，请稍后重试');

      // 将错误向下传递，让调用方可以通过 try...catch 捕获
      return Promise.reject(new Error(res.message || '业务逻辑错误'));
    }

    // 如果 code 是 1，说明成功，直接返回数据
    return res;

  }, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 5. 统一处理 HTTP 错误
    console.log('响应错误:', error);

    const status = error.response?.status;
    const errorMsg = error.response?.data?.message;

    switch (status) {
      case 401:
        ElMessage.error('登录状态已过期，请重新登录');
        // 可以在这里添加跳转到登录页的逻辑
        // router.push('/login');
        break;
      case 403:
        ElMessage.error('您没有足够的权限执行此操作');
        break;
      case 404:
        ElMessage.error('请求的接口不存在');
        break;
      case 500:
        ElMessage.error('服务器内部错误，请稍后重试');
        break;
      default:
        ElMessage.error(errorMsg || '请求失败，请稍后重试');
    }

    return Promise.reject(error);
  });

export default service;
export { baseURL };
