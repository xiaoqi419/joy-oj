<script setup lang="ts">
import { defineComponent } from 'vue'

defineComponent({
  name: 'ProfileLayout',
  components: {}

})
</script>

<template>
  <div id="profile-box">
    <a-layout style="height: 1200px;">
      <a-layout class="pd-box">
        <a-layout-sider class='sider' theme="dark">Sider</a-layout-sider>
        <a-layout-content class="content">
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </div>
</template>

<style scoped lang="scss">
#profile-box {
  background: #f8f6f6;

}
</style>
