<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import SearchProject from '../components/SearchProject.vue'
// import { getProjectDetail } from '@/api/project'

const route = useRoute()
const projectId = route.params.projectId // 获取当前项目ID
const project = ref({})

// 加载对应ID的项目数据
// const fetchData = async () => {
//   const res = await getProjectDetail(projectId)
//   project.value = res.data // 不同ID加载不同数据
// }

// onMounted(() => fetchData())
</script>

<template>
  <div class="team-introduce">
    <!-- <h1>detail:{{ projectId }}</h1>
      -->
    <SearchProject :projectId="projectId"/>

  </div>
</template>

<style scoped>
.team-introduce {
  text-align: center;
  margin-top: 100px;
  font-size: 24px;
  color: #333;
}
</style>
