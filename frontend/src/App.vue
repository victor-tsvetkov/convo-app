<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {
        Document,
        ChatDotRound
    } from '@element-plus/icons-vue';

    const userStore = useUserStore();
    const {idUser} = userStore;
    const messageLink = `/chats/${idUser}`;

</script>

<template>
    <el-container style="height:800px">
        <el-aside width="400px">
            <el-row class="tac">
                <el-col :span="15">
                    <el-menu
                        class="el-menu-vertical-demo">
<!--                        <template v-for="rule in $router.options.routes">-->
<!--                            <el-menu-item :index="rule.path">-->
<!--                                <el-icon><Document /></el-icon>-->
<!--                                <span>{{rule.name}}</span>-->
<!--                            </el-menu-item>-->
<!--                        </template>-->

                        <el-menu-item index="1">
                            <router-link class="el-menu-item" to="/">
                                <el-icon><Document /></el-icon>
                                <span>Моя страница</span>
                            </router-link>
                        </el-menu-item>

                        <el-menu-item index="2">
                            <router-link class="el-menu-item" :to=messageLink>
                                <el-icon><ChatDotRound /></el-icon>
                                <span>Мои чаты</span>
                            </router-link>
                        </el-menu-item>
                    </el-menu>
                </el-col>
            </el-row>
        </el-aside>
        <el-card style="width: 800px">
            <router-view></router-view>
        </el-card>
    </el-container>
</template>
