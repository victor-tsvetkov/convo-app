<script setup>
    import {onMounted, shallowRef} from "vue";
    import {useUserStore} from "@/stores/user.js";
    import {ChatDotRound, Document} from "@element-plus/icons-vue";
    import UserPage from "@/components/UserPage.vue";
    import Messages from "@/components/Messages.vue";

    const userStore = useUserStore();
    const idUser = userStore.idUser;

    const tabs = [
        {
            id: 1,
            name: 'Моя страница',
            component: UserPage,
            icon: Document
        },
        {
            id: 2,
            name: 'Мои чаты',
            component: Messages,
            icon: ChatDotRound
        }
    ];

    let currentComponent = shallowRef(tabs[0]);

    onMounted(() => {
        userStore.loadUserData(idUser);
        // store.connect();
    });
</script>

<template>

    <el-container style="height:800px">
        <el-aside width="400px">
            <el-row class="tac">
                <el-col :span="15">
                    <el-menu
                        class="el-menu-vertical-demo">
                        <el-menu-item @click="currentComponent = tab" :key="tab.id" v-for="tab in tabs">
                            <el-icon :is="tab.icon"></el-icon>
                            <span>{{tab.name}}</span>
                        </el-menu-item>
                    </el-menu>
                </el-col>
            </el-row>
        </el-aside>
        <el-card style="width: 800px">
            <component :idUser="idUser" :is="currentComponent.component"/>
        </el-card>
    </el-container>
</template>
