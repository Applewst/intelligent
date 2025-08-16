<script setup>
import { ref, onMounted } from 'vue'
import { getTeacherList } from '@/api/teacher'
import { useRouter } from 'vue-router'

const router = useRouter()
const personnelList = ref([])
const loading = ref(false)

// 标签颜色映射（保持不变）
const getTagColor = (type) => {
  const colorMap = {
    primary: '#409eff',
    success: '#67c23a',
    warning: '#e6a23c',
    info: '#909399',
    danger: '#f56c6c',
  }
  return colorMap[type] || '#409eff'
}

// 跳转详情页（保持不变）
const goToDetail = (person) => {
  router.push(`/member/teacher/${person.id}`)
}

const fetchTeacherData = async () => {
  try {
    loading.value = true
    const res = await getTeacherList()

    if (res.data.code === 0) {
      personnelList.value = res.data.data.list
    } else {
      ElMessage.warning(res.data.message || '获取数据失败')
    }
  } catch (error) {
    console.error('数据请求失败：', error)
    ElMessage.error('加载教师数据失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchTeacherData()
})
</script>

<template>
  <el-carousel
    :interval="4000"
    type="card"
    height="420px"
    indicator-position="outside"
    :autoplay="false"
  >
    <el-carousel-item v-for="(person, index) in personnelList" :key="index">
      <div class="person-card">
        <!-- 顶部装饰元素 -->
        <div
          class="card-decoration"
          :style="{ backgroundColor: getTagColor(person.tagType) }"
        ></div>
        <!-- 头像容器部分 -->
        <div class="avatar-container" @click="goToDetail(person)">
          <div class="avatar-frame">
            <el-avatar :src="person.avatar" shape="square" size="large" class="avatar">
              <span class="avatar-fallback">{{ person.name.charAt(0) }}</span>
            </el-avatar>
          </div>
        </div>

        <div class="person-info">
          <div class="info-content">
            <h3 class="person-name cursor-pointer" @click="goToDetail(person)">
              {{ person.name }}
            </h3>
            <div class="info-divider"></div>
            <el-tag :type="person.tagType" class="person-tag">
              {{ person.title }}
            </el-tag>
            <p class="person-email">
              <el-icon size="14"><Message /></el-icon>
              <span>{{ person.email }}</span>
            </p>
          </div>
        </div>
      </div>
    </el-carousel-item>
  </el-carousel>
</template>

<style scoped>
.person-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
  padding: 20px 15px;
  box-sizing: border-box;
  background-color: #fff;
  border-radius: 12px;
  position: relative;
  overflow: hidden;
  transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.05);
}

.card-decoration {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 4px;
  background: linear-gradient(
    90deg,
    rgba(255, 255, 255, 0.2),
    currentColor 50%,
    rgba(255, 255, 255, 0.2)
  );
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}

/* 头像容器调整 */
.avatar-container {
  margin: 30px 0 15px; /* 减少顶部间距 */
  position: relative;
  z-index: 2;
}
.avatar-frame {
  position: relative;
  padding: 8px; /* 增大内边距，为脉冲留出更多空间 */
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.1);
  /* 添加轻微悬浮感基础 */
  transform: translateY(0);
  transition: transform 0.3s ease;
}

/* 鼠标悬停时轻微上浮，增强交互反馈 */
.avatar-frame:hover {
  transform: translateY(-5px);
}

/* 主脉冲边框 */
.avatar-frame::after {
  content: '';
  position: absolute;
  top: -4px; /* 扩大脉冲范围 */
  left: -4px;
  right: -4px;
  bottom: -4px;
  border-radius: 12px; /* 增大圆角与边框呼应 */
  border: 2px solid var(--el-color-primary);
  animation: pulse 2s infinite;
  pointer-events: none;
}

