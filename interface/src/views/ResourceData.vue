<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { resourceApi, downloadFile } from '@/api/resource'

// 搜索关键词
const searchKeyword = ref('')

// 加载状态
const loading = ref(false)

// 资源列表
const resourceList = ref([])

// 分页配置
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
})

// 获取资源列表
const getResourceList = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.currentPage,
      pageSize: pagination.pageSize,
      keyword: searchKeyword.value || undefined, // 为空时不传递
    }

    // 调用 API（这里使用模拟数据，实际使用时取消注释下面的代码）
    // const response = await resourceApi.getResourceList(params)
    // resourceList.value = response.data
    // pagination.total = response.total

    // 模拟数据（实际使用时删除这部分）
    const mockData = await mockGetResourceList(params)
    resourceList.value = mockData.data
    pagination.total = mockData.total
  } catch (error) {
    ElMessage.error('获取资源列表失败')
    console.error(error)
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.currentPage = 1
  getResourceList()
}

// 重置
const handleReset = () => {
  searchKeyword.value = ''
  pagination.currentPage = 1
  getResourceList()
}

// 下载资源
const handleDownload = async (row) => {
  // 设置当前行的下载状态
  row.downloading = true

  try {
    // 调用下载 API（实际使用时取消注释）
    // const blob = await resourceApi.downloadResource(row.id)
    // downloadFile(blob, row.name)

    // 模拟下载（实际使用时删除这部分）
    await mockDownloadResource(row.id)

    ElMessage.success('下载成功')

    // 更新下载次数
    row.downloads++
  } catch (error) {
    ElMessage.error('下载失败，请稍后重试')
    console.error(error)
  } finally {
    row.downloading = false
  }
}

// 分页大小改变
const handleSizeChange = (val) => {
  pagination.pageSize = val
  pagination.currentPage = 1
  getResourceList()
}

// 当前页改变
const handleCurrentChange = (val) => {
  pagination.currentPage = val
  getResourceList()
}

// 格式化文件大小
const formatFileSize = (bytes) => {
  if (!bytes) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB', 'TB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return (bytes / Math.pow(k, i)).toFixed(2) + ' ' + sizes[i]
}

// 获取文件类型标签颜色
const getFileTypeTag = (type) => {
  const typeMap = {
    PDF: 'danger',
    DOC: 'primary',
    DOCX: 'primary',
    XLS: 'success',
    XLSX: 'success',
    PPT: 'warning',
    PPTX: 'warning',
    ZIP: 'info',
    RAR: 'info',
    MP4: '',
    MP3: '',
  }
  return typeMap[type?.toUpperCase()] || ''
}

// ========== 模拟数据和方法（实际使用时删除） ==========

