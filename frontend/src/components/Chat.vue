<script setup>
import {computed, onUnmounted, ref} from "vue";
import {useMessagesStore} from "@/stores/messages.js";

    const props = defineProps({
        idChat: String,
        idUser: String
    });

    const messagesStore = useMessagesStore();
    const {clearData, saveMessages, setPaginationDoneFunc} = messagesStore;

    const {idUser, idChat} = props;

    const messageInput = ref('');
    const messages = computed(() => messagesStore.chatMessages);

    const sendMessage = () => {
        if (messageInput.value.trim().length > 0) {
            const messageDto = {
                idUser, idChat, text: messageInput.value
            };
            messagesStore.sendMessage(messageDto, idUser);
            messageInput.value = "";
        }
    }

    const load = ({done}) => {
        setPaginationDoneFunc(done);
        saveMessages(idChat, idUser);
    }

    onUnmounted(() => {
        clearData();
        setPaginationDoneFunc(null);
    });

</script>

<template>
    <div class="chat">
        <v-infinite-scroll @load="load" side="end" class="messages_list">
            <template v-for="message in messages" :key="message.id">
                <div class="message"
                     :style="{backgroundColor: !message.read ? '#F0F2F5' : 'inherit'}">
                    <div class="message_header">
                        <span class="message_username">{{message.userName}}</span>
                        <span class="time">{{message.creationDate}}</span>
                    </div>
                    <div class="message_text">
                        {{message.text}}
                    </div>
                </div>
                <div v-if="!!message.formattedDay" style="text-align: center"
                     class="time">
                    {{message.formattedDay}}
                </div>
            </template>

            <template v-slot:empty></template>
        </v-infinite-scroll>
        <v-text-field class="text_area" label="Введите сообщение" v-model="messageInput"></v-text-field>
        <v-btn @click="sendMessage">Отправить</v-btn>
    </div>
</template>

<style scoped>
    .chat {
        display: grid;
        grid-template: 600px 100px 50px / 760px;
    }

    .message {
        padding: 5px;
        display: block;
        margin-top: 10px;
        width: 100%;
    }

    .message_username {
        color: #2A9DD0;
    }

    .message_header {
        display: grid;
        grid-template-columns: fit-content(150px) 30px;
        align-items: center;
        column-gap: 7px;
    }

    .text_area {
        height: 100%;
        margin-top: 10px;
    }

    .messages_list {
        height: 100%;
        display: flex;
        padding-right: 20px;
        padding-left: 20px;
        flex-direction: column-reverse;
        overflow-y: scroll;
        list-style-type: none;
    }
</style>