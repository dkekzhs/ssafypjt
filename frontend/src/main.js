import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import "bootStrap";
// Vuetify
import "vuetify/dist/vuetify.min.css";
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'


const app = createApp(App)
const vuetify = createVuetify({
    components,
    directives,
    iconfont: 'mdi'
})
app.use(vuetify);

app.use(createPinia())
app.use(router)


app.mount('#app')
