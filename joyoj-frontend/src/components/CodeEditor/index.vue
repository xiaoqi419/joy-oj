<script setup lang="ts">
import * as monaco from 'monaco-editor'
import { onMounted, ref, toRaw, watch } from 'vue'

type Props = {
  value: string
  language: string,
  handleChange: (v: string) => void;
}
// todo 更改语言时代码不同
const codeDefault = 'public class Main {\n' +
  '\n' +
  '    ' + '//' + ' ' + '请勿修改类名和方法名等代码' +
  '\n' +
  '    public static void main(String[] args) {\n' +
  '       \n' +
  '    }\n' +
  '\n' +
  '}'

const props = withDefaults(defineProps<Props>(), {
  value: () => codeDefault,
  language: () => 'java',
  handleChange: (v: string) => {
    console.log(v)
  }
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
    value: codeDefault,
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
    props.handleChange(toRaw(codeEditor.value).getValue())
  })
})
</script>

<template>
  <div id="code-editor" ref="codeEditorRef" style="min-height: 600px;height: 70vh"/>
</template>

<style scoped lang="scss">

</style>
