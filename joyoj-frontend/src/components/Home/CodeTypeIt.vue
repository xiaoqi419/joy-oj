<script setup lang="ts">
import { defineComponent, ref } from "vue";
import { TypeIt, type TypeItOptions } from "@/components/ReTypeit";
import "animate.css";

defineComponent({
  name: "CodeTypeIt",
  components: {
    TypeIt
  }
});

const options: TypeItOptions = {
  strings: [
    '<span class="function">console.log</span>(<span class="string">"Hello, world!"</span>);',
    " ",
    '<span class="function">print</span>("<span class="string">Hello, World!</span>")',
    " ",
    '<span class="public">public class</span> HelloWorld {\n' +
      '    <span class="public">public static void</span> main(<span class="keyword">String[]</span> args) {\n' +
      '        <span class="function">System.out.println</span>("<span class="string">Hello, World!</span>");\n' +
      "    }\n" +
      "}"
  ],
  speed: 30,
  html: true
};
const codeEditor = ref(null);
</script>

<template>
  <div class="code-container animate__animated animate__fadeInUp">
    <div class="mac-buttons">
      <div class="mac-button close"></div>
      <div class="mac-button minimize"></div>
      <div class="mac-button maximize"></div>
    </div>
    <div class="code-editor" ref="codeEditor">
      <TypeIt :options="options" />
    </div>
  </div>
</template>

<style scoped lang="scss">
.code-container {
  background-color: #1e1e1e;
  border-radius: 10px;
  padding: 20px;
  color: #abb2bf;
  font-family: "Courier New", Courier, monospace;
  font-size: 16px;
  line-height: 1.5;
  white-space: pre;
  overflow: auto;
  box-shadow: 8px 8px 10px 0 rgba(0, 0, 0, 0.26);
}

.code-editor {
  background-color: #282c34;
  border-radius: 5px;
  padding: 20px;
  color: #abb2bf;
  font-family: "Courier New", Courier, monospace;
  font-size: 16px;
  line-height: 1.5;
  white-space: pre;
  overflow: auto;

  :deep(.keyword) {
    color: #569cd6;
  }

  :deep(.string) {
    color: #e76a37;
  }

  :deep(.function) {
    color: #dcdcaa;
  }

  :deep(.comment) {
    color: #6a9955;
  }

  :deep(.type-it) {
    font-size: 18px;
  }

  :deep(.public) {
    color: #8b31d2;
  }
}

.mac-buttons {
  display: flex;
  justify-content: flex-start;
  padding: 10px;

  .mac-button {
    width: 12px;
    height: 12px;
    border-radius: 50%;
    margin-right: 8px;
  }

  .close {
    background-color: #ff5f57;
  }

  .minimize {
    background-color: #ffbd2e;
  }

  .maximize {
    background-color: #27c93f;
  }
}
</style>
