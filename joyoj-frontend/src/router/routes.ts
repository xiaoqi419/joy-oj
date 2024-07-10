import { RouteRecordRaw } from 'vue-router'
import ACCESS_ENUM from '@/assets/enum/accessEnum'

// 提取出路由配置
export const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/Home/Home.vue'),
    meta: {
      title: '首页',
      icon: 'home',
      access: [ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: '/view/question',
    name: 'ViewQuestion',
    component: () => import('@/views/question/QuestionView.vue'),
    meta: {
      title: '浏览题目',
      icon: 'viewQuestion',
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN, ACCESS_ENUM.NOT_LOGIN]
    }
  },
  {
    path: '/view/question/:id',
    name: 'QuestionInfo',
    props: true,
    component: () => import('@/views/question/QuestionInfoView.vue'),
    meta: {
      title: '题目详情',
      access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  },
  {
    path: '/add/question',
    name: 'AddQuestion',
    component: () => import('@/views/question/AddQuestionView.vue'),
    meta: {
      title: '创建题目',
      icon: 'addQuestion',
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: '/update/question',
    name: 'UpdateQuestion',
    component: () => import('@/views/question/AddQuestionView.vue'),
    meta: {
      title: '更新题目',
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  },
  {
    path: '/manage/question',
    name: 'ManageQuestion',
    component: () => import('@/views/question/ManageQuestionView.vue'),
    meta: {
      title: '管理题目',
      icon: 'manageQuestion',
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: () => import('@/views/Home/Home.vue'),
    meta: {
      title: '后台管理',
      icon: 'admin',
      access: [ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: '/403',
    name: '403',
    component: () => import('@/views/Status/403.vue'),
    meta: {
      title: '无权限',
      access: [ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  },
  {
    path: '/404',
    name: '404',
    component: () => import('@/views/Status/404.vue'),
    meta: {
      title: '找不到页面',
      access: [ACCESS_ENUM.NOT_LOGIN, ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  },
  {
    path: '/profile',
    name: 'Profile',
    redirect: '/profile/info',
    component: () => import('@/components/Layouts/ProfileLayout.vue'),
    meta: {
      title: '个人中心',
      access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    },
    children: [
      {
        path: '/profile/info',
        name: 'ProfileInfo',
        component: () => import('@/views/Profile/ProfileInfo.vue'),
        meta: {
          title: '个人信息',
          access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
        }
      }
    ]
  }
]
