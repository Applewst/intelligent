import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path:'/',
    name:'Main',
    component:()=> import('@/views/Main.vue'),
    redirect:'/home',
    children:[
      {
        //首页
        path:'/home',
        name:'Home',
        //保证每次刷新都会回到首页
        
        component:()=> import('@/views/HomeManager.vue')
      },
      {
        //团队动态
        path:'/team',
        name:'Team',
        children:[
          {
            path:'/team/sciences',
            name:'TeamSciences',
            component:()=> import('@/views/TeamSciences.vue')
          },
          {
            path:'/team/activity',
            name:'TeamActivity',
            component:()=> import('@/views/TeamActivity.vue')
          },
          {
            path:'/team/development',
            name:'TeamDevelopment',
            component:()=> import('@/views/TeamDevelopment.vue')
          },
          {
            path:'/team/Shoot',
            name:'TeamShoot',
            component:()=> import('@/views/TeamShoot.vue')
          }
        ]
      },
      {
        //研究方向
        path:'/search',
        name:'Search',
        children:[
          {
            path:'/search/project',
            name:'SearchProject',
            component:()=> import('@/views/SearchProject.vue')
          },
          {
            path:'/search/paper',
            name:'SearchPaper',
            component:()=> import('@/views/SearchPaper.vue')
          },
          {
            path:'/search/award',
            name:'SearchAward',
            component:()=> import('@/views/SearchAward.vue')
          },
        ]
      },
      {
        //成员管理
        path:'/people',
        name:'People',
        children:[
          {
            path:'/people/teacher',
            name:'PeopleTeacher',
            component:()=> import('@/views/PeopleTeacher.vue')
          },
          {
            path:'/people/Instudent',
            name:'PeopleInstudent',
            component:()=> import('@/views/PeopleInstudent.vue')
          },
          {
            path:'/people/Outstudent',
            name:'PeopleOutstudent',
            component:()=> import('@/views/PeopleOutstudent.vue')
          }
          
        ]
      }
    ]
 }
]
  


const router = createRouter({
  history: createWebHistory(),
  routes  
})

export default router;