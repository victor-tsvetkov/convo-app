import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

import VueObserveVisibility from 'vue3-observe-visibility';

import App from './App.vue'
import axios from "axios";
import router from './router';

import './styles/style.css';

axios.defaults.baseURL = "http://localhost:8080";

const app = createApp(App)

app.use(createPinia());
app.use(router);
app.use(VueObserveVisibility);
app.use(ElementPlus);
app.use(ElementPlusIconsVue);

app.mount('#app')
