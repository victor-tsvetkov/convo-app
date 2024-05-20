import { createRouter, createWebHistory } from 'vue-router'
import User from "@/components/User.vue";
import Messages from "@/components/Messages.vue";
import WelcomePage from "@/pages/WelcomePage.vue";
import UserPage from "@/components/UserPage.vue";

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
      component: User
    },
    {
      path: '/user/userPage',
      component: UserPage
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
