import axios from 'axios';
const baseURL = 'http://blog.kkstar.online' //后面再改
const service = axios.create({
  baseURL,
  // timeout:10000
})
// 添加请求拦截器
service.interceptors.request.use(config => {
    // 在发送请求之前做些什么
    const token = localStorage.getItem('token')
    console.log('请求拦截器的token',token)
    
   // 为所有请求添加 token，不限制请求方法
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    
    // 确保 Content-Type 为 application/json
    if (!config.headers['Content-Type']) {
      config.headers['Content-Type'] = 'application/json';
    }
    
    return config;
  }, error=> {
    // 对请求错误做些什么
    return Promise.reject(error);
  });

// 添加响应拦截器
service.interceptors.response.use(res=> {
    // 2xx 范围内的状态码都会触发该函数。
    if(res.data.code === 1){
      console.log('后端返回的数据res',res)
    }
    return res;

  },error=>{
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    console.log('错误打印');
    
    return Promise.reject(error);
  });
  export default service;
  export {baseURL}
