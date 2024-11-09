<script setup>
    import {useMessagesStore} from "@/stores/messages.js";
    import {storeToRefs} from "pinia";
    import {onMounted, ref} from "vue";
    import {useRouter} from "vue-router";

    const router = useRouter();

    const props = defineProps({
        idUser: String
    });
    const {idUser} = props;
    const store = useMessagesStore();
    const {dataChats} = storeToRefs(store);
    const {loadDataMessages} = store;

    let searchMessageValue = ref('');

    console.log("data chats");
    console.log(dataChats);

    onMounted(() => {
        loadDataMessages(idUser, searchMessageValue.value);
    });

    const openChat = (idChat) => {
        router.push({path: `/chats/messages/${idUser}/${idChat}`});
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
            <div class="card-header">
                <el-input @input="loadDataMessages(idUser, searchMessageValue)"
                          v-model="searchMessageValue" clearable
                          placeholder="Поиск"></el-input>
            </div>
            <div class="chat_board">
                <div  v-for="(item, index) in dataChats" :key="index">
                    <el-card @click="openChat(item.chatId)" style="height: 100%" shadow="hover">
                        <div class="chat_appearance">
                            <span>{{item.interlocutorName}}</span>
                            <time class="time">{{item.messageDate}}</time>
                        </div>
                        <div class="message">{{item.messageText}}</div>
                    </el-card>
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
        cursor: pointer;
        margin-top: 10px;
    }
    .message {
        margin-top: 5px;
    }
    .chat_appearance {
        display: flex;
        justify-content: space-between;
    }
    .time {
        font-size: 13px;
        color: #999;
    }
</style>