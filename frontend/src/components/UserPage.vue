<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {storeToRefs} from "pinia";
    import {onMounted} from "vue";
    import {useWebSocketStore} from "@/stores/websocket.js";
    import {useAuthenticationStore} from "@/stores/authentication.js";
    import {useMessagesStore} from "@/stores/messages.js";
    import {Edit} from "@element-plus/icons-vue";

    const userStore = useUserStore();
    const websocketStore = useWebSocketStore();
    const {userData, question, pointsLabel,
        oppositeGender, ageRangeValues, showDescrInput,
        description} = storeToRefs(userStore);
    const {askQuestion, idUser} = userStore;

    const authenticationStore = useAuthenticationStore();
    const messageStore = useMessagesStore();

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
        messageStore.loadDataMessages(idUser, "");
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
                    <el-icon @click="showDescrInput = !showDescrInput">
                        <Edit/>
                    </el-icon>
                    <span>Рассказ о себе: </span>
                    <div v-if="!showDescrInput">{{userData.description}}</div>
                    <el-input v-else type="textarea" v-model="description" :rows="3"></el-input>
                </div>
                <span>{{askQuestionLabel}}</span>
                <el-slider
                    style="width: 270px"
                    v-model="ageRangeValues"
                    range
                    :min="18"
                    :max="100">
                </el-slider>
                <div>
                    <el-input clearable v-model="question" :placeholder="askQuestionPlaceholder"></el-input>
                    <el-tooltip :content="toolTipText" placement="bottom-start">
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
    .user_header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

</style>