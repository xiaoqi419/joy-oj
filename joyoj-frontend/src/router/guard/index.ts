import nProgress from "nprogress";
import "nprogress/nprogress.css";
import { storeToRefs } from "pinia";
import { Router } from "vue-router";
import useUserStore from "@/store/modules/user";
import checkAccess from "@/utils/checkAccess";
import ACCESS_ENUM from "@/assets/enum/accessEnum";
import useModalStore from "@/store/modules/modal";
import { Message } from "@arco-design/web-vue";

nProgress.configure({
  showSpinner: false
});

export const installRouterGuards = (router: Router) => {
  // 全局前置守卫
  router.beforeEach(async (to, from, next) => {
    nProgress.start();

    // 如果访问的路由在路由中不存在，则跳转到404页面
    if (!to.matched.length) {
      return next({ name: "404" });
    }

    const userStore = useUserStore();
    const modalStore = useModalStore();
    const { userInfo } = storeToRefs(userStore);
    // 如果用户未登录,自动登录
    if (!userInfo.value || !userInfo.value.userRole) {
      // 加 await 是为了等用户登录成功之后，再执行后续的代码
      await userStore.getLoginUser();
    }
    // 如果访问的没有权限，则跳转到无权限界面
    const needAccess = to.meta?.access as Array<any>;
    // 要跳转的页面必须要登陆
    if (!needAccess.includes(ACCESS_ENUM.NOT_LOGIN)) {
      // 如果没登陆，打开登录弹窗,并跳转回首页
      if (
        !userInfo.value ||
        userInfo.value.userRole === ACCESS_ENUM.NOT_LOGIN ||
        !userInfo.value.userRole
      ) {
        next("/");
        Message.warning("请先登录!");
        modalStore.setUserModal(true);
        return;
      }
      // 如果已经登陆了，但是权限不足，那么跳转到无权限页面
      if (!checkAccess(userInfo.value, needAccess)) {
        next("/403");
        return;
      }
    }
    next();
  });

  // 全局后置钩子
  router.afterEach(() => {
    nProgress.done(true);
  });
};
