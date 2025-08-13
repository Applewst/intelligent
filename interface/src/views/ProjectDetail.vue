<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
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

<template>detail:{{ projectId }}</template>

<style scoped></style>
