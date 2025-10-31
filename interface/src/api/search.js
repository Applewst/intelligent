import service from '../utils/request';
import { ref } from "vue";
const papers = ref([
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 1',
    detail: '这是一篇关于机器学习算法优化的研究，详细探讨了深度学习模型的训练策略。',
    author: '作者 A',
    time: '2023-01-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 2',
    detail: '本文介绍了自然语言处理领域中的一种新型预训练模型，重点是多语言文本理解能力的提升。',
    author: '作者 B',
    time: '2023-02-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 3',
    detail: '研究了图像识别技术在医疗诊断中的应用，对比了卷积神经网络的不同架构对图像分类的影响。',
    author: '作者 C',
    time: '2023-03-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 4',
    detail: '本文探讨了区块链技术在供应链管理中的应用，分析了其在提高透明度和效率方面的潜力。',
    author: '作者 D',
    time: '2023-04-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 5',
    detail: '研究了人工智能在交通管理中的应用，提出了基于强化学习的智能交通信号控制系统设计。',
    author: '作者 E',
    time: '2023-05-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 6',
    detail: '该论文分析了生成对抗网络（GAN）在图像生成中的最新进展，提出了改进GAN稳定性的方法。',
    author: '作者 F',
    time: '2023-06-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 7',
    detail: '本文介绍了量子计算在密码学中的应用，探讨了量子密钥分发的安全性与效率。',
    author: '作者 G',
    time: '2023-07-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 8',
    detail: '研究了深度强化学习在游戏AI中的应用，分析了AlphaZero框架在策略优化中的作用。',
    author: '作者 H',
    time: '2023-08-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 9',
    detail: '该论文讨论了车联网（IoV）的概念，提出了基于5G技术的智能交通系统架构。',
    author: '作者 I',
    time: '2023-09-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 10',
    detail: '本文介绍了信息检索技术的最新进展，重点是基于深度学习的文本嵌入模型。',
    author: '作者 J',
    time: '2023-10-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 11',
    detail: '研究了云计算中的资源调度算法，提出了基于强化学习的动态资源分配策略。',
    author: '作者 K',
    time: '2023-11-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 12',
    detail: '该论文探讨了智能家居系统中的用户行为分析，提出了基于机器学习的个性化推荐算法。',
    author: '作者 L',
    time: '2023-12-01',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 13',
    detail: '本文介绍了大数据技术在金融风控中的应用，提出了基于图神经网络的风险评估模型。',
    author: '作者 M',
    time: '2023-12-15',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 14',
    detail: '研究了语音识别技术中的噪声消除算法，提出了基于深度学习的鲁棒性改进方法。',
    author: '作者 N',
    time: '2023-08-15',
  },
  {
    image: 'https://placeholder.com/300x200', // 替换为实际图片路径
    title: '论文标题 15',
    detail: '该论文分析了无人机在物流配送中的应用，提出了基于路径规划算法的优化方案。',
    author: '作者 O',
    time: '2023-05-15',
  },
]);

export const searchPapers = async ()=>{
  const useMock = true;
  if(useMock){
    return {
      "code": 1,
      "msg": "success",
      "data": papers.value
    }
  }
  return service.get('/api/search/papers',{
    
  })
}
 