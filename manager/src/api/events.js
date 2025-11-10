// 活动管理API接口
import request from "./request.js"

// 获取活动列表
export async function getEventList(params) {
  try {
    const response = await request({
      url: "/event/list",
      method: "get",
      params: {
        pageNum: params.pageNum,
        pageSize: params.pageSize,
        name: params.name || "", 
      },
    })

    return response
  } catch (error) {
    console.error("获取活动列表错误:", error)
    // 返回模拟数据用于开发
    return generateMockData(params)
  }
}

// 创建活动
export async function createEvent(params) {
  try {
    const response = await request({
      url: "/events",
      method: "post",
      data: params,
    })

    return response
  } catch (error) {
    console.error("创建活动错误:", error)
    throw error
  }
}

// 更新活动
export async function updateEvent(params) {
  try {
    const response = await request({
      url: '/event',
      method: "put",
      data: params,
    })

    return response
  } catch (error) {
    console.error("更新活动错误:", error)
    throw error
  }
}

// 删除活动
export async function deleteEvent(id) {
  try {
    await request({
      url: '/events',
      method: "delete",
      params: { id } 
    })
  } catch (error) {
    console.error("删除活动错误:", error)
    throw error
  }
}

// 生成模拟数据
function generateMockData(params) {
  const mockEvents = [
    {
    id: 1,
    title: '香山登高',
    detail: '秋风起，漫山红叶，小伙伴们一起登顶鬼见愁！',
    time: '2025-09-20',
    Image: 'https://picsum.photos/seed/xiangshan/400/250'
  },
  {
    id: 2,
    title: '密室逃脱',
    detail: '惊险刺激，60 分钟极限烧脑，全员成功通关。',
    time: '2025-09-05',
    Image: 'https://picsum.photos/seed/mishi/400/300'
  },
  {
    id: 3,
    title: '公司年会',
    detail: '2024 总结 & 2025 展望，抽奖拿 iPhone！',
    time: '2025-08-28',
    Image: 'https://picsum.photos/seed/annual/400/200'
  },
  {
    id: 4,
    title: '端午包粽',
    detail: '行政小姐姐手把手教学，成果可带走~',
    time: '2025-06-18',
    Image: 'https://picsum.photos/seed/zongzi/400/250'
  },
  {
    id: 5,
    title: '端午包粽',
    detail: '行政小姐姐手把手教学，成果可带走~',
    time: '2025-06-18',
    Image: 'https://picsum.photos/seed/zongzi/400/250'
  }
  ]

  // 过滤数据
  let filteredEvents = mockEvents
  if (params.name) {
    filteredEvents = filteredEvents.filter((event) => event.title.toLowerCase().includes(params.name.toLowerCase()))
  }
  if (params.status) {
    filteredEvents = filteredEvents.filter((event) => event.status === params.status)
  }

  // 分页
  const start = (params.pageNum - 1) * params.pageSize
  const end = start + params.pageSize
  const paginatedEvents = filteredEvents.slice(start, end)

  return {
    total: filteredEvents.length,
    list: paginatedEvents,
  }
}
