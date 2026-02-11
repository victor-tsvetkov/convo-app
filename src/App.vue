<script setup>

    import {useUserStore} from "./stores/user";
    import {computed, ref} from "vue";
    import {useMessagesStore} from "./stores/messages.js";
    import {useRouter} from "vue-router";

    const userStore = useUserStore();
    const messagesStore = useMessagesStore();
    const idUser = computed(() => userStore.idUser);

    const showSlider = computed(() => userStore.showSlider);

    const tabs = [
        {
            id: 1,
            name: "userPage",
            label: "Моя страница"
        },
        {
            id: 2,
            name: "chats",
            label: "Чаты"
        },
        {
            id: 3,
            name: "photos",
            label: "Фотографии"
        }
    ];

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
    <div>
        <div class="container">
            <el-tabs v-model="currentTab" @tab-change="routeTo" v-if="!!idUser" type="border-card">
                <el-tab-pane :key="tab.id"
                             v-for="tab in tabs"
                             :name="tab.name"
                             :label="tab.label">
                    <template v-if="tab.name === 'chats'" #label>
                        {{tab.label}}
                        <el-badge v-if="messagesStore.unreadMessagesQuantity > 0" type="danger"
                                  :value="messagesStore.unreadMessagesQuantity"></el-badge>
                    </template>
                </el-tab-pane>
            </el-tabs>
            <router-view class="router_view"></router-view>
        </div>
        <div v-if="showSlider" @click="userStore.setShowSlider(false)" class="grayLayout"></div>
    </div>
</template>

<style>
    .container {
        display: block;
        margin: 0 auto;
        width: 900px;
    }

    .router_view {
        height: 847px;
    }

    .el-tabs--border-card>.el-tabs__content {
        padding: 0;
    }

    button.el-carousel__arrow.el-carousel__arrow--right {
        right: 0;
        width: 100px;
        height: 100%;
        border-radius: 0;
    }

    button.el-carousel__arrow.el-carousel__arrow--left {
        left: 0;
        width: 100px;
        height: 100%;
        border-radius: 0;
    }

    .grayLayout {
        position: fixed;
        left: 0;
        top: 0;
        height: 100%;
        width: 100%;
        background-color: black;
        opacity: 0.5;
        z-index: 15;
    }
</style>