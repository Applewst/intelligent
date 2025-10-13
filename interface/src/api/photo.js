
import service from './request';

// 切换模式：true-使用模拟数据，false-使用真实AJAX请求
const USE_MOCK_DATA = true;


export const getPhotoWallImages = async () => {
  // 使用模拟数据模式
  if (USE_MOCK_DATA) {
    return new Promise((resolve) => {
      // 模拟网络延迟
      setTimeout(() => {
        // 模拟后端返回的JSON结构
        const mockResponse = {
          code: 0,
          message: 'success',
          data: [
            { url: 'https://picsum.photos/id/10/800/600' },
            { url: 'https://picsum.photos/id/15/800/600' },
            { url: 'https://picsum.photos/id/29/800/600' },
            { url: 'https://picsum.photos/id/42/800/600' },
            { url: 'https://picsum.photos/id/65/800/600' },
            { url: 'https://picsum.photos/id/76/800/600' },
          ]
        };

        // 模拟真实接口的数据解析逻辑
        if (mockResponse.code === 0) {
          resolve(mockResponse.data);
        } else {
          ElMessage.error(mockResponse.message || '模拟数据返回错误');
          resolve(getDefaultPhotos());
        }
      }, 600); // 模拟600ms网络延迟
    });
  }
  // 使用真实AJAX请求模式
  else {
    try {
      // 真实向后端发送POST请求
      const response = await service.get('/photos');

      // 真实接口数据处理
      if (response && response.data && response.data.code === 0) {
        return response.data.data || [];
      }

      ElMessage.warning('接口返回格式异常');
      return getDefaultPhotos();
    } catch (error) {
      console.error('AJAX请求失败:', error);
      ElMessage.error('获取照片失败，使用默认数据');
      return getDefaultPhotos();
    }
  }
};

/**
 * 获取默认照片（降级方案）
 */
const getDefaultPhotos = () => [
  { url: 'https://picsum.photos/id/10/800/600' },
  { url: 'https://picsum.photos/id/15/800/600' },
  { url: 'https://picsum.photos/id/29/800/600' },
];
