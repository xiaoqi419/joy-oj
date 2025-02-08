<script setup lang="ts">
import { ref } from "vue";
import { Message } from "@arco-design/web-vue";

// 题解部分
const text = ref(
  "::: tip\n" +
    "  在下方展示你的独家方法吧~:smiling_imp:\n" +
    ":::\n" +
    "\n" +
    "# 思路\n" +
    "> 你选用何种方法解题？\n" +
    "\n" +
    "# 解题过程\n" +
    "> 这些方法具体怎么运用？\n" +
    "\n" +
    "# Code\n" +
    "``` language\n" +
    "\n" +
    "```"
);
const handleCopyCodeSuccess = () => {
  Message.success("代码复制成功~");
};

// 标签自动搜索
// const tagData = ref();
</script>

<template>
  <div id="AddSolution">
    <a-layout style="height: auto">
      <a-layout-header>
        <a-card :style="{ marginBottom: '0.3125rem' }" :hoverable="true">
          <a-row class="form-input-title" align="center">
            <a-col :flex="9">
              <div>
                <a-input
                  default-value=""
                  placeholder="请输入标题"
                  allow-clear
                  size="large"
                />
              </div>
            </a-col>
            <a-col :flex="1">
              <div :style="{ textAlign: center }">草稿已保存</div>
            </a-col>
            <a-col :flex="1">
              <div>
                <a-button type="primary" :style="{ borderRadius: '.5rem' }">
                  <template #icon>
                    <icon-thunderbolt />
                  </template>
                  发布题解
                </a-button>
              </div>
            </a-col>
          </a-row>
          <div :style="{ marginTop: '.7rem' }">
            <a-trigger
              trigger="click"
              :unmount-on-close="false"
              show-arrow
              :popup-translate="[50, 0]"
            >
              <a-button type="primary" shape="round" status="success">
                <template #icon>
                  <icon-plus />
                </template>
                标签
              </a-button>
              <template #content>
                <div class="trigger">
                  <div>
                    <a-input placeholder="搜索标签" allow-clear>
                      <template #prefix>
                        <icon-search />
                      </template>
                    </a-input>
                  </div>
                  <a-empty />
                </div>
              </template>
            </a-trigger>
          </div>
        </a-card>
      </a-layout-header>
      <a-layout-content>
        <v-md-editor
          v-model="text"
          height="43.75rem"
          left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol todo-list table hr | link image code | tip emoji"
          @copy-code-success="handleCopyCodeSuccess"
        ></v-md-editor>
      </a-layout-content>
    </a-layout>
  </div>
</template>

<style scoped lang="scss">
#AddSolution {
  padding: 1.25rem 9.375rem;

  .form-input-title {
    text-align: center;
  }
}

.trigger {
  padding: 0.625rem;
  width: 12.5rem;
  background-color: var(--color-bg-popup);
  border-radius: 0.25rem;
  box-shadow: 0 0.125rem 0.5rem 0 rgba(0, 0, 0, 0.15);
}
</style>
