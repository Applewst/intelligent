import request from "./request"

// 模拟毕业生数据
const mockGraduates = [
  {
    id: 1,
    name: "张伟",
    gender: 1,
    avatar: "https://i.pravatar.cc/150?img=1",
  },
  {
    id: 2,
    name: "李娜",
    gender: 2,
    avatar: "https://i.pravatar.cc/150?img=5",
  },
  {
    id: 3,
    name: "王强",
    gender: 1,
    avatar: "https://i.pravatar.cc/150?img=3",
  },
  {
    id: 4,
    name: "刘芳",
    gender: 2,
    avatar: "https://i.pravatar.cc/150?img=10",
  },
  {
    id: 5,
    name: "陈明",
    gender: 1,
    avatar: "https://i.pravatar.cc/150?img=7",
  },
  {
    id: 6,
    name: "赵丽",
    gender: 2,
    avatar: "https://i.pravatar.cc/150?img=9",
  },
  {
    id: 7,
    name: "孙浩",
    gender: 1,
    avatar: "https://i.pravatar.cc/150?img=8",
  },
  {
    id: 8,
    name: "周敏",
    gender: 2,
    avatar: "https://i.pravatar.cc/150?img=20",
  },
]

// 是否使用模拟数据
const useMock = true

// 模拟延迟
const delay = (ms) => new Promise((resolve) => setTimeout(resolve, ms))

/**
 * 获取毕业生列表
 * @param {Object} params - 查询参数
 * @param {string} params.name - 毕业生姓名（可选）
 * @param {number} params.page - 页码
 * @param {number} params.size - 每页数量
 */
export function getGraduateList(params) {
  if (useMock) {
    return delay(500).then(() => {
      let filteredData = [...mockGraduates]

      // 按姓名筛选
      if (params.name) {
        filteredData = filteredData.filter((item) => item.name.includes(params.name))
      }

      // 分页
      const start = (params.page - 1) * params.size
      const end = start + params.size
      const list = filteredData.slice(start, end)

      return {
        code: 200,
        data: {
          list,
          total: filteredData.length,
        },
      }
    })
  }

  return request({
    url: "/graduate/list",
    method: "get",
    params,
  })
}

/**
 * 添加毕业生
 * @param {Object} data - 毕业生信息
 */
export function addGraduate(data) {
  if (useMock) {
    return delay(500).then(() => {
      const newGraduate = {
        id: mockGraduates.length + 1,
        ...data,
      }
      mockGraduates.push(newGraduate)
      return {
        code: 200,
        message: "添加成功",
      }
    })
  }

  return request({
    url: "/graduate/add",
    method: "post",
    data,
  })
}

/**
 * 编辑毕业生
 * @param {Object} data - 毕业生信息
 */
export function updateGraduate(data) {
  if (useMock) {
    return delay(500).then(() => {
      const index = mockGraduates.findIndex((item) => item.id === data.id)
      if (index !== -1) {
        mockGraduates[index] = { ...mockGraduates[index], ...data }
      }
      return {
        code: 200,
        message: "更新成功",
      }
    })
  }

  return request({
    url: "/graduate/update",
    method: "put",
    data,
  })
}

/**
 * 删除毕业生
 * @param {Object} params - 查询参数
 * @param {number} params.id - 毕业生ID
 */
export function deleteGraduate(params) {
  if (useMock) {
    return delay(500).then(() => {
      const index = mockGraduates.findIndex((item) => item.id === params.id)
      if (index !== -1) {
        mockGraduates.splice(index, 1)
      }
      return {
        code: 200,
        message: "删除成功",
      }
    })
  }

  return request({
    url: "/graduate/delete",
    method: "delete",
    params,
  })
}
