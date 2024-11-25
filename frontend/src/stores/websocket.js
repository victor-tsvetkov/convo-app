import {defineStore} from "pinia";

export const useWebSocketStore = defineStore("websocket", () => {

    const baseWebSocketUrl = "ws://localhost:8080/messages-socket/";

    let websocket = null;

    const connect = (idUser) => {
        if (websocket === null) {
            websocket = new WebSocket(baseWebSocketUrl + idUser);
            websocket.onopen = () => {
                console.log("Websocket opened successfully!");
            }
            websocket.onmessage = (e => {
                console.log(e.data);
            })
        }
    }

    const sendMessage = (messageDto, idInterloc) => {
        const messageDtoWithInterlocutor = {
            messageDto, idInterloc
        };
        websocket.send(JSON.stringify(messageDtoWithInterlocutor));
    }

    const closeConnection = () => {
        websocket.close();
    }

    return {
        connect, sendMessage, closeConnection
    }


});