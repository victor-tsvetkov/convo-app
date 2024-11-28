<script setup>

    import {useUserStore} from "@/stores/user.js";
    import {computed} from "vue";
    import {useMessagesStore} from "@/stores/messages.js";

    const userStore = useUserStore();
    const messagesStore = useMessagesStore();
    const idUser = computed(() => userStore.idUser);

    const chatPath = `/chats/`;

</script>

<template>
    <div class="container">
        <el-menu v-if="!!idUser"
                 class="el-menu-vertical-demo">
            <el-menu-item>
                <router-link class="el-menu-item" to="/user">
                    Моя страница
                </router-link>
            </el-menu-item>
            <el-menu-item>
                <router-link class="el-menu-item" :to="chatPath">
                    Мои чаты <span> <el-badge type="primary" :value="messagesStore.unreadMessagesQuantity"/></span>
                </router-link>
            </el-menu-item>
        </el-menu>
        <router-view></router-view>
    </div>
</template>

<style>
    .el-menu-item * {
        vertical-align: unset;
    }

    .container {
        display: grid;
        grid-template: minmax(600px, 900px) / 200px 1100px;
        column-gap: 20px;
    }
</style>