<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {computed, onMounted, ref} from "vue";

    const userStore = useUserStore();
    let fileList = computed(() => userStore.fileList);
    let showSlider = computed(() => userStore.showSlider);
    const carousel = ref(null);
    const {idUser} = userStore;

    const sliderToggle = (index) => {
        carousel.value.setActiveItem(index);
        userStore.setShowSlider(!showSlider.value);
    }

    const uploadFile = (file) => {
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
    <div>
        <el-card class="photos">
            <div slot="header" class="photos_header">
                <el-upload
                    class="upload-demo"
                    :on-change="uploadFile"
                    :auto-upload="false">
                    <el-button size="small" type="primary">Нажмите, чтобы загрузить фото</el-button> <br>
                </el-upload>
            </div>
            <div class="photo_block">
                <div v-for="(file, index) in fileList"
                     style="cursor: pointer"
                     @click="sliderToggle(index)"
                     :key="file.id">
                    <img style="width: 100%; height: 100%; object-fit: cover"
                         :src="file.filepath" alt="image">
                </div>
            </div>
        </el-card>
        <el-carousel ref="carousel" v-show="showSlider" height="885px"
                     trigger="click"
                     :autoplay="false" class="carousel"
                     indicator-position="none">
            <el-carousel-item class="carousel_item" v-for="file in fileList" :key="file.id">
                <img class="carousel_image" :src="file.filepath" alt="image">
            </el-carousel-item>
        </el-carousel>
    </div>
</template>

<style scoped>
    .photos {
        height: 100%;
        border-top: none;
    }

    .photo_block {
        display: grid;
        height: 795px;
        grid-template: 200px / repeat(4, 200px);
        grid-auto-rows: 200px;
        gap: 10px;
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
        z-index: 20;
    }

    .el-carousel__item[data-v-405f568d]:nth-child(2n+1),
    .el-carousel__item[data-v-405f568d]:nth-child(2n){
        background-color: inherit;
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