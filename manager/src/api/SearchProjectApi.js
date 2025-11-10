import service from './request';
export const GetSearchProject = async (...params) => {
  const useMock = true;
  console.log(...params)
  
  if (useMock) {
    return {
      "code":1,
      "message":"success",
      "data":[
          {
            id: 1,
            title: '神经网络的原理与应用',
            // sort: '服务推荐',
            time: '2023-10-01'
          },
          {
            id: 2,
            title: '卷积神经网络的深度学习',
            // sort: '图像处理',
            time: '2023-11-01'
          },
          {
            id: 3,
            title: '神经网络在医疗领域的应用',
            // sort: '服务推荐',
            time: '2023-10-01'
          },
          {
            id: 2,
            title: '机器学习中的特征选择',
            // sort: '自然语言',
            time: '2023-09-15'
          },
          {
            id: 3,
            title: '深度学习模型的优化策略',
            // sort: '图像处理',
            time: '2023-08-20'
          }
      ]
    }
 }
 service.get('/api/search/project', { ...params })
}