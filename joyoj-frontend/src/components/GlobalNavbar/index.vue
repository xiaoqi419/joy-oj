<script setup lang="ts">
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'
import { routes } from '@/router/routes'
import useModalStore from '@/store/modules/modal'

defineComponent({
  name: 'GlobalNavbar'
})
const router = useRouter()

// 跳转到首页
const jumpHome = () => {
  router.push('/')
}

// 动态菜单跳转
const doMenuClick = (key: string) => {
  router.push(key)
}

// 默认主页
const selectedKeys = ref(['/'])
// 路由跳转后，更新选中菜单
router.afterEach((to) => {
  selectedKeys.value = [to.path]
})

// 显示登录模态框
const modalStore = useModalStore()
const showLoginModal = () => {
  modalStore.userModal = true
}

</script>

<template>
  <div id="GlobalNavbar">
    <a-row align="center">
      <a-col flex="auto">
        <a-menu mode="horizontal" :default-selected-keys="['/']" :selected-keys="selectedKeys"
                @menu-item-click="doMenuClick">
          <a-menu-item key="0" :style="{ padding: 0, marginRight: '38px' }" disabled>
            <div class="title-bar" @click="jumpHome">
              <!--              <img class="logo" src="../../assets/images/logo.png"/>-->
              <div class="title">JOY Judge</div>
            </div>
          </a-menu-item>
          <a-menu-item v-for="item in routes" :key="item.path">
            <template #icon>
              <icon-home/>
              {{ item.meta?.title }}
            </template>
          </a-menu-item>
        </a-menu>
      </a-col>
      <a-col flex="100px">
        <div>
          <a-dropdown trigger="hover">
            <a-avatar :style="{ backgroundColor: '#3370ff' }">
              <IconUser/>
            </a-avatar>
            <template #content>
              <a-menu>
                <a-menu-item key="1" style="text-align: center" @click="showLoginModal">登录</a-menu-item>
                <a-menu-item key="2">去注册</a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-col>
    </a-row>
  </div>
</template>

<style scoped lang="scss">
#GlobalNavbar {
  border-bottom: 1px solid #ccc; /* 添加底部分割线 */
  :deep(.arco-menu-icon) {
    margin-right: 0;
    font-size: 16px;
  }

  .title-bar {
    display: flex;
    align-items: center;
    font-size: 18px;
    width: auto;
    cursor: pointer;

    .logo {
      height: 45px;
    }

    .title {
      margin-left: 16px;
      font-weight: bolder;
      color: #444;

    }
  }

}
</style>
