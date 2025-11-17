<script setup>
import { onMounted, ref } from 'vue'
import { getStudentDevelopList } from '@/api/develop.js'
import { watch } from 'vue'

const loading = ref(true)
const list = ref([])

// <CHANGE> 添加分页相关的状态
const pageNum = ref(1)
const pageSize = ref(6)
const total = ref(0)

const GetDevelopList = async (pageNum, pageSize, name = '') => {
  console.log(pageNum, pageSize, name)

  const response = await getStudentDevelopList(pageNum, pageSize, name)
  console.log('response',response)
  
  console.log('response', response)

  if (response.code === 1) {
    list.value = response.data.data
    total.value = response.data.total
    loading.value = false
  } else {
    console.error(response.message)
    loading.value = false
  }
}

onMounted(() => {
  console.log(123)

  GetDevelopList(pageNum.value, pageSize.value)
})
watch([pageNum, pageSize], () => {
  GetDevelopList(pageNum.value, pageSize.value)
})
// <CHANGE> 添加页码改变处理函数
const handleCurrentChange = (page) => {
  pageNum.value = page
}

// <CHANGE> 添加每页条数改变处理函数
const handleSizeChange = (size) => {
  pageSize.value = size
  pageNum.value = 1 // 重置到第一页
}
</script>

<template>
  <div class="wall">
    <h1 class="TitleTop">学生发展</h1>
    <div v-loading="loading" class="wall-body">
      <div class="cards">
        <div v-for="e in list" :key="e.id" class="card">
          <el-image :src="e.image" fit="cover" class="pic" lazy />
          <div class="info">
            <div class="title">{{ e.title }}</div>
            <div class="desc">{{ e.detail }}</div>
            <el-tag size="small" class="tag">{{ e.time }}</el-tag>
          </div>
        </div>
      </div>

      <el-empty v-if="!loading && !list.length" description="暂无活动" />

      <!-- <CHANGE> 添加分页组件 -->
      <div v-if="!loading && list.length" class="pagination">
        <el-pagination
          v-model:current-page="pageNum"
          v-model:page-size="pageSize"
          :page-sizes="[6, 12, 18, 24]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="handleCurrentChange"
          @size-change="handleSizeChange"
        />
      </div>
    </div>
  </div>
</template>

<style scoped lang="less">
.wall {
  max-width: 1200px;
  margin: 60px auto;
  padding: 40px 20px;
  background: #fafbfc;
  min-height: 100vh;
}
.TitleTop {
  //放中间
  text-align: center;
  margin-bottom: 10px;
  font-size: 36px;
  font-weight: 600;
  color: #303133;
}
.cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: transform 0.2s;
  &:hover {
    transform: translateY(-4px);
  }

  .pic {
    width: 100%;
    height: 160px;
    display: block;
  }

  .info {
    padding: 14px 16px 16px;
    .title {
      font-size: 17px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 6px;
    }
    .desc {
      font-size: 14px;
      color: #606266;
      line-height: 1.6;
      margin-bottom: 10px;
    }
    .tag {
      background: #f0f9ff;
      border-color: #d1e9ff;
      color: #409eff;
    }
  }
}

/* <CHANGE> 添加分页组件样式 */
.pagination {
  display: flex;
  justify-content: center;
  margin-top: 40px;
  padding: 20px 0;
}

@media (max-width: 768px) {
  .cards {
    grid-template-columns: 1fr;
  }

  /* <CHANGE> 移动端分页样式调整 */
  .pagination {
    :deep(.el-pagination) {
      justify-content: center;
      flex-wrap: wrap;
    }
  }
}
</style>
