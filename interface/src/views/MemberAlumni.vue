<template>
  <div class="graduate-wall">
    <h2 class="title">桃李天下</h2>

    <!-- 一行两个 -->
    <div class="graduate-list">
      <div v-for="s in graduates" :key="s.id" class="graduate-card">
        <!-- 证件照 -->
        <div class="avatar-box">
          <img :src="s.avatar" class="avatar" />
        </div>

        <!-- 信息 -->
        <div class="info">
          <h3>{{ s.name }}</h3>
          <p>{{ s.grade }} 级</p>
          <p>专业：{{ s.major }}</p>
        </div>
      </div>
    </div>

    <!-- Added pagination component -->
    <div class="pagination-wrapper">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getGraduateList } from '@/api/graduate'
import { ElMessage } from 'element-plus'

const graduates = ref([])
const loading = ref(true)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const fetchGraduateData = async () => {
  try {
    loading.value = true
    const res = await getGraduateList({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      name: '', // 设置name参数为空值
    })

    if (res.data.code === 0) {
      graduates.value = res.data.data.list
      total.value = res.data.data.total
    } else {
      ElMessage.warning(res.data.message || '获取数据失败')
    }
  } catch (error) {
    console.error('学生数据请求失败：', error)
    ElMessage.error('加载学生数据失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

const handleSizeChange = (newSize) => {
  pageSize.value = newSize
  currentPage.value = 1
  fetchGraduateData()
}

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage
  fetchGraduateData()
}

onMounted(fetchGraduateData)
</script>

<style scoped lang="less">
.graduate-wall {
  margin-top: 50px;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: #f5f7fa;
  padding: 40px 20px;

  .title {
    font-size: 28px;
    margin-bottom: 30px;
    color: #303133;
  }

  /* 一行两个：flex 自动换行 */
  .graduate-list {
    width: 100%;
    max-width: 800px;
    display: flex;
    flex-wrap: wrap;
    gap: 24px;
    justify-content: center;
  }

  .graduate-card {
    width: 360px;
    display: flex;
    align-items: center;
    gap: 16px;
    padding: 16px;
    border-radius: 12px;
    background: #fff;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  }

  /* 证件照比例 7:9 */
  .avatar-box {
    width: 70px;
    height: 90px;
    flex-shrink: 0;
    border-radius: 4px;
    overflow: hidden;
    border: 1px solid #dcdfe6;
  }
  .avatar {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }

  .info {
    line-height: 1.6;
    h3 {
      margin: 0 0 4px;
      font-size: 17px;
      font-weight: 600;
      color: #303133;
    }
    p {
      margin: 0;
      font-size: 14px;
      color: #606266;
    }
  }

  /* Added pagination styling */
  .pagination-wrapper {
    margin-top: 40px;
    display: flex;
    justify-content: center;
  }
}
</style>
