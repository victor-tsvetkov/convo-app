import {defineStore} from "pinia";
import axios from "axios";
import {ref} from "vue";

export const useMessagesStore = defineStore("messages", () => {
    let dataChats = ref(null);
    let chatMessages = ref([]);

    function loadDataMessages(idUser, searchParam) {
        console.log(idUser);
        axios.get("chatItem/groupChatWithMessages", {
            params: {
                idUser,
                searchParam
            }
        }).then(result => {
            console.log(result);
            dataChats.value = result.data;
        }).catch(e => console.error(e));
    }

    const loadMessagesByChatId = async (idChat) => {
        axios.get("message/findMessagesByChatId", {
            params: {
                id: idChat
            }
        })
        .then(result => {
            console.log(result);
            chatMessages.value = [...result.data];
        }).catch(e => console.error(e));
    }

    const sendMessage = (messageDto) => {
        const idChat = messageDto.idChat;
        axios.put('message', messageDto)
        .then(result => {
            loadMessagesByChatId(idChat);
        });
    }

    return {
        dataChats, loadDataMessages, sendMessage, loadMessagesByChatId, chatMessages
    }
});