/* 新增外层扩散光圈，增强浮动感 */
.avatar-frame::before {
  content: '';
  position: absolute;
  top: -12px; /* 大幅扩大外层光圈范围 */
  left: -12px;
  right: -12px;
  bottom: -12px;
  border-radius: 20px;
  background: radial-gradient(circle, var(--el-color-primary/20) 0%, transparent 70%);
  animation: pulseFloat 3s infinite ease-in-out;
  pointer-events: none;
}

/* 主脉冲动画（增大范围和强度） */
@keyframes pulse {
  0% {
    border-color: var(--el-color-primary/20);
    box-shadow: 0 0 0 0 rgba(64, 158, 255, 0.3);
    transform: scale(1);
  }
  50% {
    border-color: var(--el-color-primary);
    box-shadow: 0 0 0 8px rgba(64, 158, 255, 0.4); /* 增大阴影扩散范围 */
    transform: scale(1.03); /* 轻微放大增强浮动感 */
  }
  100% {
    border-color: var(--el-color-primary/20);
    box-shadow: 0 0 0 0 rgba(64, 158, 255, 0.3);
    transform: scale(1);
  }
}

/* 外层光圈浮动动画 */
@keyframes pulseFloat {
  0% {
    opacity: 0.4;
    transform: scale(1);
  }
  50% {
    opacity: 0.8; /* 增强透明度变化 */
    transform: scale(1.15); /* 更大幅度缩放 */
  }
  100% {
    opacity: 0.4;
    transform: scale(1);
  }
}

.avatar {
  width: 140px !important;
  height: 190px !important;
  object-fit: cover;
  border: 2px solid #f8f9fa; /* 加粗边框增强层次感 */
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.12);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  position: relative;
  z-index: 2;
}

/* 头像悬停效果增强 */
.avatar:hover {
  transform: scale(1.05); /* 增大缩放比例 */
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
}

/* 文字占位符调整 */
.avatar-fallback {
  font-size: 24px;
  font-weight: bold;
  color: #409eff;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

/* 人员信息区域（保持不变） */
.person-info {
  text-align: center;
  padding: 15px 10px;
  width: 100%;
  box-sizing: border-box;
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 2;
}

.info-content {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.person-name {
  font-size: 20px;
  font-weight: 600;
  margin: 0;
  color: #303133;
  position: relative;
  display: inline-block;
  align-self: center;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}

.person-name::after {
  content: '';
  position: absolute;
  bottom: -6px;
  left: 50%;
  transform: translateX(-50%);
  width: 30px;
  height: 3px;
  background-color: rgba(255, 0, 255, 0.557);
  border-radius: 3px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
}

.cursor-pointer {
  cursor: pointer;
  transition: all 0.2s ease;
}
.cursor-pointer:hover {
  transform: scale(1.2);
}

.person-tag {
  margin: 8px 0;
  font-size: 13px;
  padding: 3px 12px;
  align-self: center;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.05);
}

.info-divider {
  width: 60%;
  height: 1px;
  background: linear-gradient(90deg, transparent, #f0f2f5, transparent);
  margin: 5px auto;
}

.person-email {
  font-size: 13px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  margin: 0;
  color: #606266;
  line-height: 1.5;
  color: #8d8d8d;
  margin-top: 5px;
}

/* 轮播卡片样式（保持不变） */
:deep(.el-carousel--card) {
  width: 90%;
  margin: 30px auto;
  padding: 10px 0;
  background-color: #f8f9fa;
  border-radius: 16px;
  padding: 20px 0;
  box-shadow: 0 3px 10px rgba(0, 0, 0, 0.04);
}

:deep(.el-carousel--card .el-carousel__item) {
  width: 290px !important;
  height: 420px !important;
  transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  margin: 0 15px;
}

:deep(.el-carousel--card .el-carousel__item.is-active) {
  transform: scale(1.05);
  z-index: 10;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.08), 0 2px 10px rgba(0, 0, 0, 0.06);
}

:deep(.el-carousel--card .el-carousel__item:not(.is-active)) {
  opacity: 0.85;
  transform: scale(0.95);
  filter: brightness(0.97);
}
</style>
