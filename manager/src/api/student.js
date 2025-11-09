import request from "./request"

// 模拟学生数据（用于本地测试）
const mockStudentData = [
  {
    id: 1,
    name: "张小明",
    grade: "大一",
    stucode: "2024001",
    gender: 1,
    avatar: "https://picsum.photos/id/1005/200/200",
  },
  {
    id: 2,
    name: "李小红",
    grade: "大二",
    stucode: "2023045",
    gender: 2,
    avatar: "https://picsum.photos/id/1027/200/200",
  },
  {
    id: 3,
    name: "王大伟",
    grade: "大三",
    stucode: "2022088",
    gender: 1,
    avatar: "https://picsum.photos/id/1074/200/200",
  },
  {
    id: 4,
    name: "刘芳芳",
    grade: "大四",
    stucode: "2021123",
    gender: 2,
    avatar: "https://picsum.photos/id/1062/200/200",
  },
  {
    id: 5,
    name: "陈浩然",
    grade: "大一",
    stucode: "2024015",
    gender: 1,
    avatar: "https://picsum.photos/id/1081/200/200",
  },
  {
    id: 6,
    name: "杨婷婷",
    grade: "大二",
    stucode: "2023067",
    gender: 2,
    avatar: "https://picsum.photos/id/1025/200/200",
  },
  {
    id: 7,
    name: "赵强强",
    grade: "大三",
    stucode: "2022099",
    gender: 1,
    avatar: "https://picsum.photos/id/1012/200/200",
  },
  {
    id: 8,
    name: "孙美美",
    grade: "大四",
    stucode: "2021156",
    gender: 2,
    avatar: "https://picsum.photos/id/1011/200/200",
  },
]

/**
 * 获取学生列表（分页）
 */
export function getStudentList(params = {}) {
  // 标记是否使用模拟数据（开发阶段可设为true，上线前改为false）
  const useMock = true

  try {
    if (useMock) {
      // 模拟网络请求：返回模拟数据（含延迟效果）
      return new Promise((resolve) => {
        setTimeout(() => {
          // 模拟搜索过滤
          let filteredData = [...mockStudentData]
          if (params.name) {
            filteredData = filteredData.filter((s) => s.name.includes(params.name))
          }
          if (params.stucode) {
            filteredData = filteredData.filter((s) => s.stucode.includes(params.stucode))
          }
          if (params.grade) {
            filteredData = filteredData.filter((s) => s.grade === params.grade)
          }

          // 模拟分页
          const pageNum = params.pageNum || 1
          const pageSize = params.pageSize || 10
          const start = (pageNum - 1) * pageSize
          const end = start + pageSize
          const list = filteredData.slice(start, end)

          resolve({
            data: {
              code: 0,
              message: "success",
              data: {
                list: list,
                total: filteredData.length,
              },
            },
          })
        }, 500) // 模拟500ms网络延迟
      })
    } else {
      // 真实接口请求
      return request({
        url: "/students/list",
        method: "get",
        params,
      })
    }
  } catch (error) {
    console.error("获取学生列表失败：", error)
    throw new Error(error.message || "获取学生数据失败")
  }
}

/**
 * 获取学生详情
 */
export function getStudentDetail(id) {
  const useMock = true

  if (useMock) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const item = mockStudentData.find((s) => s.id === Number.parseInt(id))
        if (!item) return reject(new Error("学生不存在"))
        resolve({
          data: {
            code: 0,
            message: "success",
            data: {
              ...item,
              avatar: item.avatar.replace("/200/200", "/400/400"), // 高清头像
            },
          },
        })
      }, 300)
    })
  }

  return request({
    url: `/student/${id}`,
    method: "get",
  })
}

/**
 * 添加学生
 */
export function addStudent(data) {
  return request({
    url: "/students",
    method: "post",
    data,
  })
}

/**
 * 更新学生信息
 */
export function updateStudent(id, data) {
  return request({
    url: `/students/${id}`,
    method: "put",
    data,
  })
}

/**
 * 删除学生
 */
export function deleteStudent(params) {
  return request({
    url: "/students",
    method: "delete",
    params,
  })
}