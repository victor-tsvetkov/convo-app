<script setup>
    import {useUserStore} from "@/stores/user.js";
    import {computed, ref} from "vue";

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

</script>

<template>
    <div>
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
        <div v-if="showSlider" @click="showSlider = !showSlider" class="grayLayout"></div>
    </div>
</template>

<style scoped>

</style>