<script setup lang="ts">
import { useRouter } from "vue-router";

defineOptions({
  name: "403"
});

const router = useRouter();
</script>

<template>
  <div class="flex justify-center items-center h-[640px]">
    <joy-svg-icon
      class="mr-12 animate__animated animate__fadeInUp"
      icon="403"
      :style="{ width: '300px', height: '500px' }"
    />
    <div class="ml-12 animate__animated animate__fadeInUp">
      <p
        class="font-medium text-4xl mb-4 dark:text-white"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 80
          }
        }"
      >
        403
      </p>
      <p
        class="mb-4 text-gray-500"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 120
          }
        }"
      >
        抱歉，你无权访问该页面
      </p>
      <a-button
        type="primary"
        :initial="{
          opacity: 0,
          y: 100
        }"
        :enter="{
          opacity: 1,
          y: 0,
          transition: {
            delay: 160
          }
        }"
        @click="router.push('/')"
      >
        返回首页
      </a-button>
    </div>
  </div>
</template>
