import { createApp } from "vue";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router";
import store from "./store";
import "@arco-design/web-vue/dist/arco.css";
import ArcoVue from "@arco-design/web-vue";

const app = createApp(App);

app.use(store);
app.use(router);
app.use(ArcoVue);
app.mount("#app");
