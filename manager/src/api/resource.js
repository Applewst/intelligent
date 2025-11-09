import request from "./request.js"

// 模拟数据
const mockData = [
  {
    id: "1",
    name: "Vue3官方文档.pdf",
    size: "2.5MB",
    type: "PDF",
    uploadTime: "2024-01-15 10:30:00",
    downloads: "1250",
  },
  {
    id: "2",
    name: "ElementPlus组件库.zip",
    size: "15.8MB",
    type: "ZIP",
    uploadTime: "2024-01-18 14:20:00",
    downloads: "856",
  },
  {
    id: "3",
    name: "项目需求文档.docx",
    size: "3.2MB",
    type: "DOCX",
    uploadTime: "2024-01-20 09:15:00",
    downloads: "432",
  },
  {
    id: "4",
    name: "系统架构设计图.png",
    size: "1.8MB",
    type: "PNG",
    uploadTime: "2024-01-22 16:45:00",
    downloads: "678",
  },
  {
    id: "5",
    name: "API接口文档.md",
    size: "0.5MB",
    type: "MD",
    uploadTime: "2024-01-25 11:00:00",
    downloads: "921",
  },
  {
    id: "6",
    name: "数据库设计脚本.sql",
    size: "0.8MB",
    type: "SQL",
    uploadTime: "2024-01-28 13:30:00",
    downloads: "345",
  },
  {
    id: "7",
    name: "前端开发规范.pdf",
    size: "1.2MB",
    type: "PDF",
    uploadTime: "2024-02-01 10:00:00",
    downloads: "567",
  },
  {
    id: "8",
    name: "测试用例文档.xlsx",
    size: "2.1MB",
    type: "XLSX",
    uploadTime: "2024-02-05 15:20:00",
    downloads: "234",
  },
]

// 模拟分页和搜索
const mockRequest = (page, pageSize, keyword) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      let filteredData = [...mockData]

      // 关键字搜索
      if (keyword) {
        filteredData = filteredData.filter(
          (item) =>
            item.name.toLowerCase().includes(keyword.toLowerCase()) ||
            item.type.toLowerCase().includes(keyword.toLowerCase()),
        )
      }

      // 分页
      const total = filteredData.length
      const start = (page - 1) * pageSize
      const end = start + pageSize
      const list = filteredData.slice(start, end)

      resolve({
        code: 200,
        data: {
          list,
          total,
          page,
          pageSize,
        },
      })
    }, 300) // 模拟网络延迟
  })
}

/**
 * 获取资源列表
 * @param {number} page - 页码
 * @param {number} pageSize - 每页数量
 * @param {string} keyword - 搜索关键字
 */
export const getResourceList = (page, pageSize, keyword) => {
  return request({
    url: "/resources",
    method: "get",
    params: { page, pageSize, keyword },
  })

  // 模拟数据（开发调试使用，取消注释可切换）
  // return mockRequest(page, pageSize, keyword)
}

/**
 * 添加资源
 * @param {Object} data - 资源数据
 */
export const addResource = (data) => {
  return request({
    url: "/resources",
    method: "post",
    data,
  })

  // 模拟添加（开发调试使用，取消注释可切换）
  // return new Promise((resolve) => {
  //   setTimeout(() => {
  //     const newResource = {
  //       ...data,
  //       id: Date.now().toString(),
  //       uploadTime: new Date().toLocaleString("zh-CN"),
  //       downloads: "0",
  //     }
  //     mockData.unshift(newResource)
  //     resolve({
  //       code: 200,
  //       message: "添加成功",
  //     })
  //   }, 300)
  // })
}

/**
 * 更新资源
 * @param {string} id - 资源ID
 * @param {Object} data - 资源数据
 */
export const updateResource = (id, data) => {
  return request({
    url: `/resources/${id}`,
    method: "put",
    data,
  })

  // 模拟更新（开发调试使用，取消注释可切换）
  // return new Promise((resolve) => {
  //   setTimeout(() => {
  //     const index = mockData.findIndex((item) => item.id === id)
  //     if (index !== -1) {
  //       mockData[index] = { ...mockData[index], ...data }
  //     }
  //     resolve({
  //       code: 200,
  //       message: "更新成功",
  //     })
  //   }, 300)
  // })
}

/**
 * 删除资源
 * @param {string} id - 资源ID
 */
export const deleteResource = (id) => {
  return request({
    url: `/resources/${id}`,
    method: "delete",
  })

  // 模拟删除（开发调试使用，取消注释可切换）
  // return new Promise((resolve) => {
  //   setTimeout(() => {
  //     const index = mockData.findIndex((item) => item.id === id)
  //     if (index !== -1) {
  //       mockData.splice(index, 1)
  //     }
  //     resolve({
  //       code: 200,
  //       message: "删除成功",
  //     })
  //   }, 300)
  // })
}
