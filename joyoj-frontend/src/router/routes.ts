import { RouteRecordRaw } from "vue-router";
import ACCESS_ENUM from "@/assets/enum/accessEnum"; // 提取出路由配置

// 提取出路由配置
export const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "Home",
    component: () => import("@/views/home/Home.vue"),
    meta: {
      title: "首页",
      icon: "home",
      access: [
        ACCESS_ENUM.NOT_LOGIN,
        ACCESS_ENUM.STUDENT,
        ACCESS_ENUM.TEACHER,
        ACCESS_ENUM.ADMIN
      ]
    }
  },
  {
    path: "/post-editor",
    name: "postEditor",
    meta: {
      title: "编辑器",
      access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    },
    children: [
      {
        path: "/post-editor/solution/:id",
        name: "solution",
        component: () => import("@/components/Solution/AddSolution.vue"),
        meta: {
          title: "发布题解",
          access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
          hidden: true
        }
      }
    ]
  },
  {
    path: "/about",
    name: "About",
    component: () => import("@/views/home/About.vue"),
    meta: {
      title: "关于我们",
      hidden: true,
      access: [
        ACCESS_ENUM.NOT_LOGIN,
        ACCESS_ENUM.STUDENT,
        ACCESS_ENUM.TEACHER,
        ACCESS_ENUM.ADMIN
      ]
    }
  },
  {
    path: "/view/question",
    name: "ViewQuestion",
    component: () => import("@/views/question/QuestionView.vue"),
    meta: {
      title: "浏览题目",
      icon: "viewQuestion",
      access: [
        ACCESS_ENUM.TEACHER,
        ACCESS_ENUM.ADMIN,
        ACCESS_ENUM.NOT_LOGIN,
        ACCESS_ENUM.STUDENT
      ]
    }
  },
  {
    path: "/view/question_submit",
    name: "ViewQuestionSubmit",
    component: () => import("@/views/question/QuestionSubmitView.vue"),
    meta: {
      title: "浏览题目提交",
      icon: "viewQuestionSubmit",
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN, ACCESS_ENUM.STUDENT]
    }
  },
  {
    path: "/view/question/:id",
    name: "QuestionInfo",
    props: true,
    component: () => import("@/views/question/QuestionInfoView.vue"),
    meta: {
      title: "题目详情",
      access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  },
  {
    path: "/add/question",
    name: "AddQuestion",
    component: () => import("@/views/question/AddQuestionView.vue"),
    meta: {
      title: "创建题目",
      icon: "addQuestion",
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: "/update/question",
    name: "UpdateQuestion",
    component: () => import("@/views/question/AddQuestionView.vue"),
    meta: {
      title: "更新题目",
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    }
  },
  {
    path: "/manage/question",
    name: "ManageQuestion",
    component: () => import("@/views/question/ManageQuestionView.vue"),
    meta: {
      title: "管理题目",
      icon: "manageQuestion",
      access: [ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: "/user/manage",
    name: "UserManage",
    component: () => import("@/views/user/UserManageView.vue"),
    meta: {
      title: "用户管理",
      icon: "admin",
      access: [ACCESS_ENUM.ADMIN]
    }
  },
  {
    path: "/system/config",
    name: "SystemConfig",
    component: () => import("@/views/system/SystemConfigView.vue"),
    meta: {
      title: "系统配置",
      icon: "config",
      access: [ACCESS_ENUM.ADMIN]
    },
    children: [
      {
        path: "/system/config/announce-settings",
        name: "AnnounceSettings",
        component: () => import("@/views/system/children/BasicSettingsView.vue")
      }
    ]
  },
  {
    path: "/403",
    name: "403",
    component: () => import("@/views/status/403.vue"),
    meta: {
      title: "无权限",
      access: [
        ACCESS_ENUM.NOT_LOGIN,
        ACCESS_ENUM.STUDENT,
        ACCESS_ENUM.TEACHER,
        ACCESS_ENUM.ADMIN
      ],
      hidden: true
    }
  },
  {
    path: "/404",
    name: "404",
    component: () => import("@/views/status/404.vue"),
    meta: {
      title: "找不到页面",
      access: [
        ACCESS_ENUM.NOT_LOGIN,
        ACCESS_ENUM.STUDENT,
        ACCESS_ENUM.TEACHER,
        ACCESS_ENUM.ADMIN
      ],
      hidden: true
    }
  },
  {
    path: "/profile",
    name: "Profile",
    redirect: "/profile/info",
    component: () => import("@/components/Layouts/ProfileLayout.vue"),
    meta: {
      title: "个人中心",
      access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN],
      hidden: true
    },
    children: [
      {
        path: "/profile/info",
        name: "ProfileInfo",
        component: () => import("@/views/profile/ProfileInfo.vue"),
        meta: {
          title: "个人信息",
          access: [ACCESS_ENUM.STUDENT, ACCESS_ENUM.TEACHER, ACCESS_ENUM.ADMIN]
        }
      }
    ]
  }
];
