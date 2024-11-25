import {defineStore} from "pinia";
import { ElNotification } from 'element-plus';
import {useUserStore} from "@/stores/user.js";

export const useWebSocketStore = defineStore("websocket", () => {

    const baseWebSocketUrl = "ws://localhost:8080/messages-socket/";
    const userStore = useUserStore();

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
            senderName: userStore.userData.name,
            idInterloc
        };
        websocket.send(JSON.stringify(messageNotification));
    }

    const closeConnection = () => {
        websocket.close();
    }

    return {
        connect, sendMessage, closeConnection
    }


});