import service from './request';

// 模拟项目研究方向数据（固定5个）
const mockProjectData = [
  {
    id: 1,
    imageUrl: 'https://picsum.photos/seed/proj1/200/200',
    title: '服务推荐',
  },
  {
    id: 2,
    imageUrl: 'https://picsum.photos/seed/proj2/200/200',
    title: '自然语言，大模型',
  },
  {
    id: 3,
    imageUrl: 'https://picsum.photos/seed/proj3/200/200',
    title: '图像处理，计算机视觉',
  },

];

export const getProjectList = async (params = {}) => {
  // 解构参数，设置默认值
  const { category = 'all' } = params;

  // 本地开发时使用模拟数据（与new.js保持一致的逻辑）
  const useMock = import.meta.env.VITE_USE_MOCK === 'true' || !import.meta.env.VITE_API_BASE_URL;

  if (useMock) {
    // 模拟网络延迟，与new.js保持一致
    await new Promise(resolve => setTimeout(resolve, 800));

    // 筛选数据（如需按分类筛选）
    let filteredProjects = mockProjectData;
    if (category !== 'all') {
      filteredProjects = mockProjectData.filter(item => item.category === category);
    }

    // 返回与new.js一致的格式
    return {
      code: 0,
      data: filteredProjects
    };
  }

  // 生产环境调用真实接口
  return service({
    url: '/projects', // 项目接口地址
    method: 'post',
    data: params // 传递参数
  });
};
