import service from "./request"

/**
 * 资源共享 API 接口
 */
export const resourceApi = {
  /**
   * 获取资源列表（分页）
   * @param {Object} params - 查询参数
   * @param {number} params.page - 当前页码
   * @param {number} params.pageSize - 每页数量
   * @param {string} params.keyword - 搜索关键词（可选）
   * @returns {Promise}
   */
  getResourceList(params) {
    return service({
      url: "/api/resources",
      method: "get",
      params,
    })
  },

  /**
   * 下载资源
   * @param {number} id - 资源 ID
   * @returns {Promise}
   */
  downloadResource(id) {
    return service({
      url: `/resources/${id}/download`,
      method: "get",
      responseType: "blob", // 重要：设置响应类型为 blob
    }).then((res) => {
      // 由于响应拦截器会返回 res，这里直接返回完整响应
      return res
    })
  },
}

/**
 * 触发浏览器下载文件
 * @param {Blob} blob - 文件 blob 对象
 * @param {string} filename - 文件名
 */
export const downloadFile = (blob, filename) => {
  const url = window.URL.createObjectURL(blob)
  const link = document.createElement("a")
  link.href = url
  link.download = filename
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  window.URL.revokeObjectURL(url)
}

export default resourceApi
