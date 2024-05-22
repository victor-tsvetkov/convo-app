<script setup>

    import {useUserStore} from "@/stores/user.js";

    const userStore = useUserStore();
    const idUser = userStore.idUser;

    const chatPath = `/chats/${idUser}`;

</script>

<template>
    <el-container style="height:800px">
        <el-aside width="400px">
            <el-row class="tac">
                <el-col :span="15">
                    <el-menu
                        class="el-menu-vertical-demo">
                        <el-menu-item>
                            <router-link class="el-menu-item" to="/user">
                                Моя страница
                            </router-link>
                        </el-menu-item>
                        <el-menu-item>
                            <router-link class="el-menu-item" :to="chatPath">
                                Мои чаты
                            </router-link>
                        </el-menu-item>
                    </el-menu>
                </el-col>
            </el-row>
        </el-aside>
        <router-view></router-view>
    </el-container>
</template>
