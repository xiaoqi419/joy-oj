<template>
  <Editor :value="props.value" :mode="mode" :plugins="plugins" @change="props.handleChange"/>
</template>

<script setup lang="ts">
import gfm from '@bytemd/plugin-gfm'
import highlight from '@bytemd/plugin-highlight'
import { Editor } from '@bytemd/vue-next'
import gemoji from '@bytemd/plugin-gemoji'
import { defineComponent } from 'vue'

type Props = {
  value: string
  mode?: string
  handleChange: (v: string) => void;
}

defineComponent({
  name: 'MdEditor'
})

const plugins = [
  gfm(),
  highlight(),
  gemoji()
  // Add more plugins here
]

const props = withDefaults(defineProps<Props>(), {
  value: () => '',
  mode: () => 'split',
  handleChange: (v: string) => {
    console.log(v)
  }
})

</script>

<style scoped lang="scss">
::v-deep(.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child) {
  display: none;
}
</style>
