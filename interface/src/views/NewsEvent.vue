<script setup>
import { onMounted, ref } from 'vue'
import { getEventList } from '@/api/events'

const loading = ref(true)
const list = ref([])

const loadEvent = async () => {
  loading.value = true
  const res = await getEventList()
  if (res.code === 0) list.value = res.data
  loading.value = false
}

onMounted(loadEvent)
</script>

<template>
  <div class="wall">
    <div v-loading="loading" class="wall-body">
      <div class="cards">
        <div v-for="e in list" :key="e.id" class="card">
          <el-image :src="e.img" fit="cover" class="pic" lazy />
          <div class="info">
            <div class="title">{{ e.title }}</div>
            <div class="desc">{{ e.desc }}</div>
            <el-tag size="small" class="tag">{{ e.date }}</el-tag>
          </div>
        </div>
      </div>

      <el-empty v-if="!loading && !list.length" description="暂无活动" />
    </div>
  </div>
</template>

<style scoped lang="less">
/* 与之前样式相同，仅去掉 .group 相关 */
.wall {
  max-width: 1200px;
  margin: 60px auto;
  padding: 40px 20px;
  background: #fafbfc;
  min-height: 100vh;
}

.cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.card {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: transform 0.2s;
  &:hover {
    transform: translateY(-4px);
  }

  .pic {
    width: 100%;
    height: 160px;
    display: block;
  }

  .info {
    padding: 14px 16px 16px;
    .title {
      font-size: 17px;
      font-weight: 600;
      color: #303133;
      margin-bottom: 6px;
    }
    .desc {
      font-size: 14px;
      color: #606266;
      line-height: 1.6;
      margin-bottom: 10px;
    }
    .tag {
      background: #f0f9ff;
      border-color: #d1e9ff;
      color: #409eff;
    }
  }
}

@media (max-width: 768px) {
  .cards {
    grid-template-columns: 1fr;
  }
}
</style>
