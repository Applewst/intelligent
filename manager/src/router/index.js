import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path:'/',
    name:'Main',
    component:()=> import('@/views/Main.vue'),
    redirect:'/home',
    children:[
      {
        path:'/home',
        name:'Home',
        component:()=> import('@/views/HomeManager.vue')
      },
      
      {
        path:'/teacher',
        name:'Teacher',
        component:()=> import('@/views/TeacherManager.vue')
      },
      {
        path:'/team',
        name:'Team',
        component:()=> import('@/views/TeamManager.vue')
      },
      {
        path:'/search',
        name:'Search',
        component:()=> import('@/views/SearchManager.vue')
      },
      {
        path:'/paper',
        name:'Paper',
        component:()=> import('@/views/PaperManager.vue')
      },
      
      
    ]
 }
]
  


const router = createRouter({
  history: createWebHistory(),
  routes  
})

export default router;