<script setup>
import { ref, onMounted } from 'vue'
import { getTeacherList } from '@/api/teacher'
import { useRouter } from 'vue-router'

const router = useRouter()
const teachers = ref([])

const cleanAvatar = (raw = '') => {
  const match = raw.match(/https:\/\/picsum\.photos\/id\/\d+\/\d+\/\d+/)
  return match ? match[0] : ''
}

onMounted(async () => {
  const res = await getTeacherList()
  if (res.data.code === 0) {
    teachers.value = res.data.data.list
  }
})

const goToDetail = (t) => {
  router.push(`/member/teacher/${t.id}`)
}
</script>

<template>
  <div class="teacher-simple">
    <el-row justify="center" :gutter="24">
      <el-col v-for="t in teachers" :key="t.id" :span="8" class="col">
        <div class="teacher-card" @click="goToDetail(t)">
          <el-avatar :size="90" :src="cleanAvatar(t.avatar)" />
          <div class="info">
            <div class="name">{{ t.name }}</div>
            <div class="meta">
              <el-tag :type="undefined || 'info'" size="small">{{ t.title }}</el-tag>
              <el-link :href="'mailto:' + t.email" :underline="false" class="email">
                {{ t.email }}
              </el-link>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<style scoped lang="less">
.teacher-simple {
  text-align: center;
  margin-top: 100px;
  padding: 40px 20px;
  background: #f5f7fa;
  min-height: 100vh;
}
.col {
  display: flex;
  justify-content: center; // 卡片在栅格内居中
  margin-bottom: 24px;
}

.teacher-card {
  width: 100%;
  max-width: 260px; // 限制最大宽度，防止拉伸变形
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 20px;
  border-radius: 12px;
  background: #ffffff;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s, box-shadow 0.2s;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 6px 16px rgba(0, 0, 0, 0.08);
  }
}

:deep(.el-avatar) {
  border: 2px solid #fff;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.info {
  margin-top: 16px;
  text-align: center;
  line-height: 1.4;
}

.meta {
  display: flex;
  flex-direction: column; // 纵向排列
  align-items: center;
  gap: 8px; // 上下间距
}

.name {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin-bottom: 8px;
}

:deep(.el-tag) {
  margin-bottom: 10px;
}

.email {
  font-size: 13px;
  color: #409eff;
}
</style>
