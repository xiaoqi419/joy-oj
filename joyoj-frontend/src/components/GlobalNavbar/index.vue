<script setup lang="ts">
import { computed, defineComponent, ref } from "vue";
import { useRouter } from "vue-router";
import { routes } from "@/router/routes";
import useModalStore from "@/store/modules/modal";
import useUserStore from "@/store/modules/user";
import { storeToRefs } from "pinia";
import checkAccess from "@/utils/checkAccess";
import { Message } from "@arco-design/web-vue";
import { UserControllerService } from "../../../generated";

defineComponent({
  name: "GlobalNavbar"
});
const router = useRouter();
// 跳转到首页
const jumpHome = () => {
  router.push("/");
};
// 动态菜单跳转
const doMenuClick = (key: string) => {
  router.push(key);
};
// 默认主页
const selectedKeys = ref(["/"]);
// 路由跳转后，更新选中菜单
router.afterEach(to => {
  selectedKeys.value = [to.path];
});
// 显示登录模态框
const modalStore = useModalStore();
const showLoginModal = () => {
  modalStore.userModal = true;
};
// 获取用户信息
const { userInfo } = storeToRefs(useUserStore());
// 展示的路由
const visibleRoutes = computed(() => {
  return routes.filter(routes => {
    if (routes.meta?.hidden) {
      return false;
    }
    return checkAccess(userInfo.value, routes.meta?.access);
  });
});

// 展示注册模态框
const showRegisterModal = () => {
  modalStore.registerModal = true;
};

// 退出登录
const doLogout = async () => {
  const res = await UserControllerService.userLogoutUsingPost();
  if (res.code === 20000) {
    useUserStore().resetUserInfo();
    Message.success("退出登录成功");
    await router.push("/");
  } else {
    Message.error("退出登录失败");
  }
};

// 个人中心

const goProfile = () => {
  router.push("/profile");
};

// 获取网站配置
const { basic } = JSON.parse(localStorage.getItem("systems")!);
const websiteName = basic.websiteName;
</script>

<template>
  <div id="GlobalNavbar">
    <a-row align="center">
      <a-col flex="auto">
        <a-menu
          mode="horizontal"
          :default-selected-keys="['/']"
          :selected-keys="selectedKeys"
          @menu-item-click="doMenuClick"
        >
          <a-menu-item
            key="0"
            :style="{ padding: 0, marginRight: '38px' }"
            disabled
          >
            <div class="title-bar" @click="jumpHome">
              <!--              <img class="logo" src="../../assets/images/logo.png"/>-->
              <div class="title">{{ websiteName }}</div>
            </div>
          </a-menu-item>
          <a-menu-item v-for="item in visibleRoutes" :key="item.path">
            <template #icon>
              <div
                style="
                  display: flex;
                  justify-content: center;
                  align-items: center;
                "
              >
                <joy-svg-icon :icon="item.meta?.icon" class="mr-1" />
                {{ item.meta?.title }}
              </div>
            </template>
          </a-menu-item>
        </a-menu>
      </a-col>
      <a-col flex="100px">
        <div
          style="
            width: 100%;
            background: #fff;
            height: 60px;
            display: flex;
            align-items: center;
          "
        >
          <a-dropdown trigger="hover">
            <a-avatar
              v-if="userInfo.userAvatar === ''"
              :style="{ backgroundColor: '#3370ff' }"
            >
              <IconUser />
            </a-avatar>
            <a-avatar v-else>
              <img
                alt="avatar"
                :src="userInfo.userAvatar"
                style="width: 100%; height: 100%"
              />
            </a-avatar>
            <template #content>
              <a-menu v-if="userInfo.userName === ''">
                <a-menu-item
                  key="1"
                  style="text-align: center"
                  @click="showLoginModal"
                  >登录
                </a-menu-item>
                <a-menu-item key="2" @click="showRegisterModal"
                  >去注册
                </a-menu-item>
              </a-menu>
              <a-menu v-else>
                <a-menu-item key="3" @click="goProfile">个人中心</a-menu-item>
                <a-menu-item key="4" @click="doLogout">退出登录</a-menu-item>
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
