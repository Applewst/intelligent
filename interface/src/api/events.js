import service from './request'

/* ============== 模拟数据 ============== */
const mockEventData = [
  {
    id: 1,
    title: '香山登高',
    detail: '秋风起，漫山红叶，小伙伴们一起登顶鬼见愁！',
    time: '2025-09-20',
    image: 'https://picsum.photos/seed/xiangshan/400/250'
  },
  {
    id: 2,
    title: '密室逃脱',
    detail: '惊险刺激，60 分钟极限烧脑，全员成功通关。',
    time: '2025-09-05',
    image: 'https://picsum.photos/seed/mishi/400/300'
  },
  {
    id: 3,
    title: '公司年会',
    detail: '2024 总结 & 2025 展望，抽奖拿 iPhone！',
    time: '2025-08-28',
    image: 'https://picsum.photos/seed/annual/400/200'
  },
  {
    id: 4,
    title: '端午包粽',
    detail: '行政小姐姐手把手教学，成果可带走~',
    time: '2025-06-18',
    image: 'https://picsum.photos/seed/zongzi/400/250'
  },
  {
    id: 5,
    title: '端午包粽',
    detail: '行政小姐姐手把手教学，成果可带走~',
    time: '2025-06-18',
    image: 'https://picsum.photos/seed/zongzi/400/250'
  }
]

/* ============== 获取团队活动列表 ============== */
// <CHANGE> 修改函数以支持分页参数
export const getEventList = async (params = {}) => {
  const useMock = import.meta.env.VITE_USE_MOCK === 'true' || !import.meta.env.VITE_API_BASE_URL

  if (useMock) {
    await new Promise(resolve => setTimeout(resolve, 600)) // 模拟延迟

    // <CHANGE> 模拟分页逻辑
    const { pageNum = 1, pageSize = 10, name = '' } = params
    let filteredData = mockEventData

    // 如果name不为空，进行过滤
    if (name) {
      filteredData = mockEventData.filter(v => v.title.includes(name))
    }

    // 可按年份/月份筛选
    if (params.year) {
      filteredData = filteredData.filter(v => v.time.startsWith(params.year))
    }

    // 分页处理
    const start = (pageNum - 1) * pageSize
    const end = start + pageSize
    const pagedData = filteredData.slice(start, end)

    return {
      code: 0,
      data: pagedData,
      total: filteredData.length
    }
  }

  // <CHANGE> 生产环境，传递完整参数
  return service({
    url: '/event/list',
    method: 'get',
    params: params
  })
}
