<script setup>

    import {useUserStore} from "@/stores/user.js";

    const userStore = useUserStore();
    const idUser = userStore.idUser;

    const chatPath = `/chats/${idUser}`;

</script>

<template>
    <v-responsive>
        <v-app>
            <v-navigation-drawer>
                <v-list>
                    <v-list-item>
                        <router-link to="/user">Моя страница</router-link>
                    </v-list-item>
                    <v-list-item>
                        <router-link :to="chatPath">Мои чаты</router-link>
                    </v-list-item>
                </v-list>
            </v-navigation-drawer>

            <v-app-bar title="App bar"></v-app-bar>

            <v-main>
                <v-container>
                    <router-view></router-view>
                </v-container>
            </v-main>

        </v-app>
    </v-responsive>
</template>

<style>

</style>