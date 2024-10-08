<script setup lang="ts">
import { ref } from 'vue'

const searchTitle = ref()
const handleSelect = (v: any) => {
  console.log(v)
}
</script>

<template>
  <div id="solution">
    <a-row class="grid-demo" style="margin-bottom: 16px;">
      <a-col flex="85%">
        <div>
          <a-input v-model='searchTitle' placeholder="搜索" allow-clear>
            <template #prepend>
              <icon-search/>
            </template>
          </a-input>
        </div>
      </a-col>
      <a-col flex="auto">
        <div class="ml-1">
          <a-dropdown @select="handleSelect">
            <a-button type="text">
              <template #icon>
                <icon-sort/>
              </template>
              排序
            </a-button>
            <template #content>
              <a-doption>点赞最多</a-doption>
              <a-doption>热度最高</a-doption>
              <a-doption>最新发布</a-doption>
              <a-doption>最早发布</a-doption>
            </template>
          </a-dropdown>

        </div>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped lang="scss">
#solution {
  height: 100%;
  width: 100%;

}
</style>
