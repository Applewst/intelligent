import service from './request'

/* ============== 模拟数据 ============== */
const mockEventData = [
  {
    id: 1,
    title: '香山登高',
    desc: '秋风起，漫山红叶，小伙伴们一起登顶鬼见愁！',
    date: '2025-09-20',
    img: 'https://picsum.photos/seed/xiangshan/400/250'
  },
  {
    id: 2,
    title: '密室逃脱',
    desc: '惊险刺激，60 分钟极限烧脑，全员成功通关。',
    date: '2025-09-05',
    img: 'https://picsum.photos/seed/mishi/400/300'
  },
  {
    id: 3,
    title: '公司年会',
    desc: '2024 总结 & 2025 展望，抽奖拿 iPhone！',
    date: '2025-08-28',
    img: 'https://picsum.photos/seed/annual/400/200'
  },
  {
    id: 4,
    title: '端午包粽',
    desc: '行政小姐姐手把手教学，成果可带走~',
    date: '2025-06-18',
    img: 'https://picsum.photos/seed/zongzi/400/250'
  },
  {
    id: 5,
    title: '端午包粽',
    desc: '行政小姐姐手把手教学，成果可带走~',
    date: '2025-06-18',
    img: 'https://picsum.photos/seed/zongzi/400/250'
  }
]

/* ============== 获取团队活动列表 ============== */
export const getEventList = async (params = {}) => {
  const useMock = import.meta.env.VITE_USE_MOCK === 'true' || !import.meta.env.VITE_API_BASE_URL

  if (useMock) {
    await new Promise(resolve => setTimeout(resolve, 600)) // 模拟延迟
    // 可按年份/月份筛选
    if (params.year) {
      return { code: 0, data: mockEventData.filter(v => v.date.startsWith(params.year)) }
    }
    return { code: 0, data: mockEventData }
  }

  // 生产环境
  return service({
    url: '/events',
    method: 'post',
    data: params
  })
}
