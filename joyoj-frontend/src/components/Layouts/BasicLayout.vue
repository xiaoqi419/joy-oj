<script setup lang="ts">
import { defineComponent } from 'vue'
import GlobalNavbar from '@/components/GlobalNavbar/index.vue'
import GlobalFooter from '@/components/Footer/index.vue'

defineComponent({
  name: 'BasicLayout',
  components: {
    GlobalNavbar,
    GlobalFooter
  }
})

</script>

<template>
  <a-layout style="height: 400px;" class="layout">
    <a-layout-header>
      <!-- 导航 -->
      <GlobalNavbar/>
    </a-layout-header>
    <a-layout-content>
      <router-view/>
    </a-layout-content>
    <a-layout-footer>
      <GlobalFooter/>
    </a-layout-footer>
  </a-layout>

</template>

<style scoped lang="scss">
.layout {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 100vh;
}
</style>
