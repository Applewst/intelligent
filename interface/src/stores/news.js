// import { ref, computed } from 'vue'
// import { defineStore } from 'pinia'

// export const useNewsStore = defineStore('news', () => {
//   // 新闻列表
//   const newsList = ref([])

//   // 当前新闻详情
//   const currentNews = ref(null)

//   // 新闻分类
//   const categories = ref([
//     { id: 1, name: '学术动态', value: 'academic' },
//     { id: 2, name: '团队动态', value: 'team' },
//     { id: 3, name: '学生活动', value: 'student' },
//     { id: 4, name: '活动照片', value: 'photo' }
//   ])

//   // 当前选中的分类
//   const selectedCategory = ref('')

//   // 搜索关键词
//   const searchKeyword = ref('')

//   // 分页信息
//   const pagination = ref({
//     current: 1,
//     pageSize: 10,
//     total: 0
//   })

//   // 加载状态
//   const loading = ref(false)

//   // 计算属性：过滤后的新闻列表
//   const filteredNews = computed(() => {
//     let filtered = newsList.value

//     // 按分类过滤
//     if (selectedCategory.value) {
//       filtered = filtered.filter(news => news.category === selectedCategory.value)
//     }

//     // 按关键词搜索
//     if (searchKeyword.value) {
//       const keyword = searchKeyword.value.toLowerCase()
//       filtered = filtered.filter(news =>
//         news.title.toLowerCase().includes(keyword) ||
//         news.content.toLowerCase().includes(keyword)
//       )
//     }

//     return filtered
//   })

//   // 计算属性：分页后的新闻列表
//   const paginatedNews = computed(() => {
//     const start = (pagination.value.current - 1) * pagination.value.pageSize
//     const end = start + pagination.value.pageSize
//     return filteredNews.value.slice(start, end)
//   })

//   // 设置新闻列表
//   const setNewsList = (news) => {
//     newsList.value = news
//   }

//   // 添加新闻
//   const addNews = (news) => {
//     newsList.value.unshift(news)
//   }

//   // 更新新闻
//   const updateNews = (id, updatedNews) => {
//     const index = newsList.value.findIndex(news => news.id === id)
//     if (index !== -1) {
//       newsList.value[index] = { ...newsList.value[index], ...updatedNews }
//     }
//   }

//   // 删除新闻
//   const deleteNews = (id) => {
//     const index = newsList.value.findIndex(news => news.id === id)
//     if (index !== -1) {
//       newsList.value.splice(index, 1)
//     }
//   }

//   // 设置当前新闻
//   const setCurrentNews = (news) => {
//     currentNews.value = news
//   }

//   // 设置分类
//   const setCategory = (category) => {
//     selectedCategory.value = category
//   }

//   // 设置搜索关键词
//   const setSearchKeyword = (keyword) => {
//     searchKeyword.value = keyword
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
//     selectedCategory.value = ''
//     searchKeyword.value = ''
//     pagination.value.current = 1
//   }

//   return {
//     newsList,
//     currentNews,
//     categories,
//     selectedCategory,
//     searchKeyword,
//     pagination,
//     loading,
//     filteredNews,
//     paginatedNews,
//     setNewsList,
//     addNews,
//     updateNews,
//     deleteNews,
//     setCurrentNews,
//     setCategory,
//     setSearchKeyword,
//     setPagination,
//     setTotal,
//     setLoading,
//     resetFilters
//   }
// })
