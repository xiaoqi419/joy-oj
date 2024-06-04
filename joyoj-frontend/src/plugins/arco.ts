import { App } from 'vue'
import ArcoVue from '@arco-design/web-vue'
import '@arco-design/web-vue/dist/arco.css'
import * as ArcoVueIcon from '@arco-design/web-vue/es/icon'
import { camelCaseToLine } from '@/utils/str-utils'

const installElementIcons = (app: App) => {
  for (const [key, component] of Object.entries(ArcoVueIcon)) {
    app.component(`ar-icon${camelCaseToLine(key)}`, component)
  }
}

export const installArco = (app: App) => {
  app.use(ArcoVue)
  installElementIcons(app)
}
