<script setup>
    import {useAuthenticationStore} from "@/stores/authentication.js";
    import FormComponent from "@/components/FormComponent.vue";
    import {ref} from "vue";
    import {useRouter} from "vue-router";

    const authenticationStore = useAuthenticationStore();
    let formData = ref(authenticationStore.registerData);
    let registerMode = true;
    const alreadyHaveAccount = "Уже есть аккаунт?";
    const notHaveAccountYet = "Нет аккаунта?";
    const logInBtn = "Войти";
    const registerBtn = "Зарегистрируйтесь";
    let text = alreadyHaveAccount;
    let actionBtn = logInBtn;

    const formComponentToggle = () => {
        registerMode = !registerMode;
        formData.value = registerMode ? authenticationStore.registerData : authenticationStore.signInData;
        text = registerMode ? alreadyHaveAccount : notHaveAccountYet;
        actionBtn = registerMode ? logInBtn : registerBtn;
    }

    const router = useRouter();

    if (!!authenticationStore.idUser) {
        router.push({path:'/user'});
    }

</script>

<template>
    <el-card shadow="always" class="welcome_page">
        <FormComponent :data="formData"></FormComponent>
        <div>{{text}} <span class="actionBtn" @click="formComponentToggle">{{actionBtn}}</span></div>
    </el-card>
</template>

<style>
    .welcome_page {
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%, -50%);
    }
    .actionBtn {
        color: #409EFF;
        cursor: pointer;
    }
    .actionBtn:hover {
        text-decoration: underline;
    }
</style>