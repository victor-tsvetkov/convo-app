<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {storeToRefs} from "pinia";
    import {computed, onMounted, ref} from "vue";
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
    let showSlider = ref(false);
    const carousel = ref(null);

    const logOut = () => {
        authenticationStore.logOut();
    }

    const sliderToggle = (index) => {
        carousel.value.setActiveItem(index);
        showSlider.value = !showSlider.value;
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
                    <div v-for="(file, index) in fileList"
                         @click="sliderToggle(index)"
                         :key="file.id">
                        <img style="width: 100%; height: 100%; object-fit: cover"
                             :src="file.filepath" alt="image">
                    </div>
                </div>
                <el-carousel ref="carousel" v-show="showSlider" height="885px"
                             trigger="click"
                             :autoplay="false" class="carousel"
                             indicator-position="none">
                    <el-carousel-item class="carousel_item" v-for="file in fileList" :key="file.id">
                        <img class="carousel_image" :src="file.filepath" alt="image">
                    </el-carousel-item>
                </el-carousel>
            </el-card>
            <el-upload
                class="upload-demo"
                :on-change="uploadFile"
                :auto-upload="false">
                <el-button size="small" type="primary">Нажмите, чтобы загрузить фото</el-button>
                <div slot="tip" class="el-upload__tip">jpg/png файлы размером не более 5 Мб</div>
            </el-upload>
        </el-main>
        <div v-if="showSlider" @click="showSlider = !showSlider" class="grayLayout"></div>
    </el-card>
</template>

<style scoped>

    .grayLayout {
        position: fixed;
        left: 0;
        top: 0;
        height: 100%;
        width: 100%;
        background-color: black;
        opacity: 0.5;
        z-index: 5;
    }

    .photos {
        height: 600px;
    }
    .photo_block {
        display: grid;
        height: 570px;
        grid-template: 240px / repeat(3, 240px);
        grid-auto-rows: 240px;
        gap: 20px;
        overflow-y: scroll;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 18px;
        opacity: 0.75;
        line-height: 300px;
        margin: 0;
    }

    .carousel {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        width: 960px;
        z-index: 10;
    }

    .el-carousel__item[data-v-c078ba28]:nth-child(2n+1),
    .el-carousel__item[data-v-c078ba28]:nth-child(2n){
        background-color: #222222;
    }

    .carousel_image {
        width: 100%;
        height: 100%;
        object-fit: contain;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
</style>