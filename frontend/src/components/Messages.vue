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

    onMounted(() => {
        loadDataMessages(idUser, searchMessageValue.value);
    });

    const openChat = (idChat) => {
        router.push({path: `/chats/messages/${idUser}/${idChat}`});
    }

</script>

<template>
    <div>
        <v-card title="Чаты">
            <div class="card-header">
                <v-text-field @input="loadDataMessages(idUser, searchMessageValue)"
                          v-model="searchMessageValue" clearable
                          placeholder="Поиск"></v-text-field>
            </div>
            <div class="chat_board">
                <div  v-for="(item, index) in dataChats" :key="index">
                    <v-card @click="openChat(item.chatId)" variant="outlined" style="height: 100%;
                    padding-left: 10px; padding-right: 10px" shadow="hover">
                        <div class="chat_appearance">
                            <span>{{item.interlocutorName}}</span>
                            <time class="time">{{item.messageDate}}</time>
                        </div>
                        <div class="message">{{item.messageText}}</div>
                    </v-card>
                </div>
            </div>
        </v-card>
    </div>
</template>

<style>
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