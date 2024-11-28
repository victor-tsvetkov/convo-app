<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {storeToRefs} from "pinia";
    import {computed, onMounted} from "vue";
    import {useWebSocketStore} from "@/stores/websocket.js";
    import {useAuthenticationStore} from "@/stores/authentication.js";
    import {useMessagesStore} from "@/stores/messages.js";

    const userStore = useUserStore();
    const websocketStore = useWebSocketStore();
    const {userData, question, pointsLabel, oppositeGender} = storeToRefs(userStore);
    const {askQuestion, idUser} = userStore;

    const authenticationStore = useAuthenticationStore();
    const messageStore = useMessagesStore();

    const askQuestionLabel = "Задайте вопрос случайному человеку";
    const askQuestionPlaceholder = "Задайте вопрос";
    const oppositeGenderLabel = "Противоположный пол";
    const toolTipText = "Вопрос противоположному полу будет стоить 30 баллов";
    const askQuestionButton = "Задать вопрос";
    const logOutBtn = "Выйти";

    let fileList = computed(() => userStore.fileList);

    const logOut = () => {
        authenticationStore.logOut();
    }

    onMounted(() => {
        userStore.loadUser();
        messageStore.loadDataMessages(idUser, "");
        websocketStore.connect(idUser);
    });

    const uploadFile = (file) => {
        console.log(file);
        const fileDto = {
            multipartFile: file.raw,
            idUser,
            filename: file.name
        };
        userStore.uploadFile(fileDto);
    }

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
                <span>{{askQuestionLabel}}</span>
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
            <el-card class="photos">
                <div slot="header" class="clearfix">
                    <span>Фотографии</span>
                </div>
                <div class="photo_block">
                    <div v-for="file in fileList" :key="file.id">
                        <img :src="file.filepath" alt="image">
                    </div>
                </div>
            </el-card>
            <el-upload
                class="upload-demo"
                :on-change="uploadFile"
                :auto-upload="false">
                <el-button size="small" type="primary">Нажмите, чтобы загрузить фото</el-button>
                <div slot="tip" class="el-upload__tip">jpg/png файлы размером не более 5 Мб</div>
            </el-upload>
        </el-main>
    </el-card>
</template>

<style scoped>
    .photos {
        height: 400px;
    }
    .photo_block {
        display: grid;
        grid-template: 50px / 50px;
        grid-auto-columns: 50px;
        grid-auto-rows: 50px;
        gap: 30px;
        overflow-y: scroll;
    }
</style>