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
  //         // 注意：这里模拟的是URL，真实场景下，编辑时后端返回的也应该是图片URL
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
 * @param {Object} data - 科研联系信息 (包含Base64字符串)
 * @returns {Promise} 返回更新结果
 */
export function updateContactInfo(data) {
  // 真实接口
  return request({
    url: "/contact",
    method: "put",
    data, // 直接发送包含Base64的对象
  })

  // 模拟数据（调试时取消下面注释）
  // return new Promise((resolve) => {
  //   setTimeout(() => {
  //     console.log("更新的数据：", data);
  //     // 模拟后端处理成功后，返回新的图片URL
  //     const mockResponseData = {
  //       ...data,
  //       locationImage: "https://via.placeholder.com/400x300?text=New+Location",
  //       wechatQrCode: "https://via.placeholder.com/200x200?text=New+QR",
  //     };
  //     resolve({
  //       code: 200,
  //       data: mockResponseData,
  //       message: "更新成功",
  //     })
  //   }, 800)
  // })
}

// 删除 uploadImage 函数，因为不再需要它了