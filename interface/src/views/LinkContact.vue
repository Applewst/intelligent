<template>
  <div class="contact-display">
    <el-card class="contact-card" v-loading="loading">
      <template #header>
        <div class="card-header">
          <span class="title">科研联系信息</span>
          <el-button type="primary" size="small" @click="refreshData">
            <el-icon><Refresh /></el-icon>
            刷新
          </el-button>
        </div>
      </template>

      <div v-if="contactInfo" class="contact-content">
        <!-- 电子邮箱 -->
        <div class="info-item">
          <div class="info-label">
            <el-icon><Message /></el-icon>
            <span>电子邮箱</span>
          </div>
          <div class="info-value">
            <a :href="`mailto:${contactInfo.email}`" class="email-link">
              {{ contactInfo.email }}
            </a>
          </div>
        </div>

        <el-divider />

        <!-- 科研地点 -->
        <div class="info-item">
          <div class="info-label">
            <el-icon><Location /></el-icon>
            <span>科研地点</span>
          </div>
          <div class="info-value">{{ contactInfo.location }}</div>
        </div>

        <!-- 科研地点图片 -->
        <div class="info-item" v-if="contactInfo.locationImage">
          <div class="image-container">
            <el-image
              :src="contactInfo.locationImage"
              :preview-src-list="[contactInfo.locationImage]"
              fit="cover"
              class="location-image"
              :preview-teleported="true"
            >
              <template #error>
                <div class="image-slot">
                  <el-icon><Picture /></el-icon>
                  <span>图片加载失败</span>
                </div>
              </template>
            </el-image>
          </div>
        </div>

        <el-divider />

        <!-- 微信公众号 -->
        <div class="info-item">
          <div class="info-label">
            <el-icon><ChatDotRound /></el-icon>
            <span>微信公众号</span>
          </div>
          <div class="info-value">{{ contactInfo.wechatAccount }}</div>
        </div>

        <!-- 微信二维码 -->
        <div class="info-item" v-if="contactInfo.wechatQrCode">
          <div class="qrcode-container">
            <el-image
              :src="contactInfo.wechatQrCode"
              :preview-src-list="[contactInfo.wechatQrCode]"
              fit="contain"
              class="qrcode-image"
              :preview-teleported="true"
            >
              <template #error>
                <div class="image-slot">
                  <el-icon><Picture /></el-icon>
                  <span>二维码加载失败</span>
                </div>
              </template>
            </el-image>
            <div class="qrcode-tip">扫码关注公众号</div>
          </div>
        </div>
      </div>

      <el-empty v-else description="暂无数据" />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getContactInfo } from '@/api/contact.js'

const loading = ref(false)
const contactInfo = ref(null)

// 获取联系信息
const fetchContactInfo = async () => {
  loading.value = true
  try {
    const response = await getContactInfo()
    if (response.code === 200) {
      contactInfo.value = response.data
    } else {
      ElMessage.error(response.message || '获取联系信息失败')
    }
  } catch (error) {
    console.error('获取联系信息失败：', error)
    ElMessage.error('获取联系信息失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

// 刷新数据
const refreshData = () => {
  fetchContactInfo()
}

onMounted(() => {
  fetchContactInfo()
})
</script>

<style scoped>
.contact-display {
  padding: 20px;
  background: #f5f7fa;
  min-height: 100vh;
  text-align: center;
  margin-top: 100px;
  font-size: 24px;
  color: #333;
}

.contact-card {
  max-width: 800px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header .title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.contact-content {
  padding: 10px 0;
}

.info-item {
  margin-bottom: 24px;
}

.info-item:last-child {
  margin-bottom: 0;
}

.info-label {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 600;
  color: #606266;
  margin-bottom: 12px;
}

.info-label .el-icon {
  font-size: 18px;
  color: #409eff;
}

.info-value {
  font-size: 15px;
  color: #303133;
  line-height: 1.6;
  padding-left: 26px;
}

.email-link {
  color: #409eff;
  text-decoration: none;
  transition: color 0.3s;
}

.email-link:hover {
  color: #66b1ff;
  text-decoration: underline;
}

.image-container {
  padding-left: 26px;
  margin-top: 12px;
}

.location-image {
  width: 100%;
  max-width: 600px;
  height: 300px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s;
}

.location-image:hover {
  transform: scale(1.02);
}

.qrcode-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 8px;
  margin-top: 12px;
}

.qrcode-image {
  width: 200px;
  height: 200px;
  border-radius: 8px;
  background: white;
  padding: 10px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.qrcode-tip {
  margin-top: 12px;
  font-size: 13px;
  color: #909399;
  text-align: center;
}

.image-slot {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: #f5f7fa;
  color: #909399;
  gap: 8px;
}

.image-slot .el-icon {
  font-size: 32px;
}

.image-slot span {
  font-size: 13px;
}

.el-divider {
  margin: 24px 0;
}

@media (max-width: 768px) {
  .contact-display {
    padding: 10px;
  }

  .location-image {
    height: 200px;
  }

  .info-value {
    padding-left: 0;
    margin-top: 8px;
  }

  .image-container {
    padding-left: 0;
  }
}
</style>
