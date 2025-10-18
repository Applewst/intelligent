
import service from './request'

/* ------------- 零后端模拟数据 ------------- */
const mockGraduateData = [
  { id: 1, name: '1张三', grade: '2022', major: '计算机视觉',   avatar: 'https://picsum.photos/id/1005/260/330' },
  { id: 2, name: '1李四', grade: '2022', major: '自然语言处理', avatar: 'https://picsum.photos/id/1012/260/330' },
  { id: 3, name: '1王五', grade: '2023', major: '强化学习',     avatar: 'https://picsum.photos/id/1027/260/330' },
  { id: 4, name: '1赵六', grade: '2023', major: '图神经网络',   avatar: 'https://picsum.photos/id/1025/260/330' },
  { id: 5, name: '1孙七', grade: '2021', major: '边缘计算',     avatar: 'https://picsum.photos/id/1074/260/330' },
  { id: 6, name: '1周八', grade: '2021', major: '联邦学习',     avatar: 'https://picsum.photos/id/1062/260/330' },
  { id: 7, name: '1吴九', grade: '2022', major: '计算机图形学', avatar: 'https://picsum.photos/id/1083/260/330' },
  { id: 8, name: '1郑十', grade: '2023', major: '数据挖掘',     avatar: 'https://picsum.photos/id/1084/260/330' }
]

/* ------------- API 封装 ------------- */
export const getGraduateList = async (params = {}) => {
  const useMock = true   // 开发阶段 true，上线改为 false

  try {
    if (useMock) {
      // 模拟 300 ms 网络延迟
      return new Promise(resolve => {
        setTimeout(() => {
          resolve({
            data: {
              code: 0,
              message: 'success',
              data: { list: mockGraduateData, total: mockGraduateData.length }
            }
          })
        }, 300)
      })
    } else {
      // 真实后端接口
      const response = await service.get('/api/graduates', {params})
      return response
    }
  } catch (error) {
    console.error('获取毕业生列表失败：', error)
    throw new Error(error.message || '获取毕业生数据失败')
  }
}

/* ------------- 统一导出 ------------- */
export default { getGraduateList }
