<script setup lang="ts">
import { ref } from "vue";
import Vcode from "vue3-puzzle-vcode";

const isShow = ref(true);
const emit = defineEmits(["success", "fail"]);

function onSuccess() {
  emit("success", { success: true });
}

function onFail() {
  emit("fail", { success: false });
}
</script>

<template>
  <div class="">
    <Vcode :show="isShow" type="inside" @fail="onFail" @success="onSuccess" />
  </div>
</template>

<style lang="scss" scoped>
:deep(.vue-puzzle-vcode.inside_.show_) {
  width: 310px;
}

:deep(.vue-auth-box_) {
  width: 310px;
}
</style>
