import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useProjectStore = defineStore('project', () => {
  // 项目列表
  const projectList = ref([])
  
  // 当前项目详情
  const currentProject = ref(null)
  
  // 项目状态选项
  const projectStatuses = ref([
    { id: 1, name: '进行中', value: 'ongoing' },
    { id: 2, name: '已完成', value: 'completed' },
    { id: 3, name: '已暂停', value: 'paused' },
    { id: 4, name: '已取消', value: 'cancelled' }
  ])
  
  // 项目类型选项
  const projectTypes = ref([
    { id: 1, name: '科研项目', value: 'research' },
    { id: 2, name: '教学项目', value: 'teaching' },
    { id: 3, name: '合作项目', value: 'collaboration' },
    { id: 4, name: '创新项目', value: 'innovation' }
  ])
  
  // 筛选条件
  const filters = ref({
    status: '',
    type: '',
    keyword: '',
    startDate: '',
    endDate: ''
  })
  
  // 分页信息
  const pagination = ref({
    current: 1,
    pageSize: 12,
    total: 0
  })
  
  // 加载状态
  const loading = ref(false)
  
  // 计算属性：过滤后的项目列表
  const filteredProjects = computed(() => {
    let filtered = projectList.value
    
    // 按状态过滤
    if (filters.value.status) {
      filtered = filtered.filter(project => project.status === filters.value.status)
    }
    
    // 按类型过滤
    if (filters.value.type) {
      filtered = filtered.filter(project => project.type === filters.value.type)
    }
    
    // 按关键词搜索
    if (filters.value.keyword) {
      const keyword = filters.value.keyword.toLowerCase()
      filtered = filtered.filter(project => 
        project.title.toLowerCase().includes(keyword) ||
        project.description.toLowerCase().includes(keyword) ||
        project.tags.some(tag => tag.toLowerCase().includes(keyword))
      )
    }
    
    // 按日期范围过滤
    if (filters.value.startDate) {
      filtered = filtered.filter(project => 
        new Date(project.startDate) >= new Date(filters.value.startDate)
      )
    }
    
    if (filters.value.endDate) {
      filtered = filtered.filter(project => 
        new Date(project.endDate) <= new Date(filters.value.endDate)
      )
    }
    
    return filtered
  })
  
  // 计算属性：分页后的项目列表
  const paginatedProjects = computed(() => {
    const start = (pagination.value.current - 1) * pagination.value.pageSize
    const end = start + pagination.value.pageSize
    return filteredProjects.value.slice(start, end)
  })
  
  // 计算属性：按状态分组的项目
  const projectsByStatus = computed(() => {
    const grouped = {}
    filteredProjects.value.forEach(project => {
      if (!grouped[project.status]) {
        grouped[project.status] = []
      }
      grouped[project.status].push(project)
    })
    return grouped
  })
  
  // 设置项目列表
  const setProjectList = (projects) => {
    projectList.value = projects
  }
  
  // 添加项目
  const addProject = (project) => {
    projectList.value.unshift(project)
  }
  
  // 更新项目
  const updateProject = (id, updatedProject) => {
    const index = projectList.value.findIndex(project => project.id === id)
    if (index !== -1) {
      projectList.value[index] = { ...projectList.value[index], ...updatedProject }
    }
  }
  
  // 删除项目
  const deleteProject = (id) => {
    const index = projectList.value.findIndex(project => project.id === id)
    if (index !== -1) {
      projectList.value.splice(index, 1)
    }
  }
  
  // 设置当前项目
  const setCurrentProject = (project) => {
    currentProject.value = project
  }
  
  // 设置筛选条件
  const setFilters = (newFilters) => {
    filters.value = { ...filters.value, ...newFilters }
  }
  
  // 设置分页
  const setPagination = (page, pageSize) => {
    pagination.value.current = page
    pagination.value.pageSize = pageSize
  }
  
  // 设置总数
  const setTotal = (total) => {
    pagination.value.total = total
  }
  
  // 设置加载状态
  const setLoading = (status) => {
    loading.value = status
  }
  
  // 重置筛选条件
  const resetFilters = () => {
    filters.value = {
      status: '',
      type: '',
      keyword: '',
      startDate: '',
      endDate: ''
    }
    pagination.value.current = 1
  }
  
  // 获取项目统计信息
  const getProjectStats = () => {
    const stats = {
      total: projectList.value.length,
      ongoing: projectList.value.filter(p => p.status === 'ongoing').length,
      completed: projectList.value.filter(p => p.status === 'completed').length,
      paused: projectList.value.filter(p => p.status === 'paused').length,
      cancelled: projectList.value.filter(p => p.status === 'cancelled').length
    }
    return stats
  }
  
  return {
    projectList,
    currentProject,
    projectStatuses,
    projectTypes,
    filters,
    pagination,
    loading,
    filteredProjects,
    paginatedProjects,
    projectsByStatus,
    setProjectList,
    addProject,
    updateProject,
    deleteProject,
    setCurrentProject,
    setFilters,
    setPagination,
    setTotal,
    setLoading,
    resetFilters,
    getProjectStats
  }
})
