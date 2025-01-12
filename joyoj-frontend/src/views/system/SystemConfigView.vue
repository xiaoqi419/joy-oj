<script setup lang="ts"></script>

<template>
  <div id="systemConfigView">
    <a-card
      :style="{ width: '1450px', marginBottom: '20px', minHeight: '600px' }"
    >
      <div class="main-container">
        <a-layout style="height: 400px">
          <a-layout>
            <a-layout-sider
              breakpoint="xl"
              :width="220"
              collapsible
              :collapsed="collapsed"
              @collapse="onCollapse"
            >
              <a-menu
                :style="{ width: '200px', height: '100%' }"
                :default-open-keys="['0']"
                breakpoint="xl"
              >
                <a-sub-menu key="0">
                  <template #icon>
                    <joy-svg-icon icon="house-gear" class="mr-1" />
                  </template>
                  <template #title>基本配置</template>
                  <router-link to="/system/config/announce-settings">
                    <a-menu-item key="0_0">
                      <a-space>
                        <joy-svg-icon icon="config" class="mr-1" />
                        公告设置
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

                    <router-view></router-view>
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
