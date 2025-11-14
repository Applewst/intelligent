
import service from "./request";
//照片墙管理
const shootList = [
   {
    id: 1,
    title: '神经网络的原理与应用',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-01',
    detail: '深度学习中神经网络的基本原理和实际应用案例分析'
  },
  {
    id: 2,
    title: '卷积神经网络的深度学习',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-02',
    detail: 'CNN在图像识别领域的应用研究'
  },
  {
    id: 3,
    title: '神经网络在医疗领域的应用',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-03',
    detail: '利用神经网络进行疾病诊断和预测'
  },
  {
    id: 4,
    title: '机器学习中的特征选择',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-04',
    detail: '特征工程在机器学习模型中的重要性'
  },
  {
    id: 5,
    title: '深度学习模型的优化策略',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-05',
    detail: '梯度下降、Adam等优化算法的比较研究'
  },
  {
    id: 6,
    title: '自然语言处理技术',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-06',
    detail: 'Transformer模型在NLP中的应用'
  },
  {
    id: 7,
    title: '计算机视觉应用',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-07',
    detail: '目标检测和图像分割技术'
  },
  {
    id: 8,
    title: '强化学习算法',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-08',
    detail: 'Q-learning和DQN算法研究'
  },
  {
    id: 9,
    title: '推荐系统算法',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-09',
    detail: '协同过滤、矩阵分解、深度学习推荐系统'
  },
  {
    id: 10,
    title: '深度学习在医学图像分析中的应用',
    file: '/placeholder.svg?height=300&width=400',
    time: '2021-01-10',
    detail: '医学图像分析中的深度学习技术'
  }
]
const useMock = true
const token = localStorage.getItem('token')
//获取照片墙列表
export const GetShootList = (pageNum, pageSize, title) => {
  console.log('获取照片墙列表API', pageNum, pageSize, title)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":{
        "total":shootList.length,
        "data":shootList
      }
    }
  }
  return service.get('/api/team/shoots', { 
    params: {
      pageNum,
      pageSize,
      title
    }
   })
}
//新增照片墙
export const AddShoot = (title,file,detail) => {
  console.log('新增照片墙API:', title,file,detail)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.post('/api/team/shoots', {
    title,
    file,
    detail,
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
}
//修改照片墙
export const UpdateShoot = async (id,title,file,detail) => {
  console.log('修改照片墙API:', id,title,file,detail)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.put('/api/team/shoots', {
    id,
    title,
    file,
    detail,
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
}
//删除照片墙
export const DeleteShoot = (id) => {
  console.log('删除照片墙API:', id)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.delete('/api/team/shoots', {
    id,
    headers: {
      Authorization: `Bearer ${token}`
    }
  })
}