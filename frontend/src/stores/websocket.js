import {defineStore} from "pinia";
import { ElNotification } from 'element-plus';
import {computed} from "vue";
import {useAuthenticationStore} from "@/stores/authentication.js";

export const useWebSocketStore = defineStore("websocket", () => {

    const baseWebSocketUrl = "ws://localhost:8080/messages-socket/";
    const authStore = useAuthenticationStore();
    let userName = computed(() => authStore.userName);

    let websocket = null;

    const connect = (idUser) => {
        if (websocket === null) {
            websocket = new WebSocket(baseWebSocketUrl + idUser);
            websocket.onopen = () => {
                console.log("Websocket opened successfully!");
            }
            websocket.onmessage = (e => {
                const messageNotification = JSON.parse(e.data);
                ElNotification({
                    title: messageNotification.senderName,
                    message: messageNotification.messageText,
                    position: "bottom-left",
                    duration: 0,
                    customClass: "notification"
                });
            })
        }
    }

    const sendMessage = (messageDto, idInterloc) => {
        const messageNotification = {
            messageText: messageDto.text,
            senderName: userName.value,
            idInterloc
        };
        console.log(messageNotification.senderName)
        websocket.send(JSON.stringify(messageNotification));
    }

    const closeConnection = () => {
        websocket.close();
    }

    return {
        connect, sendMessage, closeConnection
    }


});