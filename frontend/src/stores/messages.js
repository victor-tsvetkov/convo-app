import {defineStore} from "pinia";
import axios from "axios";
import {ref} from "vue";

export const useMessagesStore = defineStore("messages", () => {
    let dataChats = ref(null);
    let chatMessages = ref([]);

    let totalMessagesQuantity = ref(0);
    let currentPage = ref(0);
    const pageSize = 50;
    let start = currentPage.value * pageSize;

    function loadDataMessages(idUser, searchParam) {
        axios.get("chatItem/getChatsWithInterlocutor", {
            params: {
                idUser,
                searchParam
            }
        }).then(result => {
            dataChats.value = result.data;
        }).catch(e => console.error(e));
    }

    const loadMessagesByChat = (idChat, start, pageSize) => {
        return axios.get("message/findMessagesByChatId", {
            params: {
                id: idChat,
                start,
                pageSize
            }
        });
    }

    const saveMessages = async (idChat) => {
        try {
            const loadedResult = await loadMessagesByChat(idChat, start, pageSize);
            totalMessagesQuantity.value = loadedResult.data.totalQuantity;
            if (isAbleToLoadMoreMessages(totalMessagesQuantity.value, currentPage.value, pageSize)) {
                setData([...chatMessages.value, ...loadedResult.data.messages]);
                currentPage.value += 1;
                start = currentPage.value * pageSize;
            }
        } catch (e) {
            console.error("Возникла ошибка: " + e);
        }
    }

    const isAbleToLoadMoreMessages = (total, currentPage, pageSize) => {
        const pagesQuantity = Math.ceil(total / pageSize);
        return currentPage < pagesQuantity;
    }

    const sendMessage = (messageDto, currentIdUser) => {
        clearData();
        axios.put('message', messageDto)
        .then(() => {
            saveMessages(messageDto.idChat, currentIdUser);
        })
    }

    const setData = (data) => {
        chatMessages.value = [...data];
    }

    const clearData = () => {
        chatMessages.value = [];
        currentPage.value = 0;
        start = 0;
    }

    return {
        dataChats, loadDataMessages, chatMessages, totalMessagesQuantity,
        clearData, setData, saveMessages, sendMessage
    }
});