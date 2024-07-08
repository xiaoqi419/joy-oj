<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { onMounted, ref, toRaw } from 'vue'

const codeEditorRef = ref()
const codeEditor = ref()
const value = ref('hello world')

onMounted(() => {
  if (!codeEditorRef.value) {
    return
  }
  // Hover on each property to see its docs!
  codeEditor.value = monaco.editor.create(codeEditorRef.value, {
    value: value.value,
    language: 'java',
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

  // 编辑 监听内容变化
  codeEditor.value.onDidChangeModelContent(() => {
    console.log('目前内容为：', toRaw(codeEditor.value).getValue())
  })
})
</script>

<template>
  <div id="code-editor" ref="codeEditorRef" style="min-height: 400px"/>
</template>

<style scoped lang="scss">

</style>
