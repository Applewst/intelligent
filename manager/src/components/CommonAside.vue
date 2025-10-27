<script setup>
  import { ref,computed } from 'vue'
  const list = ref([
    {
      path:'/home',
      name:'home',
      label:'首页',
      icon:'house',
      url:'Home'
    },
    {
      path:'/student',
      name:'student',
      label:'学生管理',
      icon:'house',
      url:'Stu'
    },
    {
      path:'/resource',
      name:'resource',
      label:'资源管理',
      icon:'house',
      url:'Resource'
    },
    {
      path:'/other',
      name:'other',
      label:'其他',
      children:[
        {
          path:'page1',
          name:'page1',
          label:'页面1',
          icon:'setting',
          url:'Page1'
        },
        {
          path:'page2',
          name:'page2',
          label:'页面2',
          icon:'setting',
          url:'Page2'
        }
      ]
    }
  ])
  const noChild = computed(() => { list.value.filter(item => !item.children) })
  const hasChild = computed(() => { list.value.filter(item =>  item.children) })  //看是否需要二级目录
</script>

<template>
  <el-aside width="180px">
    <el-menu
  
     
    >
    <h3>后台管理系统</h3>
      <el-menu-item
        v-for="item in noChild"
        :index="item.path"
        :key="item.path"
        >
        <component class="icons" :is="item.icon"></component>
        <span>{{item.label}}</span>
     </el-menu-item>
      <!-- 二级目录 -->
      <el-sub-menu
        v-for="item in hasChild"
        :index="item.path"
        :key="item.path"
      >
          <template #title>
            <component class="icons" :is="item.icon"></component>
            <span>{{item.label}}</span>
          </template>
          <el-menu-item-group>
            <el-menu-item
              v-for="(subItem,subIndex) in hasChild"
              :index="subItem.path"
              :key="subItem.path"
            >
              <component class="icons" :is="subItem.icon"></component>
              <span>{{subItem.label}}</span>
          </el-menu-item>
          </el-menu-item-group>

      </el-sub-menu>
    </el-menu>
  </el-aside>
</template>

<style scoped lang="less">

</style>