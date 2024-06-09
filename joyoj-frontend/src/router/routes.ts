import { RouteRecordRaw } from 'vue-router'
import ACCESS_ENUM from '@/assets/enum/accessEnum'

// 提取出路由配置
export const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home.vue'),
    meta: {
      title: '首页',
      access: [ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: '/tk',
    name: 'tk',
    component: () => import('@/views/Home.vue'),
    meta: {
      title: '题库',
      access: [ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/Home.vue'),
    meta: {
      title: '后台管理',
      access: [ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: '/403',
    name: '403',
    component: () => import('@/views/403.vue'),
    meta: {
      title: '无权限',
      access: [ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/404.vue'),
    meta: {
      title: '找不到页面',
      access: [ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  }
]
