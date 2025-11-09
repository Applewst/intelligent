<template>
  <div class="contact-info-container">
    <el-card class="info-card">
      <template #header>
        <div class="card-header">
          <span class="title">科研联系信息管理</span>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="120px"
        label-position="right"
        v-loading="loading"
      >
        <!-- 电子邮箱 -->
        <el-form-item label="电子邮箱" prop="email">
          <el-input
            v-model="formData.email"
            placeholder="请输入电子邮箱"
            clearable
          >
            <template #prefix>
              <el-icon><Message /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <!-- 科研地点 -->
        <el-form-item label="科研地点" prop="location">
          <el-input
            v-model="formData.location"
            type="textarea"
            :rows="2"
            placeholder="请输入科研地点详细地址"
            clearable
          >
          </el-input>
        </el-form-item>

        <!-- 科研地点图片 -->
        <el-form-item label="科研地点图片" prop="locationImage">
          <div class="upload-container">
            <el-upload
              class="image-uploader"
              :show-file-list="false"
              :before-upload="beforeLocationImageUpload"
              :http-request="handleLocationImageUpload"
              accept="image/*"
            >
              <img
                v-if="formData.locationImage"
                :src="formData.locationImage"
                class="uploaded-image"
              />
              <div v-else class="upload-placeholder">
                <el-icon class="upload-icon"><Plus /></el-icon>
                <div class="upload-text">点击上传科研地点图片</div>
              </div>
            </el-upload>
            <div class="upload-tip">
              建议尺寸：800x600px，支持 jpg、png 格式
            </div>
          </div>
        </el-form-item>

        <!-- 微信公众号 -->
        <el-form-item label="微信公众号" prop="wechatAccount">
          <el-input
            v-model="formData.wechatAccount"
            placeholder="请输入微信公众号名称"
            clearable
          >
            <template #prefix>
              <el-icon><ChatDotRound /></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <!-- 微信二维码 -->
        <el-form-item label="微信二维码" prop="wechatQrCode">
          <div class="upload-container">
            <el-upload
              class="qrcode-uploader"
              :show-file-list="false"
              :before-upload="beforeQrCodeUpload"
              :http-request="handleQrCodeUpload"
              accept="image/*"
            >
              <img
                v-if="formData.wechatQrCode"
                :src="formData.wechatQrCode"
                class="uploaded-qrcode"
              />
              <div v-else class="upload-placeholder qrcode-placeholder">
                <el-icon class="upload-icon"><Plus /></el-icon>
                <div class="upload-text">点击上传微信二维码</div>
              </div>
            </el-upload>
            <div class="upload-tip">
              建议尺寸：400x400px，支持 jpg、png 格式
            </div>
          </div>
        </el-form-item>

        <!-- 操作按钮 -->
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitting">
            保存修改
          </el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import {
  getContactInfo,
  updateContactInfo,
  uploadImage,
} from "@/api/contact.js";

// 表单引用
const formRef = ref(null);

// 加载状态
const loading = ref(false);
const submitting = ref(false);

// 表单数据
const formData = reactive({
  email: "",
  location: "",
  locationImage: "",
  wechatAccount: "",
  wechatQrCode: "",
});

// 原始数据备份（用于重置）
const originalData = reactive({});

// 表单验证规则
const rules = {
  email: [
    { required: true, message: "请输入电子邮箱", trigger: "blur" },
    { type: "email", message: "请输入正确的邮箱格式", trigger: "blur" },
  ],
  location: [
    { required: true, message: "请输入科研地点", trigger: "blur" },
    { min: 5, message: "地点名称至少5个字符", trigger: "blur" },
  ],
  locationImage: [
    { required: true, message: "请上传科研地点图片", trigger: "change" },
  ],
  wechatAccount: [
    { required: true, message: "请输入微信公众号名称", trigger: "blur" },
  ],
  wechatQrCode: [
    { required: true, message: "请上传微信二维码", trigger: "change" },
  ],
};

