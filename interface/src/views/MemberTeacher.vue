<script setup>
import { ref, onMounted } from 'vue'
import { getTeacherList } from '@/api/teacher'
import { useRouter } from 'vue-router'

const router = useRouter()
const teachers = ref([])
const currentPage = ref(1)
const pageSize = ref(6)
const total = ref(0)

const cleanAvatar = (raw = '') => {
  if (!raw) {
    return 'https://picsum.photos/200/200' // 临时用 picsum 占位，后续可替换为你的默认图
  }
  return raw
}

const fetchTeachers = async () => {
  const res = await getTeacherList({
    pageNum: currentPage.value,
    pageSize: pageSize.value,
    name: '', // 传递空的name参数
  })
  console.log(res.data)

  if (res.code === 1) {
    teachers.value = res.data.data
    total.value = res.data.total
  }
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchTeachers()
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  fetchTeachers()
}

onMounted(async () => {
  await fetchTeachers()
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

    <!-- 添加分页组件 -->
    <div class="pagination-wrapper">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[6, 12, 18, 24]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @current-change="handlePageChange"
        @size-change="handleSizeChange"
      />
    </div>
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
  justify-content: center;
  margin-bottom: 24px;
}

.teacher-card {
  width: 100%;
  max-width: 260px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 24px 20px;
  border-radius: 12px;
  background: #ffffff;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s, box-shadow 0.2s;
  cursor: pointer;

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
  flex-direction: column;
  align-items: center;
  gap: 8px;
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

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 40px;
  padding-bottom: 40px;
}
</style>
