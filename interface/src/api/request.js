import axios from 'axios';
const baseURL = 'http://7b9f8ffb.r3.cpolar.cn' //后面再改
const service = axios.create({
  baseURL,
  timeout:10000
})
// 添加请求拦截器
service.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
service.interceptors.response.use(function (res) {
    // 2xx 范围内的状态码都会触发该函数。
   if(res.data.code===0){
      return res
    }
    ElMessage.error(res.data.message || '服务异常')
  }, function (error) {
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    return Promise.reject(error);
  });
  export default service;
  export {baseURL}
