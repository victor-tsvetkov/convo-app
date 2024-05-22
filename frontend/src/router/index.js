import { createRouter, createWebHistory } from 'vue-router'
import Messages from "@/components/Messages.vue";
import WelcomePage from "@/pages/WelcomePage.vue";
import UserPage from "@/components/UserPage.vue";
import Chat from "@/components/Chat.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Страница регистрации/аутентификации',
      component: WelcomePage
    },

    {
      path: '/user',
      name: 'Моя страница',
      component: UserPage
    },
    {
      path: '/chats/:idUser',
      name: 'Мои чаты',
      component: Messages,
      props: true
    },
    {
      path: '/chats/messages/:idUser/:idChat',
      component: Chat,
      props: true
    }
  ]
})

export default router
