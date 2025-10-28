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
      icon:'location',
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
  const noChild = computed(() => list.value.filter(item => !item.children) )
  const hasChild = computed(() => list.value.filter(item => item.children) )  //看是否需要二级目录
  console.log(hasChild.value);
  
</script>

<template>
  <el-aside width="180px">
    <el-menu
      background-color="#545c64"
      text-color="#fff"
      
      >
      <h3>后台管理系统</h3>
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
  }
}
.el-aside{
  height: 100%;
  background-color: #545c64;
}
</style>