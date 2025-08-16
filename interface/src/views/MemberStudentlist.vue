<template>
  <div class="student-wall">
    <h2 class="title">在读学生</h2>

    <!-- 一行两个 -->
    <div class="student-list">
      <div v-for="s in students" :key="s.id" class="student-card">
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStudentList } from '@/api/student'
import { ElMessage } from 'element-plus'

const students = ref([])
const loading = ref(true)

const fetchStudentData = async () => {
  try {
    loading.value = true
    const res = await getStudentList()

    if (res.data.code === 0) {
      students.value = res.data.data.list
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

onMounted(fetchStudentData)
</script>

<style scoped lang="less">
.student-wall {
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
  .student-list {
    width: 100%;
    max-width: 800px;
    display: flex;
    flex-wrap: wrap;
    gap: 24px;
    justify-content: center;
  }

  .student-card {
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
}
</style>
