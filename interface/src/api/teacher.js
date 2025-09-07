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
  },
  {
    id: 7,
    name: '孙八7',
    title: '数据分析师',
    email: 'sunba@example.com',
    avatar: 'https://picsum.photos/id/1062/200/200',
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

//详情
const mockTeacherMap = {
  1: { id: 1, name: '张三', title: '前端开发工程师', email: 'zhangshan@example.com', avatar: 'https://picsum.photos/id/1005/400/400', intro: '专注 Web 前端、Vue、React 技术栈。' },
  2: { id: 2, name: '李四', title: '后端开发工程师', email: 'lisi@example.com', avatar: 'https://picsum.photos/id/1012/400/400', intro: '深耕 Java、Spring、微服务架构。' },
  3: { id: 3, name: '王五', title: '产品经理', email: 'wangwu@example.com', avatar: 'https://picsum.photos/id/1027/400/400', intro: '负责需求分析与产品生命周期管理。' },
  4: { id: 4, name: '赵六', title: 'UI设计师', email: 'zhaoliu@example.com', avatar: 'https://picsum.photos/id/1025/400/400', intro: '擅长 Figma、Sketch、Design System。' },
  5: { id: 5, name: '钱七', title: '测试工程师', email: 'qianqi@example.com', avatar: 'https://picsum.photos/id/1074/400/400', intro: '自动化测试、性能测试专家。' },
  6: { id: 6, name: '孙八', title: '数据分析师', email: 'sunba@example.com', avatar: 'https://picsum.photos/id/1062/400/400', intro: 'Python、SQL、数据可视化一把梭。' }
}

export const getTeacherDetail = async (id) => {
  const useMock ='true';
   if (useMock) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        const item = mockTeacherMap[id];
        if (!item) return reject(new Error('教师不存在'));
        resolve({ data: { code: 0, message: 'success', data: item } });
      }, 300);
    });
  }
   return service
    .get(`/teacher/${id}`)          // 路径参数
    .then(res => ({ data: res.data })) // 保持和 mock 同一外层格式
    .catch(err => {
      // 把后端错误消息透传出去，组件里 catch 到即可
      throw new Error(err?.response?.data?.message || '获取教师详情失败');
    });
}

