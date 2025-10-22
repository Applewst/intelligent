import { ref, computed } from 'vue'
import { defineStore } from 'pinia'

export const useStudentStore = defineStore('student', () => {
  // 学生列表
  const studentList = ref([])
  
  // 当前学生详情
  const currentStudent = ref(null)
  
  // 学生状态选项
  const studentStatuses = ref([
    { id: 1, name: '在读', value: 'enrolled' },
    { id: 2, name: '毕业', value: 'graduated' },
    { id: 3, name: '休学', value: 'suspended' },
    { id: 4, name: '退学', value: 'dropped' }
  ])
  
  // 学历层次选项
  const educationLevels = ref([
    { id: 1, name: '本科生', value: 'undergraduate' },
    { id: 2, name: '硕士生', value: 'master' },
    { id: 3, name: '博士生', value: 'phd' }
  ])
  
  // 筛选条件
  const filters = ref({
    keyword: '',
    status: '',
    level: '',
    major: '',
    grade: '',
    advisor: ''
  })
  
  // 分页信息
  const pagination = ref({
    current: 1,
    pageSize: 12,
    total: 0
  })
  
  // 加载状态
  const loading = ref(false)
  
  // 计算属性：过滤后的学生列表
  const filteredStudents = computed(() => {
    let filtered = studentList.value
    
    // 按关键词搜索
    if (filters.value.keyword) {
      const keyword = filters.value.keyword.toLowerCase()
      filtered = filtered.filter(student => 
        student.name.toLowerCase().includes(keyword) ||
        student.studentId.toLowerCase().includes(keyword) ||
        student.major.toLowerCase().includes(keyword) ||
        student.email.toLowerCase().includes(keyword)
      )
    }
    
    // 按状态过滤
    if (filters.value.status) {
      filtered = filtered.filter(student => 
        student.status === filters.value.status
      )
    }
    
    // 按学历层次过滤
    if (filters.value.level) {
      filtered = filtered.filter(student => 
        student.educationLevel === filters.value.level
      )
    }
    
    // 按专业过滤
    if (filters.value.major) {
      filtered = filtered.filter(student => 
        student.major === filters.value.major
      )
    }
    
    // 按年级过滤
    if (filters.value.grade) {
      filtered = filtered.filter(student => 
        student.grade === filters.value.grade
      )
    }
    
    // 按导师过滤
    if (filters.value.advisor) {
      filtered = filtered.filter(student => 
        student.advisor === filters.value.advisor
      )
    }
    
    return filtered
  })
  
  // 计算属性：分页后的学生列表
  const paginatedStudents = computed(() => {
    const start = (pagination.value.current - 1) * pagination.value.pageSize
    const end = start + pagination.value.pageSize
    return filteredStudents.value.slice(start, end)
  })
  
  // 计算属性：按学历层次分组的学生
  const studentsByLevel = computed(() => {
    const grouped = {}
    filteredStudents.value.forEach(student => {
      if (!grouped[student.educationLevel]) {
        grouped[student.educationLevel] = []
      }
      grouped[student.educationLevel].push(student)
    })
    return grouped
  })
  
  // 计算属性：按状态分组的学生
  const studentsByStatus = computed(() => {
    const grouped = {}
    filteredStudents.value.forEach(student => {
      if (!grouped[student.status]) {
        grouped[student.status] = []
      }
      grouped[student.status].push(student)
    })
    return grouped
  })
  
  // 设置学生列表
  const setStudentList = (students) => {
    studentList.value = students
  }
  
  // 添加学生
  const addStudent = (student) => {
    studentList.value.unshift(student)
  }
  
  // 更新学生信息
  const updateStudent = (id, updatedStudent) => {
    const index = studentList.value.findIndex(student => student.id === id)
    if (index !== -1) {
      studentList.value[index] = { ...studentList.value[index], ...updatedStudent }
    }
  }
  
  // 删除学生
  const deleteStudent = (id) => {
    const index = studentList.value.findIndex(student => student.id === id)
    if (index !== -1) {
      studentList.value.splice(index, 1)
    }
  }
  
  // 设置当前学生
  const setCurrentStudent = (student) => {
    currentStudent.value = student
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
      keyword: '',
      status: '',
      level: '',
      major: '',
      grade: '',
      advisor: ''
    }
    pagination.value.current = 1
  }
  
  // 获取学生统计信息
  const getStudentStats = () => {
    const stats = {
      total: studentList.value.length,
      enrolled: studentList.value.filter(s => s.status === 'enrolled').length,
      graduated: studentList.value.filter(s => s.status === 'graduated').length,
      suspended: studentList.value.filter(s => s.status === 'suspended').length,
      dropped: studentList.value.filter(s => s.status === 'dropped').length,
      undergraduate: studentList.value.filter(s => s.educationLevel === 'undergraduate').length,
      master: studentList.value.filter(s => s.educationLevel === 'master').length,
      phd: studentList.value.filter(s => s.educationLevel === 'phd').length
    }
    return stats
  }
  
  // 根据导师获取学生
  const getStudentsByAdvisor = (advisorName) => {
    return studentList.value.filter(student => 
      student.advisor === advisorName
    )
  }
  
  // 根据专业获取学生
  const getStudentsByMajor = (major) => {
    return studentList.value.filter(student => 
      student.major === major
    )
  }
  
  return {
    studentList,
    currentStudent,
    studentStatuses,
    educationLevels,
    filters,
    pagination,
    loading,
    filteredStudents,
    paginatedStudents,
    studentsByLevel,
    studentsByStatus,
    setStudentList,
    addStudent,
    updateStudent,
    deleteStudent,
    setCurrentStudent,
    setFilters,
    setPagination,
    setTotal,
    setLoading,
    resetFilters,
    getStudentStats,
    getStudentsByAdvisor,
    getStudentsByMajor
  }
})
