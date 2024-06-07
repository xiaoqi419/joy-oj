<script setup lang="ts">
import { defineComponent } from 'vue'
import BasicLayout from './components/layouts/BasicLayout.vue'
import UserModal from '@/components/modal/UserModal.vue'

defineComponent({
  name: 'App',
  components: {
    BasicLayout,
    UserModal
  }
})

</script>

<template>
  <div id="app">
    <BasicLayout/>
    <UserModal/>
    <a-back-top target-container="#app" :style="{position:'absolute'}">
      <icon-caret-up/>
    </a-back-top>
  </div>
</template>

<style scoped>
#app {
  position: relative;
}

</style>
