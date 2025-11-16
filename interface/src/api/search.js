import service from '../api/request';
import { ref } from "vue";
const papers = ref([
  {
    id: 1,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 1',
    detail: '这是一篇关于机器学习算法优化的研究，详细探讨了深度学习模型的训练策略。',
    author: '作者 A',
    time: '2023-01-01',
  },
  {
    id: 2,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 2',
    detail: '本文介绍了自然语言处理领域中的一种新型预训练模型，重点是多语言文本理解能力的提升。',
    author: '作者 B',
    time: '2023-02-01',
  },
  {
    id: 3,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 3',
    detail: '研究了图像识别技术在医疗诊断中的应用，对比了卷积神经网络的不同架构对图像分类的影响。',
    author: '作者 C',
    time: '2023-03-01',
  },
  {
    id: 4,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 4',
    detail: '本文探讨了区块链技术在供应链管理中的应用，分析了其在提高透明度和效率方面的潜力。',
    author: '作者 D',
    time: '2023-04-01',
  },
  {
    id: 5,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 5',
    detail: '研究了人工智能在交通管理中的应用，提出了基于强化学习的智能交通信号控制系统设计。',
    author: '作者 E',
    time: '2023-05-01',
  },
  {
    id: 6,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 6',
    detail: '该论文分析了生成对抗网络（GAN）在图像生成中的最新进展，提出了改进GAN稳定性的方法。',
    author: '作者 F',
    time: '2023-06-01',
  },
  {
    id: 7,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 7',
    detail: '本文介绍了量子计算在密码学中的应用，探讨了量子密钥分发的安全性与效率。',
    author: '作者 G',
    time: '2023-07-01',
  },
  {
    id: 8,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 8',
    detail: '研究了深度强化学习在游戏AI中的应用，分析了AlphaZero框架在策略优化中的作用。',
    author: '作者 H',
    time: '2023-08-01',
  },
  {
    id: 9,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 9',
    detail: '该论文讨论了车联网（IoV）的概念，提出了基于5G技术的智能交通系统架构。',
    author: '作者 I',
    time: '2023-09-01',
  },
  {
    id: 10,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 10',
    detail: '本文介绍了信息检索技术的最新进展，重点是基于深度学习的文本嵌入模型。',
    author: '作者 J',
    time: '2023-10-01',
  },
  {
    id: 11,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 11',
    detail: '研究了云计算中的资源调度算法，提出了基于强化学习的动态资源分配策略。',
    author: '作者 K',
    time: '2023-11-01',
  },
  {
    id: 12,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 12',
    detail: '该论文探讨了智能家居系统中的用户行为分析，提出了基于机器学习的个性化推荐算法。',
    author: '作者 L',
    time: '2023-12-01',
  },
  {
    id: 13,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 13',
    detail: '本文介绍了大数据技术在金融风控中的应用，提出了基于图神经网络的风险评估模型。',
    author: '作者 M',
    time: '2023-12-15',
  },
  {
    id: 14,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 14',
    detail: '研究了语音识别技术中的噪声消除算法，提出了基于深度学习的鲁棒性改进方法。',
    author: '作者 N',
    time: '2023-08-15',
  },
  {
    id: 15,
    file: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 15',
    detail: '该论文分析了无人机在物流配送中的应用，提出了基于路径规划算法的优化方案。',
    author: '作者 O',
    time: '2023-05-15',
  },
]);
const useMock = false;
//获取论文
export const GetsearchPapers = async (...params)=>{
  console.log('获取论文信息API：',params)

  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data": {
        "total":papers.value.length,
        "rows":papers.value
      }
    }
  }
  return service.get('/api/search/papers/list',{
    ...params
  })
}

//获取研究方向

const modules = ref([
    {
      title: '服务推荐',
      images: [
        { src: '../assets/images/1.jpg', time: '2023-01-01', name: '项目1' },
        { src: 'path/to/image2.png', time: '2023-02-01', name: '项目2' },
        { src: 'path/to/image3.png', time: '2023-03-01', name: '项目3' },
        { src: 'path/to/image4.png', time: '2023-04-01', name: '项目4' },
        { src: 'path/to/image5.png', time: '2023-05-01', name: '项目5' },
        { src: 'path/to/image6.png', time: '2023-06-01', name: '项目6' },
      ],
      details: {
        '1': { visible: true, content: '服务推荐详情 1' },
        '2': { visible: false, content: '服务推荐详情 2' },
        '3': { visible: false, content: '服务推荐详情 3' },
        '4': { visible: false, content: '服务推荐详情 4' },
        '5': { visible: false, content: '服务推荐详情 5' },
        '6': { visible: false, content: '服务推荐详情 6' },
      },
    },
    {
      title: '自然语言处理',
      images: [
        { src: 'path/to/image7.png', time: '2023-07-01', name: '项目7' },
        { src: 'path/to/image8.png', time: '2023-08-01', name: '项目8' },
        { src: 'path/to/image9.png', time: '2023-09-01', name: '项目9' },
        { src: 'path/to/image10.png', time: '2023-10-01', name: '项目10' },
        { src: 'path/to/image11.png', time: '2023-11-01', name: '项目11' },
        { src: 'path/to/image12.png', time: '2023-12-01', name: '项目12' },
      ],
      details: {
        '1': { visible: true, content: '自然语言处理详情 1' },
        '2': { visible: false, content: '自然语言处理详情 2' },
        '3': { visible: false, content: '自然语言处理详情 3' },
        '4': { visible: false, content: '自然语言处理详情 4' },
        '5': { visible: false, content: '自然语言处理详情 5' },
        '6': { visible: false, content: '自然语言处理详情 6' },
      },
    },
    {
      title: '图像处理',
      images: [
        { src: 'path/to/image13.png', time: '2023-01-15', name: '项目13' },
        { src: 'path/to/image14.png', time: '2023-02-15', name: '项目14' },
        { src: 'path/to/image15.png', time: '2023-03-15', name: '项目15' },
        { src: 'path/to/image16.png', time: '2023-04-15', name: '项目16' },
        { src: 'path/to/image17.png', time: '2023-05-15', name: '项目17' },
        { src: 'path/to/image18.png', time: '2023-06-15', name: '项目18' },
      ],
      details: {
        '1': { visible: true, content: '图像处理详情 1' },
        '2': { visible: false, content: '图像处理详情 2' },
        '3': { visible: false, content: '图像处理详情 3' },
        '4': { visible: false, content: '图像处理详情 4' },
        '5': { visible: false, content: '图像处理详情 5' },
        '6': { visible: false, content: '图像处理详情 6' },
      },
    },
  ]);

export const GetSearchProjects = async (id)=>{
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data":modules.value[id-1]
    }
  }
  return service.get('/api/search',{
    id
  })
}


//学生获奖信息
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

export const GetAwards = async ()=>{
  if(useMock){
    return {
      "code": 1,
      "message": "success",
      "data": awardList
    }
  }
  return service.get('/api/search/awards')
}
