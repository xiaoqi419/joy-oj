import nProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { storeToRefs } from 'pinia'
import { Router } from 'vue-router'
import useUserStore from '@/store/modules/user'

nProgress.configure({
  showSpinner: false
})

export const installRouterGuards = (router: Router) => {
  // 全局前置守卫
  router.beforeEach((to, from, next) => {
    nProgress.start()

    // 如果访问的路由在路由中不存在，则跳转到404页面
    if (!to.matched.length) {
      return next({ name: '404' })
    }

    const userStore = useUserStore()
    const { userInfo } = storeToRefs(userStore)
    // 如果用户未登录，且访问的没有权限，则跳转到无权限界面
    if (userInfo.value.access !== 'admin') {
      // @ts-ignore
      if (to.meta.access && to.meta.access.includes('admin') && to.meta.access.length === 1) {
        return next({ name: '403' })
      }
    }
    return next()
  })

  // 全局后置钩子
  router.afterEach(() => {
    nProgress.done(true)
  })
}
