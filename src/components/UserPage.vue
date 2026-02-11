<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {storeToRefs} from "pinia";
    import {onMounted} from "vue";
    import {useWebSocketStore} from "@/stores/websocket.js";
    import {useAuthenticationStore} from "@/stores/authentication.js";
    import {useMessagesStore} from "@/stores/messages.js";
    import {Edit} from "@element-plus/icons-vue";
    import {useUsersLikingStore} from "@/stores/usersliking.js";

    const userStore = useUserStore();
    const websocketStore = useWebSocketStore();
    const {userData, question, pointsLabel, ageRangeValues} = storeToRefs(userStore);
    const {askQuestion, idUser} = userStore;

    const authenticationStore = useAuthenticationStore();
    const usersLikingStore = useUsersLikingStore();

    const askQuestionLabel = "Задайте вопрос случайному человеку";
    const askQuestionPlaceholder = "Задайте вопрос";
    const oppositeGenderLabel = "Противоположный пол";
    const toolTipText = "Вопрос противоположному полу будет стоить 30 баллов";
    const askQuestionButton = "Задать вопрос";
    const logOutBtn = "Выйти";

    const logOut = () => {
        authenticationStore.logOut();
    }

    onMounted(() => {
        userStore.loadUser();
        websocketStore.connect(idUser);
    });

</script>

<template>
    <el-card class="common-layout">
        <el-header class="user_header">
            <div>
                <div>{{userData.name}}</div>
                <div>{{pointsLabel}}: {{userData.points}}</div>
            </div>
            <el-button @click="logOut">{{logOutBtn}}</el-button>
        </el-header>
        <el-main>
            <div>
                <div>
                    <el-icon style="cursor: pointer" @click="userStore.handleShowDescription()">
                        <Edit/>
                    </el-icon>
                    <span>Рассказ о себе: </span>
                    <span v-if="!userStore.showDescription">{{userData.description}}</span>
                    <el-input :placeholder="'Напишите о себе что-нибудь'" v-else v-model="userData.description"></el-input>
                </div>
                <el-slider
                    style="width: 270px"
                    v-model="ageRangeValues"
                    range
                    :min="18"
                    :max="100">
                </el-slider>
                <div>
<!--                    компонент пролистывания анкет-->
                </div>
            </div>
        </el-main>
    </el-card>
</template>

<style scoped>
    .user_header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

</style>