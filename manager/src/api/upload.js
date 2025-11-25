import request from "./request.js";

/**
 * 通用文件上传接口
 * @param {File} file - 上传的文件
 * @returns {Promise} 返回文件 URL
 */
export function uploadImage(file) {
  const formData = new FormData();
  formData.append("file", file);

  return request({
    url: "/upload", 
    method: "post",
    data: formData,
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}