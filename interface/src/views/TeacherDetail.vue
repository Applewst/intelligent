<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { getTeacherDetail } from '@/api/teacher'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const teacherId = route.params.teacherId
const teacher = ref({})
const loading = ref(true)

const cleanAvatar = (raw = '') => {
  const match = raw.match(/https:\/\/picsum\.photos\/id\/\d+\/\d+\/\d+/)
  return match ? match[0] : ''
}

const fetchData = async () => {
  try {
    const res = await getTeacherDetail(Number(teacherId))
    teacher.value = res.data.data
  } catch (e) {
    ElMessage.error(e.message || '获取教师详情失败')
    router.back()
  } finally {
    loading.value = false
  }
}

onMounted(fetchData)
</script>

<template>
  <div class="page">
    <div v-loading="loading" class="content">
      <div class="left">
        <el-avatar :size="260" :src="cleanAvatar(teacher.avatar)" class="avatar" shape="square" />
      </div>

      <div class="right">
        <h1 class="name">{{ teacher.name }}</h1>
        <p class="title">{{ teacher.title }}</p>
        <p class="intro">{{ teacher.intro || '暂无简介' }}</p>
        <div class="contact">
          <el-link :href="'mailto:' + teacher.email" :underline="false" icon="Message">
            {{ teacher.email }}
          </el-link>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="less">
/* ---------- 变量 ---------- */
@gray-1: #f2f3f5; /* 背景灰 */
@gray-9: #1f2329; /* 文字主色 */
@primary: #4a90e2; /* 主题蓝（邮箱链接） */
@photo-w: 140px; /* 证件照宽 35 mm ≈ 140 px@2× */
@photo-h: 180px; /* 证件照高 45 mm ≈ 180 px@2× */

/* ---------- 整体 ---------- */
.page {
  display: flex;
  margin-top: 50px;
  justify-content: center;
  padding: 64px 24px;
  background: @gray-1;
}
.content {
  display: flex;
  gap: 48px;
  width: 100%;
  max-width: 820px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 6px 24px rgba(0, 0, 0, 0.06);
  padding: 48px;
}

/* ---------- 左侧证件照 ---------- */
.left {
  flex: 0 0 @photo-w;
}
.avatar {
  width: @photo-w;
  height: @photo-h;
  border-radius: 6px; /* 证件照小圆角 */
  object-fit: cover;
  border: 1px solid #e6e8eb;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

/* ---------- 右侧信息 ---------- */
.right {
  flex: 1;
  display: flex;
  flex-direction: column;
  justify-content: center; /* 垂直居中，视觉更稳 */
}
.name {
  font-size: 28px;
  font-weight: 600;
  color: @gray-9;
  margin: 0 0 4px;
}
.title {
  font-size: 16px;
  color: #656b73;
  margin-bottom: 20px;
}
.contact {
  margin-bottom: 24px;
  a {
    font-size: 14px;
    color: @primary;
    display: inline-flex;
    align-items: center;
    gap: 4px;
  }
}
.intro {
  font-size: 15px;
  color: #454a52;
  line-height: 1.8;
}

/* ---------- 响应式 ---------- */
@media (max-width: 768px) {
  .content {
    flex-direction: column;
    align-items: center;
    text-align: center;
    padding: 32px 24px;
  }
  .right {
    align-items: center;
  }
}
</style>
