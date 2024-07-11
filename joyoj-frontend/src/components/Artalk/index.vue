<script lang="ts" setup>
import Artalk from 'artalk'
import { nextTick, onBeforeUnmount, onMounted, ref, watch } from 'vue'

import 'artalk/dist/Artalk.css'
import { useRoute } from 'vue-router'

const el = ref<HTMLElement>()
const route = useRoute()

let artalk: Artalk

onMounted(() => {
  artalk = Artalk.init({
    el: el.value,
    pageKey: route.path,
    pageTitle: document.title,
    server: 'https://artalk.ojason.top',
    site: 'Joy Judge'
    // ...
  })
})

watch(
  () => route.path,
  (path) => {
    nextTick(() => {
      artalk.update({
        pageKey: path,
        pageTitle: document.title
      })
      artalk.reload()
    })
  }
)

onBeforeUnmount(() => {
  artalk.destroy()
})
</script>

<template>
  <view ref="el"></view>
</template>
