<script setup>
    import {useMessagesStore} from "@/stores/messages.js";
    import {storeToRefs} from "pinia";
    import {computed, onMounted, reactive, ref} from "vue";
    import {useRouter} from "vue-router";
    import {useWebSocketStore} from "@/stores/websocket.js";
    import {useUserStore} from "@/stores/user.js";

    const router = useRouter();
    const userStore = useUserStore();

    let idUser = computed(() => userStore.idUser);
    const store = useMessagesStore();
    const websocketStore = useWebSocketStore();
    const {dataChats} = storeToRefs(store);
    const {loadDataMessages, noChatsText} = store;

    let searchMessageValue = ref('');

    onMounted(() => {
        console.log(idUser.value)
        loadDataMessages(idUser.value, searchMessageValue.value);
        websocketStore.connect(idUser.value);
    });

    const openChat = (idChat, idInterloc) => {
        router.push({path: `/chats/messages/${idUser.value}/${idChat}/${idInterloc}`});
    }

</script>

<template>
    <el-card title="Чаты" class="chats">
        <div v-if="dataChats.length > 0">
            <div slot="header" class="clearfix">
                <span>Чаты</span>
            </div>
            <div class="card-header">
                <el-input @input="loadDataMessages(idUser, searchMessageValue)"
                          v-model="searchMessageValue" clearable
                          placeholder="Поиск"></el-input>
            </div>
            <div class="chat_board">
                <div v-for="(item, index) in dataChats" :key="index">
                    <el-card @click="openChat(item.chatId, item.interlocutorId)"  style="height: 100%;
                padding-left: 10px; padding-right: 10px" shadow="hover">
                        <div class="chat_appearance">
                            <span>{{item.interlocutorName}}</span>
                            <time class="time">{{item.messageDate}}</time>
                        </div>
                        <div class="message">{{item.messageText}}</div>
                    </el-card>
                </div>
            </div>
        </div>
        <el-empty v-else :description="noChatsText"></el-empty>
    </el-card>
</template>

<style>
    .chats {
        width: 700px;
    }
    .chat_board {
        display: grid;
        grid-template: 80px / 100%;
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