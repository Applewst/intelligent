import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import UserRegister from '../views/UserRegister.vue'
import TeamIntroduce from '../views/TeamIntroduce.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        {
          path:'/team/introduction',
          name: 'introduction',
          component: TeamIntroduce
        },
        {
          path: '/search/project',
          name: 'project',
          component: ()=>import('../views/SearchProject.vue')
        },
        {
          path: '/search/paper',
          name: 'paper',
          component: ()=>import('../views/SearchPaper.vue')
        },
        {
          path: '/search/award',
          name: 'award',
          component: ()=>import('../views/SearchAward.vue')
        },
        {
          path: '/news/activity',
          name: 'activity',
          component: ()=>import('../views/NewsActivity.vue')
        },
        {
          path: '/news/event',
          name: 'event',
          component: ()=>import('../views/NewsEvent.vue')
        },
        {
          path: '/news/student',
          name: 'student',
          component: ()=>import('../views/NewsStudent.vue')
        },
        {
          path: '/news/photo',
          name: 'photo',
          component: ()=>import('../views/NewsPhoto.vue')
        },
        {
          path: '/member/teacher',
          name: 'teacher',
          component: ()=>import('../views/MemberTeacher.vue')
        },
        {
          path: '/member/studentlist',
          name: 'studentlist',
          component: ()=>import('../views/MemberStudentList.vue')
        },
        {
          path: '/member/alumni',
          name: 'alumni',
          component: ()=>import('../views/MemberAlumni.vue')
        },
        {
          path: '/link/contact',
          name: 'contact',
          component: ()=>import('../views/LinkContact.vue')
        },
        {
          path: '/resource/data',
          name: 'data',
          component: ()=>import('../views/ResourceData.vue')
        },
        {
          path: '/resource/doc',
          name: 'doc',
          component: ()=>import('../views/ResourceDoc.vue')
        },
        {
          path: '/resource/code',
          name: 'code',
          component: ()=>import('../views/ResourceCode.vue')
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
      ]
    },
    {
      path: '/login',
      name: 'login',
      component: UserRegister
    },



  ],
})

export default router
