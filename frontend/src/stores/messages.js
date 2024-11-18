import {defineStore} from "pinia";
import axios from "axios";
import {ref} from "vue";

export const useMessagesStore = defineStore("messages", () => {
    let dataChats = ref(null);
    let chatMessages = ref([]);
    let readMessagesIds = ref([]);

    let paginationDoneFunc = ref(null);

    let totalMessagesQuantity = ref(0);
    let currentPage = ref(0);
    const pageSize = 15;
    let start = currentPage.value * pageSize;

    const setPaginationDoneFunc = (func) => {
        paginationDoneFunc.value = func;
    }

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

    const setReadToTrue = (start, idCurrentUser) => {
        for (let i = start; i < (start + pageSize); i++) {
            console.log("element: ");
            console.log(chatMessages.value[i]);
            if (chatMessages.value[i].idUser !== idCurrentUser && !chatMessages.value[i].read) {
                chatMessages.value[i].read = true;
            }
        }
    }

    const readMessages = () => {
        return axios.patch('message/readMessages', readMessagesIds.value);
    }

    const saveMessages = async (idChat, idCurrentUser) => {
        try {
            const loadedResult = await loadMessagesByChat(idChat, start, pageSize);
            totalMessagesQuantity.value = loadedResult.data.totalQuantity;
            if (isAbleToLoadMoreMessages(totalMessagesQuantity.value, currentPage.value, pageSize)) {
                setData([...chatMessages.value, ...loadedResult.data.messages]);
                readMessagesIds.value = loadedResult.data.messages
                    .filter(message => message.idUser !== idCurrentUser && !message.read)
                    .map(message => message.id);
                if (readMessagesIds.value.length > 0) {
                    await readMessages();
                    console.log('выполняется?')
                    setReadToTrue(start, idCurrentUser);
                    readMessagesIds.value = [];
                }
                currentPage.value += 1;
                start = currentPage.value * pageSize;
                paginationDoneFunc.value('ok');
            } else {
                paginationDoneFunc.value('empty');
            }
        } catch (e) {
            paginationDoneFunc.value("error");
            console.error("Возникла ошибка: " + e);
        }
    }

    const isAbleToLoadMoreMessages = (total, currentPage, pageSize) => {
        const pagesQuantity = Math.ceil(total / pageSize);
        return currentPage < pagesQuantity;
    }

    const sendMessage = (messageDto, currentIdUser) => {
        axios.put('message', messageDto)
        .then(() => {
            clearData();
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
        clearData, setData, saveMessages, sendMessage, setPaginationDoneFunc
    }
});