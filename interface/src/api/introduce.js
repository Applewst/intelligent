import request from "./request.js"

// 模拟数据开关（开发时设置为true，生产环境设置为false）
const USE_MOCK_DATA = false

// 模拟数据
const mockTeamData = {

  "code": 200,
  "message": "请求成功",
  "data":{
        teamName: "技术创新团队",
        introduction:
          "我们是一支充满激情和创造力的技术团队，致力于为用户提供最优质的产品和服务。团队成员来自不同背景，拥有丰富的行业经验。",
        imageUrl: "https://cdn.pixabay.com/photo/2018/05/27/05/21/plum-blossom-3432877_1280.jpg",
    }
}

// 模拟延迟（模拟网络请求）
const mockDelay = (ms = 500) => new Promise((resolve) => setTimeout(resolve, ms))

/**
 * 获取团队介绍信息
 */
export const getTeamIntroduction = async () => {
  // 如果开启模拟数据，直接返回
  if (USE_MOCK_DATA) {
    await mockDelay()
    return mockTeamData
  }

  // 实际API调用
  return request({
    url: "/introduce",
    method: "get",
  })
}
