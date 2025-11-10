import service from './request';
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
    time: "2023-10-01",
    detail: "这是关于神经网络原理与应用的内容。",
  },
  {
    id: 2,
    title: "卷积神经网络的深度学习",
    author: "王丽",
    time: "2023-11-01",
    detail: "这是关于卷积神经网络深度学习的内容。",
  },
  {
    id: 3,
    title: "神经网络在医疗领域的应用",
    author: "张伟",
    time: "2023-10-01",
    detail: "这是关于神经网络在医疗领域应用的内容。",
  },
  {
    id: 4,
    title: "机器学习中的特征选择",
    author: "李华",
    time: "2023-09-15",
    detail: "这是关于机器学习中特征选择的内容。",
  },
  {
    id: 5,
    title: "深度学习模型的优化策略",
    author: "王丽",
    time: "2023-08-20",
    detail: "这是关于深度学习模型优化策略的内容。",
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

const useMock = true;

//-----------------------------------------------------------
//获取项目
export const GetSearchProject = async (...params) => {
  
  console.log("获取项目API",...params)
  if (useMock) {
    return {
      "code":1,
      "message":"success",
      "data":projectList
    }
 }
 service.get('/api/search/project', { ...params })
}
//新增项目
export const AddSearchProject = async (...project) => {
  console.log("新增项目API",...project)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.post('/api/search/projects', {...project })
}
//编辑项目
export const EditSearchProject = async (...project) => {
  console.log("编辑项目API",...project)
  if(useMock){
    return {
      "code":1,
      "message":"success",
      "data":null
    }
  }
  return service.put('/api/search/projects', {...project })
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
  return service.delete('/api/search/projects',  { params: { id } })
}
//-----------------------------------------------------------
// 获取论文列表
export const GetPaperList = async (...paper) => {
 
  if(useMock){
    console.log(paper);
    
    return {
      "code": 1,
      "message": "success",
      "data": {
        "total": paperList.length,
        "rows":paperList
      }
    }
  }

  return service.post('/api/search/papers/lisst', {
    ...paper
  })
}
//论文新增
export const AddPaper = async (...paper) => {
  if(useMock){
    console.log("论文新增API:",...paper) 
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.post('/api/search/papers', {
    ...paper
  })
}
//论文修改
export const UpdatePaper = async (...paper) => {
  if(useMock){
    console.log("论文修改内容API:",...paper)
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.put('/api/search/paper', {
    ...paper
  })
}
//论文删除
export const DeletePaper = async (id) => {
  if(useMock){
    console.log("论文删除API",id)
    return {
      "code": 1,
      "message":"success",
      "data":null
    }
  }
  return service.delete('/api/search/papers',id)
}
//-----------------------------------------------------------
//获取获奖列表
export const GetAwardList = async (...award) => {
  console.log("获取获奖API",award)
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data": {
        "rows": awardList
      }
    }
  }
  return service.get('/api/search/awards', {...award})
}
//新增获奖
export const AddAward = async (...award) => {
  console.log("新增获奖API",...award)
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.post('/api/search/awards', {...award})
}
//修改获奖
export const UpdateAward = async (...award) => {
  console.log("修改获奖API",...award)
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data":null
    }
  }
  return service.put('/api/search/awards', {...award})
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
  return service.delete('/api/search/awards',  { params: { id } })
}