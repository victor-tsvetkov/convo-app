<script setup>
    import {useMessagesStore} from "@/stores/messages.js";
    import {storeToRefs} from "pinia";
    import {onMounted} from "vue";

    const props = defineProps({
        idUser: String
    });
    const {idUser} = props;
    const store = useMessagesStore();
    const {dataMessages} = storeToRefs(store);
    const {loadDataMessages} = store;

    onMounted(() => loadDataMessages(idUser));

</script>

<template>
    <el-card style="max-width: 550px">
        <template #header>
            <div class="card-header">
                <span>Чаты</span>
            </div>
        </template>
        <div class="chat_board">
            <div v-for="item in dataMessages" :key="item.chatInfo.id">
                <el-card shadow="hover">{{item.chatInfo.id}}</el-card>
            </div>
        </div>
    </el-card>
</template>

<style>
    .chat_board {
        display: grid;
        grid-template: 60px / 480px;
        row-gap: 5px;
    }
</style>