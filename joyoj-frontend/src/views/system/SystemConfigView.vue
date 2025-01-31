<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";

const isShowIntro = ref(true);
const route = useRoute();
const selectedKeys = ref([route.path]); // 初始化为当前路径
// 页面加载时,从localStorage中获取isShowIntro
onMounted(() => {
  const storedIsShowIntro = localStorage.getItem("isShowIntro");
  if (storedIsShowIntro) {
    isShowIntro.value = storedIsShowIntro === "true";
  }
  watch(
    () => route.path,
    () => {
      isShowIntro.value = route.path === "/system/config";
    },
    { immediate: true }
  );
  // 将isShowIntro存入localStorage
  localStorage.setItem("isShowIntro", isShowIntro.value.toString());
  // 监听路由变化
  watch(
    () => route.path,
    newPath => {
      selectedKeys.value = [newPath];
    }
  );
});
console.log(selectedKeys.value);
</script>

<template>
  <div id="systemConfigView">
    <a-card
      :style="{
        width: '1450px',
        marginBottom: '20px',
        minHeight: '600px',
        height: 'auto'
      }"
    >
      <div class="main-container">
        <a-layout style="height: 400px">
          <a-layout>
            <a-layout-sider breakpoint="xl" :width="220" collapsible>
              <a-menu
                :style="{ width: '200px', height: '100%' }"
                breakpoint="xl"
                :selected-keys="selectedKeys"
                :default-open-keys="['0']"
              >
                <a-sub-menu key="0">
                  <template #icon>
                    <joy-svg-icon icon="house-gear" class="mr-1" />
                  </template>
                  <template #title>系统配置</template>
                  <router-link to="/system/config/announce-settings">
                    <a-menu-item key="/system/config/announce-settings">
                      <a-space>
                        <joy-svg-icon icon="config" class="mr-1" />
                        基础设置
                      </a-space>
                    </a-menu-item>
                  </router-link>
                  <a-menu-item key="0_1">Menu 2</a-menu-item>
                  <a-menu-item key="0_2">Menu 3</a-menu-item>
                  <a-menu-item key="0_3">Menu 4</a-menu-item>
                </a-sub-menu>
              </a-menu>
            </a-layout-sider>
            <a-layout-content>
              <!-- 路由 -->
              <div class="content-container">
                <a-watermark content="Joy Judge">
                  <a-card
                    :style="{ width: '100%', minHeight: '500px' }"
                    hoverable
                  >
                    <!-- 未选中配置菜单时显示的 -->
                    <div v-if="isShowIntro">
                      <a-typography :style="{ marginTop: '-40px' }">
                        <a-typography-title> 系统配置</a-typography-title>
                        <a-typography-paragraph blockquote>
                          <p :style="{ fontSize: '15px' }">
                            在系统配置中可以进行系统的各种基础配置和网站信息设置，包括
                            <a-typography-text type="primary">
                              网站标题
                            </a-typography-text>
                            、
                            <a-typography-text type="primary">
                              网站描述
                            </a-typography-text>
                            、
                            <a-typography-text type="primary">
                              LOGO
                            </a-typography-text>
                            等。
                          </p>
                        </a-typography-paragraph>
                        <a-typography-paragraph>
                          <p :style="{ fontSize: '17px' }">
                            点击左侧菜单进行配置。
                          </p>
                        </a-typography-paragraph>
                      </a-typography>
                    </div>
                    <router-view v-else></router-view>
                  </a-card>
                </a-watermark>
              </div>
            </a-layout-content>
          </a-layout>
        </a-layout>
      </div>
    </a-card>
  </div>
</template>

<style scoped lang="scss">
#systemConfigView {
  max-width: 1450px;
  margin: 20px auto;

  .content-container {
    padding: 20px;
  }
}
</style>
