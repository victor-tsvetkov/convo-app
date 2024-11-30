<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {computed, onMounted, ref} from "vue";

    const userStore = useUserStore();
    let fileList = computed(() => userStore.fileList);
    let showSlider = ref(false);
    const carousel = ref(null);
    const {idUser} = userStore;

    const sliderToggle = (index) => {
        carousel.value.setActiveItem(index);
        showSlider.value = !showSlider.value;
    }

    const uploadFile = (file) => {
        console.log(file);
        const fileDto = {
            multipartFile: file.raw,
            idUser,
            filename: file.name
        };
        userStore.uploadFile(fileDto);
    }

    onMounted(() => userStore.loadFiles());

</script>

<template>
    <el-card class="photos">
        <div slot="header" class="photos_header">
            <span>Фотографии</span>
            <el-upload
                class="upload-demo"
                :on-change="uploadFile"
                :auto-upload="false">
                <el-button size="small" type="primary">Нажмите, чтобы загрузить фото</el-button> <br>
            </el-upload>
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
        height: 100%;
    }
    .photos_header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }
    .photo_block {
        display: grid;
        height: 100%;
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

    .el-carousel__item:nth-child(2n+1),
    .el-carousel__item:nth-child(2n){
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