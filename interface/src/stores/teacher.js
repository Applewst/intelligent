// import { ref, computed } from 'vue'
// import { defineStore } from 'pinia'

// export const useTeacherStore = defineStore('teacher', () => {
//   // 教师列表
//   const teacherList = ref([])

//   // 当前教师详情
//   const currentTeacher = ref(null)

//   // 研究方向列表
//   const researchDirections = ref([])

//   // 筛选条件
//   const filters = ref({
//     keyword: '',
//     direction: '',
//     title: '', // 职称
//     department: ''
//   })

//   // 分页信息
//   const pagination = ref({
//     current: 1,
//     pageSize: 12,
//     total: 0
//   })

//   // 加载状态
//   const loading = ref(false)

//   // 计算属性：过滤后的教师列表
//   const filteredTeachers = computed(() => {
//     let filtered = teacherList.value

//     // 按关键词搜索
//     if (filters.value.keyword) {
//       const keyword = filters.value.keyword.toLowerCase()
//       filtered = filtered.filter(teacher =>
//         teacher.name.toLowerCase().includes(keyword) ||
//         teacher.title.toLowerCase().includes(keyword) ||
//         teacher.department.toLowerCase().includes(keyword) ||
//         teacher.researchInterests.some(interest =>
//           interest.toLowerCase().includes(keyword)
//         )
//       )
//     }

//     // 按研究方向过滤
//     if (filters.value.direction) {
//       filtered = filtered.filter(teacher =>
//         teacher.researchDirections.includes(filters.value.direction)
//       )
//     }

//     // 按职称过滤
//     if (filters.value.title) {
//       filtered = filtered.filter(teacher =>
//         teacher.title === filters.value.title
//       )
//     }

//     // 按院系过滤
//     if (filters.value.department) {
//       filtered = filtered.filter(teacher =>
//         teacher.department === filters.value.department
//       )
//     }

//     return filtered
//   })

//   // 计算属性：分页后的教师列表
//   const paginatedTeachers = computed(() => {
//     const start = (pagination.value.current - 1) * pagination.value.pageSize
//     const end = start + pagination.value.pageSize
//     return filteredTeachers.value.slice(start, end)
//   })

//   // 计算属性：按院系分组的教师
//   const teachersByDepartment = computed(() => {
//     const grouped = {}
//     filteredTeachers.value.forEach(teacher => {
//       if (!grouped[teacher.department]) {
//         grouped[teacher.department] = []
//       }
//       grouped[teacher.department].push(teacher)
//     })
//     return grouped
//   })

//   // 设置教师列表
//   const setTeacherList = (teachers) => {
//     teacherList.value = teachers
//   }

//   // 添加教师
//   const addTeacher = (teacher) => {
//     teacherList.value.unshift(teacher)
//   }

//   // 更新教师信息
//   const updateTeacher = (id, updatedTeacher) => {
//     const index = teacherList.value.findIndex(teacher => teacher.id === id)
//     if (index !== -1) {
//       teacherList.value[index] = { ...teacherList.value[index], ...updatedTeacher }
//     }
//   }

//   // 删除教师
//   const deleteTeacher = (id) => {
//     const index = teacherList.value.findIndex(teacher => teacher.id === id)
//     if (index !== -1) {
//       teacherList.value.splice(index, 1)
//     }
//   }

//   // 设置当前教师
//   const setCurrentTeacher = (teacher) => {
//     currentTeacher.value = teacher
//   }

//   // 设置研究方向列表
//   const setResearchDirections = (directions) => {
//     researchDirections.value = directions
//   }

//   // 设置筛选条件
//   const setFilters = (newFilters) => {
//     filters.value = { ...filters.value, ...newFilters }
//   }

//   // 设置分页
//   const setPagination = (page, pageSize) => {
//     pagination.value.current = page
//     pagination.value.pageSize = pageSize
//   }

//   // 设置总数
//   const setTotal = (total) => {
//     pagination.value.total = total
//   }

//   // 设置加载状态
//   const setLoading = (status) => {
//     loading.value = status
//   }

//   // 重置筛选条件
//   const resetFilters = () => {
//     filters.value = {
//       keyword: '',
//       direction: '',
//       title: '',
//       department: ''
//     }
//     pagination.value.current = 1
//   }

//   // 获取教师统计信息
//   const getTeacherStats = () => {
//     const stats = {
//       total: teacherList.value.length,
//       professors: teacherList.value.filter(t => t.title === '教授').length,
//       associateProfessors: teacherList.value.filter(t => t.title === '副教授').length,
//       lecturers: teacherList.value.filter(t => t.title === '讲师').length,
//       departments: [...new Set(teacherList.value.map(t => t.department))].length
//     }
//     return stats
//   }

//   // 根据研究方向获取教师
//   const getTeachersByDirection = (direction) => {
//     return teacherList.value.filter(teacher =>
//       teacher.researchDirections.includes(direction)
//     )
//   }

//   return {
//     teacherList,
//     currentTeacher,
//     researchDirections,
//     filters,
//     pagination,
//     loading,
//     filteredTeachers,
//     paginatedTeachers,
//     teachersByDepartment,
//     setTeacherList,
//     addTeacher,
//     updateTeacher,
//     deleteTeacher,
//     setCurrentTeacher,
//     setResearchDirections,
//     setFilters,
//     setPagination,
//     setTotal,
//     setLoading,
//     resetFilters,
//     getTeacherStats,
//     getTeachersByDirection
//   }
// })
