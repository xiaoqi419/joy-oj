import { App } from 'vue'
import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/home.vue')
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export const installRouter = (app: App) => {
  app.use(router)
}

export default router
