<script setup>
    import {useMessagesStore} from "@/stores/messages.js";
    import {storeToRefs} from "pinia";
    import {computed, onMounted, ref} from "vue";
    import {useRouter} from "vue-router";
    import {useWebSocketStore} from "@/stores/websocket.js";
    import {useUserStore} from "@/stores/user.js";
    import {More} from "@element-plus/icons-vue";

    const router = useRouter();
    const userStore = useUserStore();

    let idUser = computed(() => userStore.idUser);
    const store = useMessagesStore();
    const websocketStore = useWebSocketStore();
    const {dataChats, showEmpty} = storeToRefs(store);
    const {loadDataMessages, noChatsText, removeChatWithMessages} = store;

    let searchMessageValue = ref('');

    onMounted(() => {
        loadDataMessages(idUser.value, searchMessageValue.value);
        websocketStore.connect(idUser.value);
    });

    const openChat = (idChat, idInterloc) => {
        router.push({path: `/chats/messages/${idUser.value}/${idChat}/${idInterloc}`});
    }

</script>

<template>
    <el-card class="chats">
        <div v-if="dataChats.length > 0">
            <div class="card-header">
                <el-input @input="loadDataMessages(idUser, searchMessageValue)"
                          v-model="searchMessageValue" clearable
                          placeholder="Поиск"></el-input>
            </div>
            <div class="chat_board">
                <div v-for="(item, index) in dataChats" :key="index">
                    <el-card   style="height: 100%;
                padding-left: 10px; padding-right: 10px; position: relative" shadow="hover">
                        <div @click="openChat(item.chatId, item.interlocutorId)">
                            <div class="chat_appearance">
                                <span>{{item.interlocutorName}}</span>
                                <time class="time">{{item.messageDate}}</time>
                            </div>
                            <div class="message">{{item.messageText}}</div>
                        </div>
                        <el-tooltip placement="right" effect="light">
                            <template #content>
                                <div class="tooltip_text">Удалить</div>
                                <div class="tooltip_text">Блокировать</div>
                            </template>
                            <el-icon class="close_chat_icon">
                                <More/>
                            </el-icon>
                        </el-tooltip>
                    </el-card>
                </div>
            </div>
        </div>
        <el-empty v-if="showEmpty" :description="noChatsText"></el-empty>
    </el-card>
</template>

<style>
    .chats {
        width: 100%;
    }
    .close_chat_icon {
        position: absolute;
        top: 5px;
        right: 5px;
        z-index: 20;
    }

    .tooltip_text {
        width: 100px;
        height: 30px;
        font-size: 18px;
        cursor: pointer;
        vertical-align: center;
    }

    .tooltip_text:hover {
        background-color: #F5F7FA;
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