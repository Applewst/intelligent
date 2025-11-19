import request from './request';

// =================== 静态模拟数据 ===================
// 这是一个固定的用户列表，每次调用都会返回完全相同的数据
const mockUserData = {
  code: 0,
  message: 'success',
  data: {
    total: 5, // 总条数
    list: [
      { id: 1, username: '张三', identity: '管理员'},
      { id: 2, username: '李四', identity: '普通用户'},
      { id: 3, username: '王五', identity: '普通用户'},
      { id: 4, username: '赵六', identity: '普通用户'},
      { id: 5, username: '孙七', identity: '普通用户'},
    ],
    pageNum: 1,
    pageSize: 10
  }
};

// 是否启用模拟数据 (true: 使用模拟数据; false: 使用真实API)
const USE_MOCK = false; // 直接硬编码为 true

// =================== API函数 ===================

/**
 * 获取用户列表
 * @param {Object} params - 查询参数 (模拟数据会忽略此参数)
 */
export function getUserList(params) {
  if (USE_MOCK) {
    console.warn('%c使用静态模拟数据获取用户列表', 'color: #ff7300');
    // 直接返回一个 resolved Promise，包含我们的静态数据    
    return Promise.resolve(mockUserData);
  }
  
  // 当 USE_MOCK 为 false 时，才会执行真实的 API 请求
  return request({
    url: '/user/list',
    method: 'get',
    params
  });
}

/**
 * 新增用户 (模拟实现)
 */
export function addUser(data) {
  if (USE_MOCK) {
    console.warn('%c使用模拟数据新增用户', 'color: #ff7300');
    return Promise.resolve({
      code: 0,
      message: '用户创建成功 (模拟数据)'
    });
  }
  
  return request({
    url: '/user/register',
    method: 'post',
    data
  });
}

/**
 * 更新用户 (模拟实现)
 */
export function updateUser(data) {
  if (USE_MOCK) {
    console.warn('%c使用模拟数据更新用户', 'color: #ff7300');
    return Promise.resolve({
      code: 0,
      message: '用户更新成功 (模拟数据)'
    });
  }
  
  return request({
    url: "/user",
    method: 'put',
    data
  });
}

/**
 * 删除用户 (模拟实现)
 */
export function deleteUser(id) {
  if (USE_MOCK) {
    console.warn('%c使用模拟数据删除用户', 'color: #ff7300');
    return Promise.resolve({
      code: 0,
      message: '用户删除成功 (模拟数据)'
    });
  }
  
  return request({
    url: "/user",
    method: 'delete',
    params: { id } 
  });
}