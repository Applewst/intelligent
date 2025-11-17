// 统一导出所有 stores
export { useUserStore } from './user'
export { useAppStore } from './app'

// 也可以创建一个组合函数来同时使用多个 stores
export const useAllStores = () => {
  const userStore = useUserStore()
  const appStore = useAppStore()

  return {
    userStore,
    appStore,
  }
}
