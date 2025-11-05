<script setup>
  import { ref,computed } from 'vue'
  import { useDataStore } from '@/stores'
  const list = ref([
    {
      path:'/home',
      name:'home',
      label:'首页',
      icon:'house',
      url:'Home'
    },
    {
      path:'/team',
      name:'team',
      label:'团队活动',
      icon:'house',
      url:'Team'
    },
    {
      path:'/teacher',
      name:'teacher',
      label:'教师管理',
      icon:'house',
      url:'Resource'
    },
    {
      path:'/search',
      name:'search',
      label:'科研管理',
      icon:'house',
      url:'Search'
    },
    {
      path:'/paper',
      name:'paper',
      label:'论文管理',
      icon:'house',
      url:'Paper'
    },
    {
      path:'/student',
      name:'student',
      label:'学生管理',
      icon:'location',
      children:[
        {
          path:'/graduate',
          name:'graduate',
          label:'毕业生',
          icon:'setting',
          url:'Graduate'
        },
        {
          path:'/inschool',
          name:'inschool',
          label:'在校生',
          icon:'setting',
          url:'InSchool'
        }
      ]
    }
  ])
  const noChild = computed(() => list.value.filter(item => !item.children) )
  const hasChild = computed(() => list.value.filter(item => item.children) )  //看是否需要二级目录

  const store = useDataStore()
  const isCollapse = computed(() => store.state.isCollapse) 
  //侧边栏宽度
  const widths = computed(() => store.state.isCollapse ? '60px' : '180px')
</script>

<template>
  <el-aside :width="widths">
    <el-menu
      background-color="#545c64"
      text-color="#fff"
      :collapse="isCollapse"
      collapse-transition="true"
      router="true"
      >
      <h3 v-if="!isCollapse">后台管理系统</h3>
      <h3 v-else>后台</h3>
      <el-menu-item 
        v-for="item in noChild"
        :key="item.path"
        :index="item.path"
        
      >
      
          <component :is="item.icon" class="icons"></component>
          <span>{{item.label}}</span>
      </el-menu-item>
      <el-sub-menu 
        v-for="item in hasChild"
        :key="item.path"
        :index="item.path"
      >
        <template #title>
          <component :is="item.icon" class="icons"></component>
          <span>{{item.label}}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item 
            v-for="(subItem,subIndex) in item.children"
            :key="subIndex.path"
            :index="subIndex.path"
          >
          <component :is="subItem.icon" class="icons"></component>
            {{subItem.label}}
          </el-menu-item>
        </el-menu-item-group>
       
     
        </el-sub-menu>

        
      </el-menu>
  </el-aside>
</template>

<style scoped lang="less">
.icons{
  width: 18px;
  height: 18px;
  margin-right: 5px;
}
.el-menu{
  border-right:none;
  h3{
    line-height:48px;
    color:#fff;
    text-align: center;
    margin-bottom: 10px;
  }
}
.el-aside{
  height: 100%;
  background-color: #545c64;
}
</style>