// 统一导出所有 stores
export { useCounterStore } from './counter'
export { useUserStore } from './user'
export { useAppStore } from './app'
export { useNewsStore } from './news'
export { useProjectStore } from './project'
export { useTeacherStore } from './teacher'
export { useStudentStore } from './student'

// 也可以创建一个组合函数来同时使用多个 stores
export const useAllStores = () => {
  const counterStore = useCounterStore()
  const userStore = useUserStore()
  const appStore = useAppStore()
  const newsStore = useNewsStore()
  const projectStore = useProjectStore()
  const teacherStore = useTeacherStore()
  const studentStore = useStudentStore()
  
  return {
    counterStore,
    userStore,
    appStore,
    newsStore,
    projectStore,
    teacherStore,
    studentStore
  }
}
