import {defineStore} from "pinia";
import axios from "axios";
import {computed, ref} from "vue";
import {useWebSocketStore} from "@/stores/websocket.js";
import {useUserStore} from "@/stores/user.js";

export const useMessagesStore = defineStore("messages", () => {
    let dataChats = ref([]);
    let chatMessages = ref([]);

    const userStore = useUserStore();
    let idUser = computed(() => userStore.idUser);

    let unreadMessagesQuantity = computed(() => dataChats.value
        .filter(c => !c.read && c.sender !== idUser.value).length);

    let totalMessagesQuantity = ref(0);
    let currentPage = ref(0);
    const pageSize = 50;
    let start = currentPage.value * pageSize;
    const noChatsText = "У вас пока нет чатов. Начните общение, задав вопрос случайному пользователю!";
    let showEmpty = ref(false);

    const socketStore = useWebSocketStore();

    function loadDataMessages(idUser, searchParam) {
        axios.get("chatItem/getChatsWithInterlocutor", {
            params: {
                idUser,
                searchParam
            }
        }).then(result => {
            dataChats.value = result.data;
            if (dataChats.value.length === 0) {
                showEmpty.value = true;
            }
            console.log(result.data)
        }).catch(e => console.error(e));
    }

    const removeChatWithMessages = (idChat) => {
        axios.delete("chat", {
            params: {
                id: idChat
            }
        })
        .then(() => loadDataMessages(idUser.value, ""));
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

    const sendMessage = (messageDto, currentIdUser, idInterloc) => {
        clearData();
        axios.put('message', messageDto)
        .then(() => {
            socketStore.sendMessage(messageDto, idInterloc)
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
        dataChats, loadDataMessages, chatMessages, totalMessagesQuantity, removeChatWithMessages,
        clearData, setData, saveMessages, sendMessage, unreadMessagesQuantity, noChatsText, showEmpty
    }
});