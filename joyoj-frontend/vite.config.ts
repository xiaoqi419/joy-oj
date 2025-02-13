import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";
import eslint from "vite-plugin-eslint";
import vueJsx from "@vitejs/plugin-vue-jsx";
import vueSetupExtend from "vite-plugin-vue-setup-extend";
import { createSvgIconsPlugin } from "vite-plugin-svg-icons";
import monacoEditorPlugin from "vite-plugin-monaco-editor";
import UnoCSS from "unocss/vite";
import prismjs from "vite-plugin-prismjs";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueSetupExtend(),
    vueJsx(),
    eslint(),
    monacoEditorPlugin({}),
    createSvgIconsPlugin({
      // 要缓存的图标文件夹
      iconDirs: [path.resolve(__dirname, "src/svg")],
      // 执行 icon name 的格式
      symbolId: "icon-[name]"
    }),
    UnoCSS(),
    prismjs({
      languages: ["json", "java", "python", "c", "cpp", "js"]
    })
  ],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src")
    }
  },
  server: {
    port: 3000,
    cors: true,
    proxy: {
      "/api": {
        target: "http://localhost:8104/",
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, "")
      }
    }
  },
  envDir: path.resolve(__dirname, "./env")
});