// 模拟获取资源列表
const mockGetResourceList = ({ page, pageSize, keyword }) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      const allData = [
        {
          id: 1,
          name: 'Vue3开发指南.pdf',
          size: 2048576,
          type: 'PDF',
          uploadTime: '2024-01-15 10:30:00',
          downloads: 128,
        },
        {
          id: 2,
          name: 'Element Plus组件库文档.docx',
          size: 1536000,
          type: 'DOCX',
          uploadTime: '2024-01-14 09:20:00',
          downloads: 95,
        },
        {
          id: 3,
          name: '项目需求文档.xlsx',
          size: 512000,
          type: 'XLSX',
          uploadTime: '2024-01-13 14:15:00',
          downloads: 67,
        },
        {
          id: 4,
          name: '产品演示PPT.pptx',
          size: 4096000,
          type: 'PPTX',
          uploadTime: '2024-01-12 16:45:00',
          downloads: 203,
        },
        {
          id: 5,
          name: '源代码压缩包.zip',
          size: 10485760,
          type: 'ZIP',
          uploadTime: '2024-01-11 11:00:00',
          downloads: 456,
        },
        {
          id: 6,
          name: 'API接口文档.pdf',
          size: 1024000,
          type: 'PDF',
          uploadTime: '2024-01-10 13:30:00',
          downloads: 189,
        },
        {
          id: 7,
          name: '数据库设计文档.doc',
          size: 768000,
          type: 'DOC',
          uploadTime: '2024-01-09 15:20:00',
          downloads: 142,
        },
        {
          id: 8,
          name: '测试报告.xlsx',
          size: 614400,
          type: 'XLSX',
          uploadTime: '2024-01-08 10:10:00',
          downloads: 78,
        },
        {
          id: 9,
          name: '培训视频.mp4',
          size: 52428800,
          type: 'MP4',
          uploadTime: '2024-01-07 09:00:00',
          downloads: 321,
        },
        {
          id: 10,
          name: '用户手册.pdf',
          size: 3072000,
          type: 'PDF',
          uploadTime: '2024-01-06 14:40:00',
          downloads: 267,
        },
        {
          id: 11,
          name: '技术方案.docx',
          size: 2048000,
          type: 'DOCX',
          uploadTime: '2024-01-05 11:25:00',
          downloads: 156,
        },
        {
          id: 12,
          name: '项目资料合集.rar',
          size: 15728640,
          type: 'RAR',
          uploadTime: '2024-01-04 16:30:00',
          downloads: 389,
        },
      ]

      // 根据关键词过滤
      let filteredData = allData
      if (keyword) {
        filteredData = allData.filter((item) =>
          item.name.toLowerCase().includes(keyword.toLowerCase())
        )
      }

      const total = filteredData.length
      const start = (page - 1) * pageSize
      const end = start + pageSize
      const data = filteredData.slice(start, end).map((item) => ({
        ...item,
        downloading: false,
      }))

      resolve({ data, total })
    }, 500)
  })
}

// 模拟下载资源
const mockDownloadResource = (id) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      console.log(`下载资源 ID: ${id}`)
      resolve()
    }, 1000)
  })
}

// 组件挂载时获取数据
onMounted(() => {
  getResourceList()
})
</script>

<template>
  <div class="resource-share">
    <!-- 搜索栏 -->
    <div class="search-bar">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入资源名称搜索"
        clearable
        style="width: 300px"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-button type="primary" :icon="Search" @click="handleSearch" style="margin-left: 10px">
        搜索
      </el-button>
      <el-button :icon="Refresh" @click="handleReset"> 重置 </el-button>
    </div>

    <!-- 资源列表 -->
    <div class="resource-list">
      <el-table :data="resourceList" v-loading="loading" border stripe style="width: 100%">
        <el-table-column type="index" label="序号" width="60" align="center" />

        <el-table-column prop="name" label="资源名称" min-width="200">
          <template #default="{ row }">
            <div class="resource-name">
              <el-icon class="file-icon"><Document /></el-icon>
              <span>{{ row.name }}</span>
            </div>
          </template>
        </el-table-column>

        <el-table-column prop="size" label="文件大小" width="120" align="center">
          <template #default="{ row }">
            {{ formatFileSize(row.size) }}
          </template>
        </el-table-column>

        <el-table-column prop="type" label="文件类型" width="120" align="center">
          <template #default="{ row }">
            <el-tag :type="getFileTypeTag(row.type)">{{ row.type }}</el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="uploadTime" label="上传时间" width="180" align="center" />

        <el-table-column prop="downloads" label="下载次数" width="100" align="center" />

        <el-table-column label="操作" width="120" align="center" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              :icon="Download"
              size="small"
              :loading="row.downloading"
              @click="handleDownload(row)"
            >
              下载
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.currentPage"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<style scoped>
.resource-share {
  text-align: center;
  margin-top: 100px;
  font-size: 24px;
  color: #333;
  padding: 20px;
  background: #fff;
  border-radius: 8px;
}

.search-bar {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.resource-list {
  margin-top: 20px;
}

.resource-name {
  display: flex;
  align-items: center;
  gap: 8px;
}

.file-icon {
  color: #409eff;
  font-size: 18px;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}
</style>
