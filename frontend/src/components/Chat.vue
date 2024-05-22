<script setup>
    import {computed, onMounted, ref} from "vue";
    import {useMessagesStore} from "@/stores/messages.js";

    const props = defineProps({
        idChat: String,
        idUser: String
    });

    const messagesStore = useMessagesStore();

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

    onMounted(() => messagesStore.loadMessagesByChatId(idChat));

</script>

<template>
    <el-card>
        <div class="chat">
            <ul class="messages_list">
                <li :key="message.id" v-for="message in messages"
                    :style="{width: '200px', marginTop: '10px', alignSelf: idUser === message.user.id ? 'flex-end' : 'flex-start'}">
                    <el-card>{{message.text}}</el-card>
                </li>
            </ul>
            <el-input placeholder="Введите сообщение" v-model="messageInput" type="textarea"></el-input>
            <el-button @click="sendMessage">Отправить</el-button>
        </div>
    </el-card>
</template>

<style scoped>
    .chat {
        display: grid;
        grid-template: 630px 100px / 800px;
    }
    .messages_list {
        height: 630px;
        display: flex;
        justify-content: space-between;
        flex-direction: column;
        overflow-y: scroll;
        list-style-type: none;
    }
</style>