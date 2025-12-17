import { time } from 'echarts';
import service from './request';
// import axios from 'axios';
const projectList = [
    {
      id: 1,
      title: '神经网络的原理与应用',
      // sort: '服务推荐',
      image:'https://example.com/image1.jpg',
      time: '2023-10-01'
    },
    {
      id: 2,
      title: '卷积神经网络的深度学习',
      // sort: '图像处理',
      image:'https://example.com/image1.jpg',
      time: '2023-11-01'
    },
    {
      id: 3,
      title: '神经网络在医疗领域的应用',
      // sort: '服务推荐',
      image:'https://example.com/image1.jpg',
      time: '2023-10-01'
    },
    {
      id: 2,
      title: '机器学习中的特征选择',
      // sort: '自然语言',
      image:'https://example.com/image1.jpg',
      time: '2023-09-15'
    },
    {
      id: 3,
      title: '深度学习模型的优化策略',
      // sort: '图像处理',
      image:'https://example.com/image1.jpg',
      time: '2023-08-20'
    }
]  //项目数据
const paperList = [
  {
    id: 1,
    title: "神经网络的原理与应用",
    author: "张伟",
    file: "https://example.com/paper1.pdf",
    time: "2023-10-01",
    detail: "这是关于神经网络原理与应用的内容。",
  },
  {
    id: 2,
    title: "卷积神经网络的深度学习",
    author: "王丽",
    file: "https://example.com/paper2.pdf",
    time: "2023-11-01",
    detail: "这是关于卷积神经网络深度学习的内容。",
  },
  {
    id: 3,
    title: "神经网络在医疗领域的应用",
    author: "张伟",
    file: "https://example.com/paper3.pdf",
    time: "2023-10-01",
    detail: "这是关于神经网络在医疗领域应用的内容。",
  },
  {
    id: 4,
    title: "机器学习中的特征选择",
    author: "李华",
    file: "https://example.com/paper4.pdf",
    time: "2023-09-15",
    detail: "这是关于机器学习中特征选择的内容。",
  },
  {
    id: 5,
    title: "深度学习模型的优化策略",
    author: "王丽",
    file: "https://example.com/paper5.pdf",
    time: "2023-08-20",
    detail: "这是关于深度学习模型优化策略的内容。",
  },
  {
    id: 6,
    title: "深度学习的应用",
    author: "哇洒",
    file: "https://example.com/paper6.pdf",
    time: "2022-07-25",
    detail: "这是关于深度学习的应用的内容。",
  },
  {
    id: 7,
    title: "深度学习的发展趋势",
    author: "张胜利",
    file: "https://example.com/paper7.pdf",
    time: "2025-06-28",
    detail: "这是关于深度学习的发展趋势的内容。",
  },
  {
    id: 8,
    title: "深度学习的应用",
    time: "2022-05-31",
    author: "钱七",
    file: "https://example.com/paper8.pdf",
    detail: "这是关于深度学习的应用的内容。",
  },
  {
    id: 9,
    title: "深度学习的发展趋势",
    author: "王五",
    time: "2021-05-06",
    detail: "这是关于深度学习的发展趋势的内容。",
  },
  {
    id: 10,
    title: "深度学习的应用",
    author: "赵六",
    time: "2022-04-11",
    detail: "这是关于深度学习的应用的内容。",
  },
  {
    id: 11,
    title: "深度学习的发展趋势",
    author: "王麻子",
    time: "2022-03-17",
    detail: "这是关于深度学习的发展趋势的内容。",
  },
  {
    id: 12,
    title: "深度学习的应用",
    time: "2022-02-22",
    detail: "这是关于深度学习的应用的内容。",
  },
  {
    id: 13,
    title: "深度学习的发展趋势",
    author: "张三",
    time: "2022-01-28",
    detail: "这是关于深度学习的发展趋势的内容。",
  },
  {
    id: 14,
    title: "深度学习的应用",
    time: "2021-12-31",
    detail: "这是关于深度学习的应用的内容。",
  },
  {
    id: 15,
    title: "深度学习的发展趋势",
    author: "王五",
    time: "2021-12-06",
    detail: "这是关于深度学习的发展趋势的内容。",
  },
  {
    id: 16,
    title: "深度学习的应用",
    author: "赵六",
    time: "2021-11-11",
    detail: "这是关于深度学习的应用的内容。",
  },    

]    //论文数据
const awardList = [
  {
    id: 1,
    detail: "2022年获得国家科技进步奖二等奖，表彰在人工智能领域的突出贡献。",
    time: "2022-12-15",
    author: "张伟",
    image:'https://example.com/award1.jpg',
  },
  {
    id: 2,
    detail: "2022年国际人工智能大会最佳论文奖",
    time: "2022-11-20",
    author: "王丽",
    image:'https://example.com/award2.jpg',
  },
  {
    id: 3,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-10-25",
    author: "张信",
    image:'https://example.com/award3.jpg',
  },
  {
    id: 4,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-09-30",
    author: "马丽",
    image:'https://example.com/award4.jpg',
  },
  {
    id: 5,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-08-18",
    author: "横跨",
    image:'https://example.com/award5.jpg',
  },
  {
    id: 6,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-07-23",
    author: "李四",
    image:'https://example.com/award6.jpg',
  },
  {
    id: 7,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-06-28",
    author: "王五",
    image:'https://example.com/award7.jpg',
  },
  {
    id: 8,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-05-31",
    author:"赵六",
    image:'https://example.com/award8.jpg',
  },
  {
    id: 9,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-05-06",
    author:"钱七",
    image:'https://example.com/award9.jpg',
  },
  {
    id: 10,
    detail: "2022年被评为年度杰出青年科学家",
    time: "2022-04-11",
    author: "王麻子",
    image:'https://example.com/award10.jpg',
  }
]//获奖数据
const direction =  [
          {
            id: 1,
            name: '深度学习在图像识别中的应用',
            file: '/placeholder.svg?height=300&width=400',     
            time : '2023-10-01',
            detail: '这是关于深度学习在图像识别中的应用的研究方向。',
            author:"张伟",
          },
          {
            id: 2,
            name: '自然语言处理的最新进展',
            file: '/placeholder.svg?height=300&width=400',     
            time : '2023-11-15',
            detail: '这是关于自然语言处理最新进展的研究方向。',
            author:"李红",
          },
          {
            id: 3,
            name: '强化学习在游戏中的应用',
            file: '/placeholder.svg?height=300&width=400',     
            time : '2023-09-20',
            detail: '这是关于强化学习在游戏中应用的研究方向。',
            author:"王丽",
          }
] //研究方向数据
const useMock = false

