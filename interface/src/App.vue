<script setup>
// import { RouterLink, RouterView } from 'vue-router'
import router from './router';
import CommonFooter from './components/CommonFooter.vue';
</script>

<template>
  <div id="app">
    <router-view></router-view>
    <CommonFooter />
  </div>
</template>

<style scoped>
#app {
  width: 100%;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}
</style>
