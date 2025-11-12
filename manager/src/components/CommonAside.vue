<script setup>
import { ref, computed } from "vue";
import { useDataStore } from "@/stores";
const list = ref([
  {
    path: "/home",
    name: "home",
    label: "首页",
    icon: "house",
    url: "Home",
  },
  {
    path: "/introduce",
    name: "TeamIntroduction",
    label: "团队介绍",
    icon: "house",
    url: "TeamIntroduction",
  },
  {
    path: "/user",
    name: "AdminUser",
    label: "管理人员",
    icon: "house",
    url: "AdminUser",
  },
  {
    path: "/contact",
    name: "ContactUs",
    label: "联系我们",
    icon: "house",
    url: "ContactUs",
  },
  {
    path: "/team",
    name: "team",
    label: "团队动态",
    icon: "house",
    children: [
      {
        path: "/team/sciences",
        name: "TeamSciences",
        label: "科研动态",
        icon: "setting",
        url: "ScienceDynamic",
      },
      {
        path: "/team/activity",
        name: "TeamActivity",
        label: "团队活动",
        icon: "setting",
        url: "Activity",
      },
      {
        //发展
        path: "/team/development",
        name: "TeamDevelopment",
        label: "学生发展",
        icon: "setting",
        url: "Development",
      },
      {
        path: "/team/shoot",
        name: "TeamShoot",
        label: "照片墙",
        icon: "setting",
        url: "Shoot",
      },
    ],
  },
  {
    path: "/search",
    name: "search",
    label: "研究成果",
    icon: "house",
    children: [
      {
        path: "/search/project",
        name: "SearchProject",
        label: "研究项目",
        icon: "setting",
        url: "Project",
      },
      {
        path: "/search/paper",
        name: "SearchPaper",
        label: "论文管理",
        icon: "setting",
        url: "Paper",
      },
      {
        path: "/search/award",
        name: "SearchAward",
        label: "获奖管理",
        icon: "setting",
      },
    ],
  },
  {
    path: "/people",
    name: "people",
    label: "成员管理",
    icon: "house",
    children: [
      {
        path: "/people/teacher",
        name: "PeopleTeacher",
        label: "教师管理",
        icon: "setting",
        url: "Teacher",
      },
      {
        path: "/people/Instudent",
        name: "PeopleInStudent",
        label: "在校生管理",
        icon: "setting",
        url: "Student",
      },
      {
        path: "/people/Outstudent",
        name: "PeopleOutStudent",
        label: "毕业生管理",
        icon: "setting",
        url: "OutStudent",
      },
    ],
  },
  {
    path: "/share",
    name: "ResourceShare",
    label: "资源管理",
    icon: "house",
    url: "ResourceShare",
  },
]);
const noChild = computed(() => list.value.filter((item) => !item.children));
const hasChild = computed(() => list.value.filter((item) => item.children)); //看是否需要二级目录

const store = useDataStore();
const isCollapse = computed(() => store.state.isCollapse);
//侧边栏宽度
const widths = computed(() => (store.state.isCollapse ? "60px" : "180px"));
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
      <el-menu-item v-for="item in noChild" :key="item.path" :index="item.path">
        <component :is="item.icon" class="icons"></component>
        <span>{{ item.label }}</span>
      </el-menu-item>
      <el-sub-menu v-for="item in hasChild" :key="item.path" :index="item.path">
        <template #title>
          <component :is="item.icon" class="icons"></component>
          <span>{{ item.label }}</span>
        </template>
        <el-menu-item-group>
          <el-menu-item
            v-for="subItem in item.children"
            :key="subItem.path"
            :index="subItem.path"
          >
            <component :is="subItem.icon" class="icons"></component>
            {{ subItem.label }}
          </el-menu-item>
        </el-menu-item-group>
      </el-sub-menu>
    </el-menu>
  </el-aside>
</template>

<style scoped lang="less">
.icons {
  width: 18px;
  height: 18px;
  margin-right: 5px;
}
.el-menu {
  border-right: none;
  h3 {
    line-height: 48px;
    color: #fff;
    text-align: center;
    margin-bottom: 10px;
  }
}
.el-aside {
  height: 100%;
  background-color: #545c64;
}
</style>