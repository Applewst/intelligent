import service from './request'
//学生发展
const developList = [
  {
    id: 1,
    name: '张小明',
    time: '2024-09-01',
    detail: '出国留学',
    file: '/placeholder.svg?height=80&width=80'
  },
  {
    id: 2,
    name: '李小红',
    time: '2023-09-01',
    detail: '前往北京参加国际峰会',
    file: '/placeholder.svg?height=80&width=80'
  },
  {
    id: 3,
    name: '王大伟',
    time: '2022-09-01',
    detail: '创建公司',
    file: '/placeholder.svg?height=80&width=80'
  },
  {
    id: 4,
    name: '刘芳芳',
    time: '2021-09-01',
    detail: '阿文阿瓦阿达飒飒大苏打阿松大按时的',
    file: '/placeholder.svg?height=80&width=80'
  },
  {
    id: 5,
    name: '陈浩然',
    time: '2024-09-01',
    detail: '奥委会健康的奥委会打开啊',
    file: '/placeholder.svg?height=80&width=80'
  },
  {
    id: 6,
    name: '赵敏',
    time: '2023-09-01',
    detail: '阿瓦蒂后就卡等哈空间',
    file: '/placeholder.svg?height=80&width=80'
  },
  {
    id: 7,
    name: '孙志强',
    time: '2022-09-01',
    detail: '安康惊魂未定卡机和',
    file: '/placeholder.svg?height=80&width=80'
  },
  {
    id: 8,
    name: '周婷婷',
    time: '2021-09-01',
    detail: '啊how的哈利就哈伦裤DHL卡',
    file: '/placeholder.svg?height=80&width=80'
  }
]
const useMock = false
//获取学生发展列表
export const GetDevelopList = (pageNum,pageSize,name) => {
  console.log('获取学生发展列表API：',pageNum,pageSize,name)
  if (useMock) {
      return {
        "code":1,
        "message":"success",
        "data":{
          "total":developList.length,
          "data":developList
        }
      }
  }
  return service.get('/api/team/developments',{
    params:{
      pageNum,
      pageSize,
      name
    }
  })
}
//新增学生发展
export const AddDevelop = (name,detail,file) => {
  console.log('新增学生发展API：',name,detail,file)
  if (useMock) {
      return {
        "code":1,
        "message":"success",
        "data":null
      }
  }
  return service.post('/api/team/developments',{
    name,
    detail,
    file
  })
}
//修改学生发展
export const UpdateDevelop = (id,name,detail,file) => {
  console.log('编辑学生发展APi：',id,name,detail,file)
  if (useMock) {
      return {
        "code":1,
        "message":"success",
        "data":null
      }
  }
  return service.put('/api/team/developments',{
    id,
    name,
    detail,
    file
  })
}
//删除学生发展
export const DeleteDevelop = (id) => {
  console.log('删除学生发展API：',id)
  if (useMock) {
      return {
        "code":1,
        "message":"success",
        "data":null
      }
  }
  return service.delete('/api/team/developments',id)
}