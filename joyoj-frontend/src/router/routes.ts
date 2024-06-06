import { RouteRecordRaw } from 'vue-router'

// 提取出路由配置
export const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/home.vue'),
    meta: {
      title: '首页'
    }
  },
  {
    path: '/tk',
    name: 'tk',
    component: () => import('@/views/home.vue'),
    meta: {
      title: '题库'
    }
  }
]
