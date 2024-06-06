<script setup lang="ts">
import { defineComponent } from 'vue'
import GlobalNavbar from '@/components/GlobalNavbar/index.vue'

defineComponent({
  name: 'BasicLayout',
  components: { GlobalNavbar }
})

</script>

<template>
  <a-layout style="height: 400px;">
    <a-layout-header>
      <!-- 导航 -->
      <GlobalNavbar/>
    </a-layout-header>
    <a-layout-content>
      <router-view/>
    </a-layout-content>
    <a-layout-footer>Footer</a-layout-footer>
  </a-layout>

</template>

<style scoped lang="scss">

</style>
