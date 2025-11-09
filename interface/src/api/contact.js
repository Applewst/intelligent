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