// 获取联系信息
const fetchContactInfo = async () => {
  loading.value = true;
  try {
    const response = await getContactInfo();
    if (response.code === 200) {
      Object.assign(formData, response.data);
      Object.assign(originalData, response.data);
    } else {
      ElMessage.error(response.message || "获取信息失败");
    }
  } catch (error) {
    console.error("获取联系信息失败：", error);
    ElMessage.error("获取信息失败，请稍后重试");
  } finally {
    loading.value = false;
  }
};

// 科研地点图片上传前验证
const beforeLocationImageUpload = (file) => {
  const isImage = file.type.startsWith("image/");
  const isLt5M = file.size / 1024 / 1024 < 5;

  if (!isImage) {
    ElMessage.error("只能上传图片文件!");
    return false;
  }
  if (!isLt5M) {
    ElMessage.error("图片大小不能超过 5MB!");
    return false;
  }
  return true;
};

// 处理科研地点图片上传
const handleLocationImageUpload = async (options) => {
  try {
    const response = await uploadImage(options.file);
    if (response.code === 200) {
      formData.locationImage = response.data.url;
      ElMessage.success("图片上传成功");
    } else {
      ElMessage.error(response.message || "图片上传失败");
    }
  } catch (error) {
    console.error("图片上传失败：", error);
    ElMessage.error("图片上传失败，请稍后重试");
  }
};

// 二维码上传前验证
const beforeQrCodeUpload = (file) => {
  const isImage = file.type.startsWith("image/");
  const isLt2M = file.size / 1024 / 1024 < 2;

  if (!isImage) {
    ElMessage.error("只能上传图片文件!");
    return false;
  }
  if (!isLt2M) {
    ElMessage.error("图片大小不能超过 2MB!");
    return false;
  }
  return true;
};

// 处理二维码上传
const handleQrCodeUpload = async (options) => {
  try {
    const response = await uploadImage(options.file);
    if (response.code === 200) {
      formData.wechatQrCode = response.data.url;
      ElMessage.success("二维码上传成功");
    } else {
      ElMessage.error(response.message || "二维码上传失败");
    }
  } catch (error) {
    console.error("二维码上传失败：", error);
    ElMessage.error("二维码上传失败，请稍后重试");
  }
};

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true;
      try {
        const response = await updateContactInfo(formData);
        if (response.code === 200) {
          ElMessage.success("保存成功");
          Object.assign(originalData, formData);
        } else {
          ElMessage.error(response.message || "保存失败");
        }
      } catch (error) {
        console.error("保存失败：", error);
        ElMessage.error("保存失败，请稍后重试");
      } finally {
        submitting.value = false;
      }
    } else {
      ElMessage.warning("请完善表单信息");
    }
  });
};

// 重置表单
const handleReset = () => {
  Object.assign(formData, originalData);
  formRef.value?.clearValidate();
  ElMessage.info("已重置为初始数据");
};

// 组件挂载时获取数据
onMounted(() => {
  fetchContactInfo();
});
</script>

<style scoped>
.contact-info-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.info-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
}

.upload-container {
  width: 100%;
}

.image-uploader,
.qrcode-uploader {
  display: inline-block;
}

.uploaded-image {
  width: 400px;
  height: 300px;
  object-fit: cover;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.uploaded-image:hover {
  opacity: 0.8;
}

.uploaded-qrcode {
  width: 200px;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.uploaded-qrcode:hover {
  opacity: 0.8;
}

.upload-placeholder {
  width: 400px;
  height: 300px;
  border: 2px dashed #d9d9d9;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all 0.3s;
  background-color: #fafafa;
}

.qrcode-placeholder {
  width: 200px;
  height: 200px;
}

.upload-placeholder:hover {
  border-color: #409eff;
  background-color: #f5f7fa;
}

.upload-icon {
  font-size: 48px;
  color: #c0c4cc;
  margin-bottom: 12px;
}

.upload-text {
  font-size: 14px;
  color: #606266;
}

.upload-tip {
  margin-top: 8px;
  font-size: 12px;
  color: #909399;
  line-height: 1.5;
}

:deep(.el-form-item__label) {
  font-weight: 500;
}

:deep(.el-textarea__inner) {
  resize: vertical;
}
</style>
