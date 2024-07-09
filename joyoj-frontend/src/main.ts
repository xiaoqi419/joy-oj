import { createApp } from 'vue'
import App from './App.vue'
import { installComponents } from '@/components'
import router, { installRouter } from '@/router'
import { installRouterGuards } from '@/router/guard'
import { installArco } from '@/plugins/arco'
import { installStore } from '@/store'
import { installAssets } from '@/plugins/assets'
// 额外引入图标库
import ArcoVueIcon from '@arco-design/web-vue/es/icon'
import '@/assets/style.css'
import 'animate.css'
// Markdown编辑器样式
import 'bytemd/dist/index.css'
// JsonViewer样式
import JsonViewer from 'vue-json-viewer'

const startApp = () => {
  const app = createApp(App)
  app.use(ArcoVueIcon)
  app.use(JsonViewer)
  // 静态资源
  installAssets(app)
  // 路由 VueRouter
  installRouter(app)
  // 路由守卫
  installRouterGuards(router)
  // UI 组件库 Arco Design
  installArco(app)
  // 全局状态管理 Pinia
  installStore(app)
  // 自定义组件库
  installComponents(app)

  app.mount('#app')
}

startApp()
