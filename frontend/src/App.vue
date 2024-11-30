<script setup>

    import {useUserStore} from "@/stores/user.js";
    import {computed, ref} from "vue";
    import {useMessagesStore} from "@/stores/messages.js";
    import {useRouter} from "vue-router";

    const userStore = useUserStore();
    const messagesStore = useMessagesStore();
    const idUser = computed(() => userStore.idUser);

    let currentTab = ref(
        localStorage.getItem("currentTab") !== null ? localStorage.getItem("currentTab") : "userPage"
    );
    const router = useRouter();
    const routeTo = () => {
        localStorage.setItem("currentTab", currentTab.value);
        let path;
        if (currentTab.value === 'userPage') {
            path = "/user";
        } else if (currentTab.value === 'chats') {
            path = "/chats/";
        } else {
            path = "/photos";
        }
        router.push({path});
    }

</script>

<template>
    <div class="container">
        <el-tabs v-model="currentTab" @tab-change="routeTo" v-if="!!idUser" type="border-card">
            <el-tab-pane name="userPage" label="Моя страница"></el-tab-pane>
            <el-tab-pane name="chats" label="Чаты">
                <el-badge type="primary" :value="messagesStore.unreadMessagesQuantity"/>
            </el-tab-pane>
            <el-tab-pane name="photos" label="Фотографии"></el-tab-pane>
        </el-tabs>
        <router-view></router-view>
    </div>
</template>

<style>
    .container {
        position: absolute;
        left: 50%;
        transform: translateX(-50%);
        display: grid;
        grid-template: 39px minmax(600px, 900px) / 900px;
        column-gap: 20px;
    }
</style>