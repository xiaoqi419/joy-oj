<script setup lang="ts">
import { defineComponent, onMounted } from 'vue'
import BasicLayout from '@/components/Layouts/BasicLayout.vue'
import UserModal from '@/components/Modal/UserModal.vue'
import RegisterModal from '@/components/Modal/RegisterModal.vue'

defineComponent({
  name: 'App',
  components: {
    BasicLayout,
    UserModal,
    RegisterModal
  }
})

/**
 * 初始化函数，用于初始化数据
 */
const init = () => {
  // 初始化数据
  console.log('欢迎使用Joy Judge！')
}

onMounted(() => {
  init()
})
</script>

<template>
  <div id="app">
    <BasicLayout/>
    <UserModal/>
    <RegisterModal/>
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
