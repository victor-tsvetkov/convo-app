import { createApp } from 'vue'
import { createPinia } from 'pinia'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import axios from "axios";
// import router from './router'

axios.defaults.baseURL = "http://localhost:8080";

const app = createApp(App)

app.use(createPinia())
app.use(ElementPlus);
// app.use(router)

app.mount('#app')
