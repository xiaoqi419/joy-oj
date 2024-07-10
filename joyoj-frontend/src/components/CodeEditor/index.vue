<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { onMounted, ref, toRaw, watch } from 'vue'

type Props = {
  value: string
  language: string
}
const props = withDefaults(defineProps<Props>(), {
  value: () => '',
  language: () => 'javascript'
})
const codeEditorRef = ref()
const codeEditor = ref()

watch(
  () => props.language,
  () => {
    const lowCase = props.language.toLowerCase()
    monaco.editor.setModelLanguage(
      // 踩坑一定要使用toRaw
      toRaw(codeEditor.value).getModel(),
      lowCase
    )
  }
)

onMounted(() => {
  if (!codeEditorRef.value) {
    return
  }
  // Hover on each property to see its docs!
  codeEditor.value = monaco.editor.create(codeEditorRef.value, {
    value: props.value,
    language: props.language,
    automaticLayout: true,
    colorDecorators: true,
    minimap: {
      enabled: true
    },
    readOnly: false,
    theme: 'vs-dark'
    // lineNumbers: "off",
    // roundedSelection: false,
    // scrollBeyondLastLine: false,
  })
})
</script>

<template>
  <div id="code-editor" ref="codeEditorRef" style="min-height: 600px;height: 70vh"/>
</template>

<style scoped lang="scss">

</style>
