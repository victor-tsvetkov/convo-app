<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {storeToRefs} from "pinia";
    import {onMounted, onBeforeUnmount} from "vue";
    import {useWebSocketStore} from "@/stores/websocket.js";
    import {useAuthenticationStore} from "@/stores/authentication.js";

    const userStore = useUserStore();
    const websocketStore = useWebSocketStore();
    const {userData, question, pointsLabel, oppositeGender} = storeToRefs(userStore);
    const {askQuestion, idUser} = userStore;

    const authenticationStore = useAuthenticationStore();

    const askQuestionLabel = "Задайте вопрос случайному человеку";
    const askQuestionPlaceholder = "Задайте вопрос";
    const oppositeGenderLabel = "Противоположный пол";
    const toolTipText = "При активной галочке вопрос со 100% вероятностью отправится представителю " +
        "противоположного пола, но израсходует 30 баллов вместо 10-ти";
    const askQuestionButton = "Задать вопрос";
    const logOutBtn = "Выйти";

    const logOut = () => {
        authenticationStore.logOut();
    }

    onMounted(() => {
        userStore.loadUser();
        websocketStore.connect(idUser);
    });

    onBeforeUnmount(() => {
        console.log("before unmounting")
        // websocketStore.closeConnection();
    });

</script>

<template>
    <el-card style="width: 800px;" class="common-layout">
        <el-header class="user_header">
            <div>
                <div>{{userData.name}}</div>
                <div>{{pointsLabel}}: {{userData.points}}</div>
            </div>
            <el-button @click="logOut">{{logOutBtn}}</el-button>
        </el-header>
        <el-main>
            <div>
                <span>{{askQuestionLabel}}</span>
                <div>
                    <el-input clearable v-model="question" :placeholder="askQuestionPlaceholder"></el-input>
                    <el-tooltip :content="toolTipText" placement="bottom-end">
                        <el-checkbox :label="oppositeGenderLabel" v-model="oppositeGender"></el-checkbox>
                    </el-tooltip>
                    <el-button style="float: right; margin-top: 10px"
                               @click="askQuestion(idUser)">
                        {{askQuestionButton}}</el-button>
                </div>
            </div>
        </el-main>
    </el-card>
</template>

<style scoped>

</style>