import service from './request';

// 模拟产品数据（用于本地测试）
const mockNewsData = [
  { id: 1, image: 'https://picsum.photos/seed/new1/100/100', title: 'LAB 智能分析系统 V1', detail: '高精度数据分析工具，支持多维度数据可视化。', time: '05 八月，2025', category: 'tech' },
  { id: 2, image: 'https://picsum.photos/seed/new2/100/100', title: 'SEED9 种子培育系统', detail: '农业种子优化培育方案，提高发芽率30%。', time: '12 九月，2025', category: 'agri' },
  { id: 3, image: 'https://picsum.photos/seed/new3/100/100', title: 'X BLOCK PRO 区块链模块', detail: '企业级区块链解决方案，支持智能合约部署。', time: '20 九月，2025', category: 'tech' },
  { id: 4, image: 'https://picsum.photos/seed/new4/100/100', title: 'LAB 数据采集终端', detail: '物联网数据采集设备，低功耗长续航设计。', time: '02 十月，2025', category: 'iot' },
  { id: 5, image: 'https://picsum.photos/seed/new5/100/100', title: '新型AI助手发布', detail: '基于大语言模型的智能助手，提升工作效率。', time: '15 十月，2025', category: 'tech' },
  { id: 6, image: 'https://picsum.photos/seed/new6/100/100', title: '绿色能源新突破', detail: '高效太阳能电池技术，转换效率提升至25%。', time: '22 十月，2025', category: 'energy' },
];

/**
 * 获取新闻列表（统一接口，支持分页和模拟数据）
 * @param {Object} params - 查询参数
 * @param {number} params.pageNum - 当前页码
 * @param {number} params.pageSize - 每页数量
 * @param {string} params.category - 分类筛选 (可选)
 */
export const getNewsList = async (params = {}) => {
  const { pageNum = 1, pageSize = 10, category } = params;
  const useMock = false

  if (useMock) {
    // 模拟网络延迟
    await new Promise(resolve => setTimeout(resolve, 500));

    // 1. 先进行分类筛选
    let filteredData = [...mockNewsData];
    if (category && category !== 'all') {
      filteredData = filteredData.filter(item => item.category === category);
    }

    // 2. 再进行分页处理
    const total = filteredData.length;
    const startIndex = (pageNum - 1) * pageSize;
    const paginatedData = filteredData.slice(startIndex, startIndex + pageSize);

    // 3. 返回符合后端格式的分页数据
    return {
      code: 0,
      data: {
        list: paginatedData,
        total: total,
        pageNum: pageNum,
        pageSize: pageSize,
      }
    };
  }

  // 生产环境调用真实接口
  return service({
    url: '/new/list',
    method: 'get',
    params: params
  });
};
