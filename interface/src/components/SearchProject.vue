<script setup>
import { onMounted, ref } from 'vue';
// import { useRouter } from 'vue-router';
import { GetSearchProjects } from '../api/search';
const props = defineProps({
  projectId: Number
})

const tables = ref([])

const GetAllSearchProjects = async (id) => {
  const response = await GetSearchProjects(id);
  if (response.data && !Array.isArray(response.data)) {
    tables.value = [response.data]
  } else {
    tables.value = response.data || []
  }
  

}

onMounted(() => {
  GetAllSearchProjects(props.projectId);
})
</script>

<template>
  <div class="search-project">
    <div class="module">
      <div class="image-container">
        <!-- 添加调试：检查循环次数 -->
        <div
          class="image-item"
          v-for="item in tables"
          :key="item.id"
        >

          <el-card>
            <img :src="item.image" alt="">
            <template #footer>
              <div class="project-info">
                <div class="project-name">{{ item.title }}</div>
                <div class="project-time">{{ item.time }}</div>
              </div>
            </template>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.search-project {
  margin-top: 100px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.module {
  margin: 0 auto; /* 居中 */
  width: 1200px; /* 控制模块宽度 */
  padding: 10px;
  margin-bottom: 50px;
  border-radius: 5px;
  position: relative;
}

.module-title {
  text-align: center;
  margin-bottom: 60px;
  font-weight: bold;
  font-size: 30px;
}

.image-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.image-item {
  position: relative;
  margin: 0 auto;
  margin-bottom: 20px;
  width: calc(30.33% - 20px);
  height: 320px; /* 固定卡片高度，确保内容不溢出 */
  border: 1px solid #ccc;
  border-radius: 5px;
  overflow: hidden; /* 去除多余部分 */
  cursor: pointer;
}

.el-card {
  height: 100%; /* 确保卡片高度填满 .image-item */
  display: flex;
  flex-direction: column;
}

img {
  width: 100%;
  height: auto; /* 图片自适应宽度 */
  max-height: 100%; /* 限制图片高度，确保留出文字空间 */
  object-fit: cover; /* 图片适配时裁剪 */
}

.project-info {
  flex: 1; /* 填充剩余空间 */
  display: flex;
  flex-direction: column;
  align-items: center; /* 项目名称居中 */
  justify-content: flex-end; /* 时间在底部 */
  padding: 10px; /* 内边距 */
}

.project-name {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 5px; /* 与时间保持间距 */
  color: #333; /* 确保项目名称清晰 */
}

.project-time {
  font-size: 14px;
  color: #666; /* 轻度的颜色以显示时间 */
}

.image-item:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}
</style>
