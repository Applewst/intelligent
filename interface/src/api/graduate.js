import service from "./request"

/* ------------- 零后端模拟数据 ------------- */
const mockGraduateData = [
  { id: 1, name: "1张三", avatar: "https://picsum.photos/id/1005/260/330" },
  { id: 2, name: "1李四", avatar: "https://picsum.photos/id/1012/260/330" },
  { id: 3, name: "1王五", avatar: "https://picsum.photos/id/1027/260/330" },
  { id: 4, name: "1赵六", avatar: "https://picsum.photos/id/1025/260/330" },
  { id: 5, name: "1孙七", avatar: "https://picsum.photos/id/1074/260/330" },
  { id: 6, name: "1周八", avatar: "https://picsum.photos/id/1062/260/330" },
  { id: 7, name: "1吴九", avatar:  "https://picsum.photos/id/1083/260/330"},
  { id: 8, name: "1郑十", avatar: "https://picsum.photos/id/1084/260/330" },
]

/* ------------- API 封装 ------------- */
export const getGraduateList = async (params = { pageNum: 1, pageSize: 10, name: "" }) => {
  const useMock = true // 开发阶段 true，上线改为 false

  try {
    if (useMock) {
      return new Promise((resolve) => {
        setTimeout(() => {
          const { pageNum = 1, pageSize = 10 } = params
          const start = (pageNum - 1) * pageSize
          const end = start + pageSize
          const paginatedData = mockGraduateData.slice(start, end)

          resolve({
            data: {
              code: 0,
              message: "success",
              data: {
                list: paginatedData,
                total: mockGraduateData.length,
              },
            },
          })
        }, 300)
      })
    } else {
      const response = await service.get("/graduates", { params })
      return response
    }
  } catch (error) {
    console.error("获取毕业生列表失败：", error)
    throw new Error(error.message || "获取毕业生数据失败")
  }
}

/* ------------- 统一导出 ------------- */
export default { getGraduateList }
