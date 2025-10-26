import service from "./request";


//登录请求
export const UserLogin = async (username,password) =>{
  return service.get('/api/user/login',{
    username,
    password
  }
  )
}

//注册请求
export const UserRegister = async (username,password,identify) =>{
  return service.post('/api/user/register',{
    username,
    password,
    identify
  })
}