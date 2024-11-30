import { createRouter, createWebHistory } from 'vue-router'
import Messages from "@/components/Messages.vue";
import WelcomePage from "@/pages/WelcomePage.vue";
import UserPage from "@/components/UserPage.vue";
import Chat from "@/components/Chat.vue";
import Photos from "@/components/Photos.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'Home',
      component: WelcomePage
    },

    {
      path: '/user',
      name: 'My page',
      component: UserPage
    },
    {
      path: '/chats/',
      name: 'My chats',
      component: Messages,
      props: true
    },
    {
      path: '/chats/messages/:idUser/:idChat/:idInterloc',
      component: Chat,
      props: true
    },
    {
      path: '/photos',
      name: 'Photos',
      component: Photos
    }
  ]
})

export default router;
