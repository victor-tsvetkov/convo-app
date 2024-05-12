import { createRouter, createWebHistory } from 'vue-router'
import User from "@/components/User.vue";
import Messages from "@/components/Messages.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Моя страница',
      component: User
    },
    {
      path: '/chats/:idUser',
      name: 'Мои чаты',
      component: Messages,
      props: true
    }
  ]
})

export default router
