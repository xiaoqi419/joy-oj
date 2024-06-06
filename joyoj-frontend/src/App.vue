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
  </div>
</template>
