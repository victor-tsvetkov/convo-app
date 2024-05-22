<script setup>
    import {useMessagesStore} from "@/stores/messages.js";
    import {storeToRefs} from "pinia";
    import {onMounted} from "vue";
    import {useRouter} from "vue-router";

    const router = useRouter();

    const props = defineProps({
        idUser: String
    });
    const {idUser} = props;
    const store = useMessagesStore();
    const {dataChats} = storeToRefs(store);
    const {loadDataMessages} = store;

    console.log("data chats");
    console.log(dataChats);

    onMounted(() => {
        loadDataMessages(idUser);
    });

    const openChat = (chat) => {
        router.push({path: `/chats/messages/${idUser}/${chat.chatInfo.id}`});
    }

</script>

<template>
    <div>
        <el-card style="max-width: 550px">
            <template #header>
                <div class="card-header">
                    <span>Чаты</span>
                </div>
            </template>
            <div class="chat_board">
                <div  v-for="item in dataChats" :key="item.chatInfo.id">
                    <el-card @click="openChat(item)" style="height: 100%" shadow="hover">{{item.interlocutor.name}}</el-card>
                </div>
            </div>
        </el-card>
    </div>
</template>

<style>
    .chat_board {
        display: grid;
        grid-template: 80px / 480px;
        grid-auto-rows: 80px;
    }
</style>