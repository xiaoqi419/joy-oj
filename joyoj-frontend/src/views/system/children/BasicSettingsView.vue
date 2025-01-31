<script setup lang="ts">
import { ref } from "vue";

const form = ref({
  announcement: "",
  websiteName: "",
  isRead: false
});
</script>

<template>
  <div>
    <a-alert>这里可以更改系统为属于你的独家特色🫠</a-alert>
    <a-form
      :model="form"
      :style="{ marginTop: '10px', width: '100%' }"
      size="large"
      label-align="left"
      :label-col-props="{ span: 2 }"
    >
      <a-form-item
        field="websiteName"
        label="网站名称"
        tooltip="全局替换网站名称和水印,最大为20字符"
        :style="{ marginTop: '10px' }"
      >
        <a-input
          :v-model="form.websiteName"
          allow-clear
          placeholder="请输入你的网站名称"
          max-length="20"
        ></a-input>
      </a-form-item>
      <a-form-item
        field="announcement"
        tooltip="内容将替换首页公告内容"
        label="公告内容"
        :style="{ marginTop: '10px' }"
      >
        <a-textarea
          v-model="form.announcement"
          placeholder="请输入你的公告内容"
          :autosize="{ minRows: 2, maxRows: 6 }"
          :auto-size="true"
          :show-word-limit="true"
          :allow-clear="true"
          :max-length="{ length: 200 }"
          :style="{ height: '200px' }"
        />
      </a-form-item>
      <a-form-item>
        <a-button html-type="submit" type="primary">提交</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style scoped lang="scss"></style>
