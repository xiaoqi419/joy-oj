<template>
  <Viewer :value="props.value" :plugins="plugins" />
</template>

<script setup lang="ts">
import gfm from "@bytemd/plugin-gfm";
import highlight from "@bytemd/plugin-highlight";
import { Viewer } from "@bytemd/vue-next";
import gemoji from "@bytemd/plugin-gemoji";
import { defineComponent } from "vue";

type Props = {
  value: string;
};

defineComponent({
  name: "MdEditor"
});

const plugins = [
  gfm(),
  highlight(),
  gemoji()
  // Add more plugins here
];

const props = withDefaults(defineProps<Props>(), {
  value: () => ""
});
</script>

<style scoped lang="scss">
::v-deep(.bytemd-toolbar-icon.bytemd-tippy.bytemd-tippy-right:last-child) {
  display: none;
}
</style>
