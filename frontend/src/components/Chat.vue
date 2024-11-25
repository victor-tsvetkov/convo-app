<script setup>
import {computed, onMounted, onUnmounted, ref} from "vue";
import {useMessagesStore} from "@/stores/messages.js";
import axios from "axios";

    const props = defineProps({
        idChat: String,
        idUser: String,
        idInterloc: String
    });

    const messagesStore = useMessagesStore();
    const {clearData, saveMessages} = messagesStore;

    const {idUser, idChat, idInterloc} = props;

    let scrollList = null;

    const messageInput = ref('');
    const messages = computed(() => messagesStore.chatMessages);

    let idsMessagesToRead = [];

    const sendMessage = () => {
        if (messageInput.value.trim().length > 0) {
            const messageDto = {
                idUser, idChat, text: messageInput.value
            };
            messagesStore.sendMessage(messageDto, idUser, idInterloc);
            messageInput.value = "";
        }
    }

    const scrollMessages = () => {
        const percentage = (scrollList.scrollTop / (scrollList.scrollHeight - scrollList.clientHeight)) * 100;
        if (Math.abs(percentage) >= 90) {
            saveMessages(idChat, idUser);
        }
        if (idsMessagesToRead.length > 0) {
            for (let id of idsMessagesToRead) {
                messages.value.filter(m => m.id === id)[0].read = true;
            }
            axios.patch('message/readMessages', idsMessagesToRead)
            .then(() => idsMessagesToRead = []);
        }
    }

    const addIdsMessagesToRead = (isVisible, entry, message) => {
        if (isVisible) {
            if (message.idUser !== idUser && !message.read) {
                idsMessagesToRead.push(message.id);
            }
        }
    }

    onMounted(() => {
        scrollList = document.querySelector('.messages_list');
        saveMessages(idChat, idUser);
    });

    onUnmounted(() => {
        clearData();
    });

</script>

<template>
    <div class="chat">
        <ul class="messages_list" @scrollend="scrollMessages">
            <li v-for="message in messages" :key="message.id">
                <div v-if="!!message.formattedDay" style="text-align: center"
                     class="time">
                    {{message.formattedDay}}
                </div>
                <div class="message" v-observe-visibility="(isVisible, entry) => addIdsMessagesToRead(isVisible, entry, message)"
                     :style="{backgroundColor: !message.read ? '#F0F2F5' : 'inherit'}">
                    <div class="message_header">
                        <span class="message_username">{{message.userName}}</span>
                        <span class="time">{{message.creationDate}}</span>
                    </div>
                    <div class="message_text">
                        {{message.text}}
                    </div>
                </div>
            </li>
        </ul>
        <el-input class="text_area" label="Введите сообщение" v-model="messageInput"></el-input>
        <el-button @click="sendMessage">Отправить</el-button>
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