import request from "./request.js"

/**
 * 获取科研联系信息
 * @returns {Promise} 返回科研联系信息
 */
export function getContactInfo() {
  // 真实接口
  return request({
    url: "/contact",
    method: "get",
  })

  // 模拟数据（调试时取消下面注释）
  // return new Promise((resolve) => {
  //   setTimeout(() => {
  //     resolve({
  //       code: 200,
  //       data: {
  //         email: "research@university.edu.cn",
  //         location: "北京市海淀区中关村大街1号",
  //         locationImage: "https://via.placeholder.com/400x300?text=Research+Location",
  //         wechatAccount: "科研创新平台",
  //         wechatQrCode: "https://via.placeholder.com/200x200?text=WeChat+QR",
  //       },
  //       message: "获取成功",
  //     })
  //   }, 500)
  // })
}

/**
 * 更新科研联系信息
 * @param {Object} data - 科研联系信息
 * @returns {Promise} 返回更新结果
 */
export function updateContactInfo(data) {
  // 真实接口
  return request({
    url: "/contact",
    method: "put",
    data,
  })

  // 模拟数据（调试时取消下面注释）
  // return new Promise((resolve) => {
  //   setTimeout(() => {
  //     console.log("更新的数据：", data)
  //     resolve({
  //       code: 200,
  //       data: data,
  //       message: "更新成功",
  //     })
  //   }, 800)
  // })
}

/**
 * 上传图片
 * @param {File} file - 图片文件
 * @returns {Promise} 返回图片URL
 */
export function uploadImage(file) {
  // 真实接口
  const formData = new FormData()
  formData.append("file", file)
  return request({
    url: "/upload",
    method: "post",
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  })

  // 模拟上传（调试时取消下面注释）
  // return new Promise((resolve) => {
  //   setTimeout(() => {
  //     const url = URL.createObjectURL(file)
  //     resolve({
  //       code: 200,
  //       data: {
  //         url: url,
  //       },
  //       message: "上传成功",
  //     })
  //   }, 1000)
  // })
}
