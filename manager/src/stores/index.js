import {defineStore} from 'pinia'
import {ref} from 'vue'

function initState(){
  return {
    isCollapse:false,  //侧边栏是否折叠
    userName:'',//从前台传来
  }
}

export const useDataStore = defineStore('datastore',()=>{
  const state=ref(initState()) 

  return {
    state
  }
})