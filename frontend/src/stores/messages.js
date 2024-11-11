import {defineStore} from "pinia";
import axios from "axios";
import {ref} from "vue";

export const useMessagesStore = defineStore("messages", () => {
    let dataChats = ref(null);
    let chatMessages = ref([]);

    let totalMessagesQuantity = ref(0);
    let pagesQuantity = ref(0);
    let currentPage = ref(0);
    const pageSize = 17;
    let start = currentPage.value * pageSize;

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


    const loadMessagesByChatId = (idChat, {done}) => {
        axios.get("message/findMessagesByChatId", {
            params: {
                id: idChat,
                start,
                pageSize
            }
        })
        .then(result => {
            totalMessagesQuantity.value = result.data.totalQuantity;
            pagesQuantity.value = Math.ceil(totalMessagesQuantity.value / pageSize);
            if (currentPage.value < pagesQuantity.value) {
                console.log(result);
                setData([...chatMessages.value, ...result.data.data]);
                currentPage.value += 1;
                start = currentPage.value * pageSize;
                done('ok');
            } else {
                done('empty');
            }
        }).catch(e => console.error(e));
    }

    const sendMessage = (messageDto) => {
        const idChat = messageDto.idChat;
        axios.put('message', messageDto)
        .then(result => {
            // loadMessagesByChatId(idChat, );
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
        clearData, setData, loadMessagesByChatId, sendMessage
    }
});