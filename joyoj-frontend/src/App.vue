<script setup lang="ts">
import { defineComponent } from "vue";
import BasicLayout from "@/components/Layouts/BasicLayout.vue";
import UserModal from "@/components/Modal/UserModal.vue";
import RegisterModal from "@/components/Modal/RegisterModal.vue";
import ForgetModal from "@/components/Modal/ForgetModal.vue";
import useModalStore from "@/store/modules/modal";
import { storeToRefs } from "pinia";
import { SystemConfigControllerService } from "../generated";
import useSystemStore from "@/store/modules/system";
import { Message } from "@arco-design/web-vue";

defineComponent({
  name: "App",
  components: {
    BasicLayout,
    UserModal,
    RegisterModal,
    ForgetModal
  }
});

/**
 * 初始化函数，用于初始化数据
 */
const init = async () => {
  // 初始化数据
  console.log("欢迎使用Joy Judge！");
  // 获取系统配置并保存在Store中，只执行一次
  const basicConfig =
    await SystemConfigControllerService.getBasicConfigUsingGet();
  if (basicConfig.code === 20000) {
    useSystemStore().setBasic(basicConfig.data);
  } else {
    Message.error("获取数据失败:" + basicConfig.message);
  }
};
const modalStore = storeToRefs(useModalStore());

init();
</script>

<template>
  <div id="app">
    <BasicLayout class="layout" />
    <UserModal v-if="modalStore.userModal.value" />
    <RegisterModal v-if="modalStore.registerModal.value" />
    <ForgetModal v-if="modalStore.forgetModal.value" />
    <a-back-top>
      <a-button type="primary" shape="circle">
        <icon-caret-up />
      </a-button>
    </a-back-top>
  </div>
</template>

<style scoped>
#app {
  position: relative;
}
</style>
