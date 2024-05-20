<script setup>
    import {useAuthenticationStore} from "@/stores/authentication.js";
    import FormComponent from "@/components/FormComponent.vue";
    import {ref} from "vue";
    import {useRouter} from "vue-router";

    const authenticationStore = useAuthenticationStore();
    let formData = ref(authenticationStore.signInData);
    let registerMode = true;

    const formComponentToggle = () => {
        console.log("click!");
        registerMode = !registerMode;
        formData.value = registerMode ? authenticationStore.signInData : authenticationStore.registerData;
    }

    const router = useRouter();

    if (!!authenticationStore.idUser) {
        router.push({path:'/user'});
    }

</script>

<template>
    <el-card shadow="always" class="welcome_page">
        <FormComponent :data="formData"></FormComponent>
        <div>Уже есть аккаунт? <span @click="formComponentToggle">Войти</span></div>
    </el-card>
</template>

<style>
    .welcome_page {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }
</style>