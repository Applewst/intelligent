<template>
  <div class="team-introduction">
    <el-card class="introduction-card">
      <template #header>
        <div class="card-header">
          <h2>团队介绍管理</h2>
        </div>
      </template>

      <el-form
        ref="formRef"
        :model="formData"
        :rules="rules"
        label-width="120px"
        v-loading="loading"
      >
        <el-form-item label="团队名称" prop="teamName">
          <el-input
            v-model="formData.teamName"
            placeholder="请输入团队名称"
            clearable
          />
        </el-form-item>

        <el-form-item label="团队简介" prop="introduction">
          <el-input
            v-model="formData.introduction"
            type="textarea"
            :rows="4"
            placeholder="请输入团队简介"
          />
        </el-form-item>

        <el-form-item label="团队图片">
          <div class="image-upload-container">
            <el-upload
              class="image-uploader"
              :show-file-list="false"
              :on-change="handleImageChange"
              :auto-upload="false"
              accept="image/*"
            >
              <img v-if="imageUrl" :src="imageUrl" class="preview-image" />
              <el-icon v-else class="image-uploader-icon">
                <Plus />
              </el-icon>
            </el-upload>
            <div class="image-tip">建议尺寸：800x600px</div>
          </div>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitting">
            保存
          </el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from "vue";
import { ElMessage } from "element-plus";
import { Plus } from "@element-plus/icons-vue";
import { getTeamIntroduction, updateTeamIntroduction } from "@/api/introduce";

const formRef = ref(null);
const loading = ref(false);
const submitting = ref(false);
const imageUrl = ref("");
const imageFile = ref(null);

const formData = reactive({
  teamName: "",
  introduction: "",
});

const rules = {
  teamName: [{ required: true, message: "请输入团队名称", trigger: "blur" }],
  introduction: [
    { required: true, message: "请输入团队简介", trigger: "blur" },
  ],
};

// 获取团队介绍信息
const fetchTeamData = async () => {
  loading.value = true;
  try {
    const response = await getTeamIntroduction();
    if (response.code === 200) {
      formData.teamName = response.data.teamName;
      formData.introduction = response.data.introduction;
      imageUrl.value = response.data.imageUrl;
      ElMessage.success("数据加载成功");
    } else {
      ElMessage.error(response.message || "获取数据失败");
    }
  } catch (error) {
    ElMessage.error("获取数据失败，请稍后重试");
    console.error("Error fetching team data:", error);
  } finally {
    loading.value = false;
  }
};

// 处理图片变化
const handleImageChange = (file) => {
  const isImage = file.raw.type.startsWith("image/");
  const isLt5M = file.raw.size / 1024 / 1024 < 5;

  if (!isImage) {
    ElMessage.error("只能上传图片文件!");
    return;
  }
  if (!isLt5M) {
    ElMessage.error("图片大小不能超过 5MB!");
    return;
  }

  imageFile.value = file.raw;
  imageUrl.value = URL.createObjectURL(file.raw);
};

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return;

  await formRef.value.validate(async (valid) => {
    if (valid) {
      submitting.value = true;
      try {
        const formDataToSubmit = new FormData();
        formDataToSubmit.append("teamName", formData.teamName);
        formDataToSubmit.append("introduction", formData.introduction);

        if (imageFile.value) {
          formDataToSubmit.append("image", imageFile.value);
        }

        const response = await updateTeamIntroduction(formDataToSubmit);

        if (response.code === 200) {
          ElMessage.success("保存成功");
          await fetchTeamData(); // 重新获取数据
        } else {
          ElMessage.error(response.message || "保存失败");
        }
      } catch (error) {
        ElMessage.error("保存失败，请稍后重试");
        console.error("Error submitting form:", error);
      } finally {
        submitting.value = false;
      }
    }
  });
};

// 取消操作
const handleCancel = () => {
  formRef.value?.resetFields();
  imageFile.value = null;
  fetchTeamData(); // 重新加载原始数据
};

// 组件挂载时获取数据
onMounted(() => {
  fetchTeamData();
});
</script>

<style scoped>
.team-introduction {
  padding: 20px;
  max-width: 900px;
  margin: 0 auto;
}

.introduction-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-header h2 {
  margin: 0;
  font-size: 20px;
  color: #303133;
}

.image-upload-container {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.image-uploader {
  width: 200px;
  height: 200px;
  border: 2px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  overflow: hidden;
  transition: all 0.3s;
}

.image-uploader:hover {
  border-color: #409eff;
}

.image-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 200px;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.preview-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.image-tip {
  font-size: 12px;
  color: #909399;
}

:deep(.el-form-item__label) {
  font-weight: 500;
}

:deep(.el-textarea__inner) {
  resize: vertical;
}
</style>