import service from './request.js';
const paperList = [
  {
    id: 1,
    title: "神经网络的原理与应用",
    author: "张伟",
    time: "2023-10-01",
    content: "这是关于神经网络原理与应用的内容。",
  },
  {
    id: 2,
    title: "卷积神经网络的深度学习",
    author: "王丽",
    time: "2023-11-01",
    content: "这是关于卷积神经网络深度学习的内容。",
  },
  {
    id: 3,
    title: "神经网络在医疗领域的应用",
    author: "张伟",
    time: "2023-10-01",
    content: "这是关于神经网络在医疗领域应用的内容。",
  },
  {
    id: 4,
    title: "机器学习中的特征选择",
    author: "李华",
    time: "2023-09-15",
    content: "这是关于机器学习中特征选择的内容。",
  },
  {
    id: 5,
    title: "深度学习模型的优化策略",
    author: "王丽",
    time: "2023-08-20",
    content: "这是关于深度学习模型优化策略的内容。",
  },
]

// 获取论文列表
export const GetPaperList = async (...paper) => {
  const useMock = true;
  if(useMock){
    console.log(paper);
    
    return {
      "code": 1,
      "message": "success",
      "data": {
        "total": paperList.length,
        row:[
          ...paperList
        ]
      }
    }
  }
  return service.post('/paper/list', {
    ...paper
  })

}