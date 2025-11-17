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
      url: "/resources",
      method: "get",
      params,
    })
  },

  /**
   * 下载资源
   * @param {number} id - 资源 ID
   * @returns {Promise<{blob: Blob, filename: string}>} 返回一个包含 blob 对象和文件名的 Promise
   */
  downloadResource(id) {
    return service({
      url: `/resources/${id}/download`,
      method: "get",
      responseType: "blob", // 关键：告诉 axios 和拦截器这是一个 blob 请求
    }).then((response) => {
      // 现在 response 是原始的 axios 响应对象
      const blob = response.data; // 这是 blob 数据

      // （推荐）从响应头中提取文件名
      let filename = `文件${id}`; // 默认文件名
      const disposition = response.headers['content-disposition'];
      if (disposition) {
        const match = disposition.match(/filename="?([^";]+)"?/);
        if (match && match[1]) {
          filename = decodeURIComponent(match[1]); // 解码文件名，防止中文乱码
        }
      }

      // 返回一个对象，包含 blob 和提取出的文件名
      return { blob, filename };
    });
  },
}

/**
 * 触发浏览器下载文件
 * @param {Blob} blob - 文件 blob 对象
 * @param {string} filename - 文件名
 */
export const downloadFile = (blob, filename) => {
  // 处理文件名可能包含的非法字符
  const safeFilename = filename.replace(/[\\/*?:"<>|]/g, "-");

  const url = window.URL.createObjectURL(blob)
  const link = document.createElement("a")
  link.href = url
  link.download = safeFilename
  document.body.appendChild(link)
  link.click()
  document.body.removeChild(link)
  window.URL.revokeObjectURL(url)
}

export default resourceApi