//-----------------------------------------------------------
//获取项目
export const GetSearchProject = async (pageNum, pageSize, name) => {
  console.log("获取项目API",pageNum, pageSize, name)
  if (useMock) {
    return {
      "code":1,
      "message":"success",
      "data":{
        "data": projectList,
      }
    }
 }
 return service.get('/search/list', {
    params: {
      pageNum,
      pageSize,
      name
    }
  })
}
//新增项目
export const AddSearchProject = async (title,image) => {
  console.log("新增项目API",title,image)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.post('/search', {
    title,
    image
   })
}
//编辑项目
export const EditSearchProject = async (id,title,image) => {
  console.log("编辑项目API",id,title,image)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.put('/search/project', {
    id,
    title,
    image
   })
}
//删除项目
export const DeleteSearchProject = async (id) => {
  console.log("删除项目API:",id)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.delete('/search',  { params: { id } })
}
//-----------------------------------------------------------
// 获取论文列表
export const GetPaperList = async (pageNum, pageSize, author, title,) => {
 console.log('获取论文列表Api',pageNum, pageSize, author, title);
 if (useMock) {
    return {
      "code": 1,
      "message": "success",
      "data": {
        "data": paperList,
        "total": paperList.length
    }
  }}
  return service.get('/search/papers/list', {
    params: {
      pageNum,
      pageSize,
      author,
      title
    }
  })

}
//论文新增
export const AddPaper = async (title,author,detail,file) => {
  console.log("论文新增API:",title,author,detail,file) 
  if(useMock){
    
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.post('/search/papers', {
    title,
    author,
    detail,
    file
  })
}
//论文修改
export const UpdatePaper = async (id,title,author,detail,file) => {
  console.log("论文修改内容API:",id,title,author,detail,file)
  if(useMock){
    
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.put('/search/papers', {
    id,
    title,
    author,
    detail,
    file
  })
}
//论文删除
export const DeletePaper = async (id) => {
  console.log("论文删除API",id)
  if(useMock){
    return {
      "code": 1,
      "message":"success",
      "data":null
    }
  }
  return service.delete('/search/papers',{
    params: {
      id
    }
  })
}
//-----------------------------------------------------------
//获取获奖列表
export const GetAwardList = async (pageNum, pageSize, author) => {
  console.log("获取获奖API",pageNum, pageSize, author)
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data": {
        "data": awardList,
        "total": awardList.length
      }
    }
  }
  return service.get('/search/awards/list', {
    params: {
      pageNum,
      pageSize,
      author
    }
  })
}
//新增获奖
export const AddAward = async (detail,author,file,time) => {
  console.log("新增获奖API",detail,author,file,time)
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.post('/search/awards', {
    detail,
    author,
    file,
    time
  })
}
//修改获奖
export const UpdateAward = async (id,detail,author,file,time) => {
  console.log("修改获奖API",id,detail,author,file,time)
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.put('/search/awards', {
    id,
    detail,
    author,
    file,
    time
  })
}
//删除获奖
export const DeleteAward = async (id) => {
  console.log("删除获奖API",id)
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.delete('/search/awards',  { params: { id } })
}



//查询研究方向
export const GetResearchDirections = async (pageNum,pageSize,name,author) => {
  console.log("查询研究方向API",pageNum,pageSize,name,author)
  let useMock1 = true
  if(useMock1){
    return {
      "code": 1,
      "message": "success",
      "data": {
        "data": direction,
        "total": direction.length
      }
    }
  }
  return service.get('/search/direction', {
      params: {
        pageNum,
        pageSize,
        name,
        author
      }
    })
}
//新增研究方向
export const AddResearchDirection = async (name,detail,author,file) => {
  console.log("新增研究方向API",name,detail,author,file)
  let useMock1 = true
  if(useMock1){
    return {
      "code": 1,
      "message": "success",
      "data": null
    }
  }
  return service.post('/search/direction', {
    title,
    detail,
    author,
    file
   })
}
//编辑研究方向
export const EditResearchDirection = async (id,name,detail,author,file) => {
  console.log("编辑研究方向API",id,name,detail,author,file)
  let useMock1 = true
  if(useMock1){
    return {
      "code": 1,
      "message": "success",
      "data": null
    }
  }       
  return service.put('/search/direction', {
    id,
    title,
    detail,
    author,
    file
   })
}
//删除研究方向
export const DeleteResearchDirection = async (id) => {
  console.log("删除研究方向API",id)
  let useMock1 = true
  if(useMock1){
    return {
      "code": 1,
      "message": "success",
      "data": null
    }
  }
  return service.delete('/search/direction', { params: { id } })
}
