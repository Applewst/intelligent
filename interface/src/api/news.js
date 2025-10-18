import service from './request';

// 模拟产品数据（用于本地测试）
const mocknewData = [
  {
    id: 1,
    qrCode: 'https://picsum.photos/seed/new1/100/100', // 稳定的占位图
    title: 'LAB 智能分析系统',
    desc: '高精度数据分析工具，支持多维度数据可视化，123456789123456789',
    time: '05 八月，2025',
  },
  {
    id: 2,
    qrCode: 'https://picsum.photos/seed/new2/100/100',
    title: 'SEED9 种子培育系统',
    desc: '农业种子优化培育方案，提高发芽率30%',
    time: '12 九月，2025',
  },
  {
    id: 3,
    qrCode: 'https://picsum.photos/seed/new3/100/100',
    title: 'X BLOCK PRO 区块链模块',
    desc: '企业级区块链解决方案，支持智能合约部署',
    time: '20 九月，2025',
  },
  {
    id: 4,
    qrCode: 'https://picsum.photos/seed/new4/100/100',
    title: 'LAB 数据采集终端',
    desc: '物联网数据采集设备，低功耗长续航设计',
    time: '02 十月，2025',
  }
];

// 获取产品列表（支持模拟数据）
export const getnewList = async (params = {}) => {
  // 本地开发时使用模拟数据（可通过环境变量控制）
  const useMock = import.meta.env.VITE_USE_MOCK === 'true' || !import.meta.env.VITE_API_BASE_URL;

  if (useMock) {
    // 模拟网络延迟
    await new Promise(resolve => setTimeout(resolve, 800));

    // 支持根据参数筛选模拟数据（如按分类筛选）
    if (params.category && params.category !== 'all') {
      return {
        code: 0, // 匹配request.js中约定的成功状态码
        data: mocknewData.filter(item => item.category === params.category)
      };
    }
    return {
      code: 0,
      data: mocknewData
    };
  }

  // 使用统一配置的service调用真实接口
  return service({
    url: 'api/news',
    method: 'get',
    params: params
  });
};


// 获取全部科研动态（含本地 mock）
export const getAllNews = async (params = {}) => {
  const useMock = import.meta.env.VITE_USE_MOCK === 'true' || !import.meta.env.VITE_API_BASE_URL;

  if (useMock) {
    // 模拟网络延迟
    await new Promise(resolve => setTimeout(resolve, 800));
    return {
      code: 0,
      data: mocknewData          // 直接返回全部 4 条
    };
  }

  // 生产环境调用真实接口
  return service({
    url: 'api/news/all',
    method: 'get',
    params:params
  });
};
