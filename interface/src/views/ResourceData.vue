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
  pageSize: 5,
  total: 0, // 用于存储总记录数
})

// 获取资源列表
const getResourceList = async () => {
  loading.value = true
  try {
    const params = {
      page: pagination.currentPage,
      pageSize: pagination.pageSize,
      keyword: searchKeyword.value || '',
    }

    const response = await resourceApi.getResourceList(params)
    console.log(response.data)

    // 核心修改 1: 正确处理分页数据
    // 假设你的后端返回格式是 { data: [...], total: 100 }
    resourceList.value = response.data.data || []
    pagination.total = response.data.total || 0 // 将 total 赋值为总记录数
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
    const blob = await resourceApi.downloadResource(row.id)
    downloadFile(blob, row.name)

    ElMessage.success('下载成功')

    // 更新下载次数（这只是前端临时更新，刷新后会恢复）
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
    // MP4 和 MP3 没有定义类型，会返回 undefined
  }
  // 核心修改 2: 为没有匹配到的类型提供一个默认值，如 'info' 或直接返回 undefined
  // 返回 undefined 时，el-tag 会使用默认样式
  return typeMap[type?.toUpperCase()]
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
            {{ row.size }}
          </template>
        </el-table-column>

        <el-table-column prop="type" label="文件类型" width="120" align="center">
          <template #default="{ row }">
            <!-- 这里 :type 会接收 undefined，el-tag 会处理为默认样式，不会报错 -->
            <el-tag :type="getFileTypeTag(row.type)">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="downloads" label="下载次数" width="100" align="center" />

        <el-table-column label="操作" width="120" align="center" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
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
          :page-sizes="[5, 10, 20, 50, 100]"
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
