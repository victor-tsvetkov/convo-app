import { createApp } from 'vue'
import { createPinia } from 'pinia'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import App from './App.vue'
import axios from "axios";
import router from './router'

axios.defaults.baseURL = "http://localhost:8080";

const app = createApp(App)
const vuetify = createVuetify({
    components,
    directives,
});

app.use(createPinia());
app.use(vuetify);
app.use(router);

app.mount('#app')
