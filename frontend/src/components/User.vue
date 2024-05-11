<script setup>
    import {onMounted} from "vue";
    import {useUserStore} from "@/stores/user.js";
    import {storeToRefs} from "pinia";

    const store = useUserStore();
    const {userData, question, pointsLabel, oppositeGender} = storeToRefs(store);
    const {askQuestion} = store;

    const askQuestionLabel = "Задайте вопрос случайному человеку";
    const askQuestionPlaceholder = "Задайте вопрос";
    const oppositeGenderLabel = "Противоположный пол";
    const toolTipText = "При активной галочке вопрос со 100% вероятностью отправится представителю " +
        "противоположного пола, но израсходует 30 баллов вместо 10-ти";
    const askQuestionButton = "Задать вопрос";

    onMounted(store.loadUserData);
</script>

<template>
    <div class="common-layout">
        <el-container>
            <el-header>
                <div>{{userData.name}}</div>
                <div>{{pointsLabel}}: {{userData.points}}</div>
            </el-header>
            <el-main>
                <div>
                    <span>{{askQuestionLabel}}</span>
                    <div>
                        <el-input clearable v-model="question" :placeholder="askQuestionPlaceholder"></el-input>
                        <el-tooltip :content="toolTipText" placement="bottom-end">
                            <el-checkbox :label="oppositeGenderLabel" v-model="oppositeGender"></el-checkbox>
                        </el-tooltip>
                        <el-button @click="askQuestion(userData.id)">{{askQuestionButton}}</el-button>
                    </div>
                </div>
            </el-main>
        </el-container>
    </div>
</template>
