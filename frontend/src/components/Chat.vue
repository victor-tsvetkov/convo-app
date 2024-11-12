<script setup>
import {computed, onUnmounted, ref} from "vue";
import {useMessagesStore} from "@/stores/messages.js";

    const props = defineProps({
        idChat: String,
        idUser: String
    });

    const messagesStore = useMessagesStore();
    const {clearData, saveMessages} = messagesStore;

    const {idUser, idChat} = props;

    const messageInput = ref('');
    const messages = computed(() => messagesStore.chatMessages);

    const sendMessage = () => {
        if (messageInput.value.trim().length > 0) {
            const messageDto = {
                idUser, idChat, text: messageInput.value
            };
            messagesStore.sendMessage(messageDto);
            messageInput.value = "";
        }
    }

    const load = async ({done}) => {
        const bool = await saveMessages(idChat, {done});
        let status = bool ? "ok" : "empty";
        done(status);
    }

    onUnmounted(clearData);

</script>

<template>
    <div class="chat">
        <v-infinite-scroll @load="load" side="end" class="messages_list">
            <div class="message" :key="message.id" v-for="message in messages"
                    :style="{alignSelf: idUser === message.idUser ? 'flex-end' : 'flex-start',
                    display: 'flex', justifyContent: 'space-between'}">
                {{message.text}}
                <span class="time">{{message.creationDate}}</span>
            </div>
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
        border: 1px solid black;
        padding: 5px;
        display: block;
        margin-top: 10px;
        width: 210px;
        span {
            margin-top: 10px;
        }
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