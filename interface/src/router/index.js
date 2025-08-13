import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/UserRegister.vue'),
    },
    {
      path:'/news',
      name:'news',
      component:()=>import('../views/MoreNews.vue'),
    },
    {
      path: '/projects/:projectId',
      name: 'ProjectDetail',
      component: ()=>import('../views/ProjectDetail.vue'),
      props: route => ({ projectId: route.params.projectId })
    },
    {
      path: '/teacher-detail/:teacherId',
      name: 'TeacherDetail',
      component: ()=>import('../views/TeacherDetail.vue'),
      props: route => ({ projectId: route.params.teacherId })
    },
    {
      path: '/photos',
      name: 'photos',
      component: ()=>import('../views/PhotosWall.vue'),
    },
  ],
})

export default router
