import service from "./request";

// const mockUserData =
//     "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1QjlBRUQ4OUFEOTU3RjY2MDYzQzI2QTBBQzJDNzAzQiIsInVzZXIiOiJ7XCJpZFwiOlwiMVwiLFwicm9sZVwiOjAsXCJ1c2VybmFtZVwiOlwiYWRtaW5cIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE3NjA2MTQ0NDAsImV4cCI6MTc2MDYxNjI0MH0.JMRNwAzBI_AAcF_90T-bX1DiZlM0it8HpeLgS0JrSkQ"

//登录请求
export const UserLogin = async (username,password) =>{
  // const useMock = false;
  console.log('登录的参数：',typeof username,typeof password)

  // if(useMock){
  //     return {
  //     "code": 1,
  //     "message": "success",
  //     "data": mockUserData
  //     }
  //   }
    return service.get('/api/user/login',{
      params:{
        username,
        password
      }
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
