import service from './request';

// 模拟教师数据（用于本地测试）
const mockTeacherData = [
  {
    id: 1,
    name: '张三',
    title: '前端开发工程师',
    email: 'zhangshan@example.com',
    avatar: 'https://picsum.photos/id/1005/200/200',
  },
  {
    id: 2,
    name: '李四',
    title: '后端开发工程师',
    email: 'lisi@example.com',
    avatar: 'https://picsum.photos/id/1012/200/200',
  },
  {
    id: 3,
    name: '王五',
    title: '产品经理',
    email: 'wangwu@example.com',
    avatar: 'https://picsum.photos/id/1027/200/200',
  },
  {
    id: 4,
    name: '赵六',
    title: 'UI设计师',
    email: 'zhaoliu@example.com',
    avatar: 'https://picsum.photos/id/1025/200/200',
  },
  {
    id: 5,
    name: '钱七',
    title: '测试工程师',
    email: 'qianqi@example.com',
    avatar: 'https://picsum.photos/id/1074/200/200',
  },
  {
    id: 6,
    name: '孙八',
    title: '数据分析师',
    email: 'sunba@example.com',
    avatar: 'https://picsum.photos/id/1062/200/200',
    tagType: 'danger',
  },
]

export const getTeacherList = async (params = {}) => {
  // 标记是否使用模拟数据（开发阶段可设为true，上线前改为false）
  const useMock = true;

  try {
    if (useMock) {
      // 模拟网络请求：返回模拟数据（含延迟效果）
      return new Promise((resolve) => {
        setTimeout(() => {
          resolve({
            data: {
              code: 0,
              message: 'success',
              data: {
                list: mockTeacherData,
                total: mockTeacherData.length
              }
            }
          });
        }, 500); // 模拟500ms网络延迟
      });
    } else {
      // 真实接口请求：调用service发送POST请求
      const response = await service.post('/teachers', params);
      return response; // 返回真实接口响应
    }
  } catch (error) {
    // 统一捕获请求错误（模拟数据模式下基本不会触发，真实接口可能需要处理）
    console.error('获取教师列表失败：', error);
    // 抛出错误，让调用方处理UI提示
    throw new Error(error.message || '获取教师数据失败');
  }
};
