import request from "./request" 
/**
 * 获取教师列表（分页）
 */
export function getTeacherList(params) {
  return request({
    url: "/teacher/list",
    method: "get",
    params,
  })
}

/**
 * 添加教师
 */
export function addTeacher(data) {
  return request({
    url: "/teacher",
    method: "post",
    data,
  })
}

/**
 * 更新教师信息
 */
export function updateTeacher(data) {
  return request({
    url: "/teacher",
    method: "put",
    data,
  })
}

/**
 * 删除教师
 */
export function deleteTeacher(id) {
  return request({
    url: '/teacher',
    method: 'delete',
    params: { id } 
  })
}