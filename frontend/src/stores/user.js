import {defineStore} from "pinia";
import {ref} from "vue";
import {loadUserData} from "@/api/loadUserData.js";
import axios from "axios";
import {ElMessage} from "element-plus";

export const useUserStore = defineStore("user", () => {
    let userData = ref({});
    const pointsLabel = ref("Ваши баллы");
    const idUser = localStorage.getItem("idUser");

    loadUserData(idUser)
    .then(result => {
        userData.value = {
            name: result.data.name,
            id: result.data.id,
            sex: result.data.sex,
            points: result.data.points
        };
    }).catch(e => console.error(e));

    const question = ref("");

    const oppositeGender = ref(false);
    let stompClient = null;

    function askQuestion(idUser) {
        if (question.value.trim().length > 0) {
            const questionDto = {
                idUser,
                question: question.value,
                oppositeGender: oppositeGender.value
            };
            axios.post("question", questionDto)
            .then(result => {
                console.log(result);
                question.value = "";
                oppositeGender.value = false;
            }).catch(e => {
                console.warn(e)
                ElMessage({
                    showClose: true,
                    message: e.response.data.message,
                    type: 'warning',
                })
            });
        }
    }

    const connect = () => {

        // const socket = new WebSocket('ws://localhost:8080/messages-socket');
        // socket.onopen = () => {
        //     console.log("Web socket opened with WebSocket class!))")
        //     const testEntity = {
        //         id: 1,
        //         description: "Тестовое описание для сокета!"
        //     };
        //     socket.send(JSON.stringify(testEntity));
        // }
        // socket.onmessage = (e) => {
        //     console.log(`From server: ${e.data}`);
        // }


        // const url = "http://localhost:8080/ws/";
        // const socket = new SockJS(url);
        // socket.onopen = () => {
        //     console.log("web socket opened!");
        //
        //     socket.send("daw");
        // }
        //
        //
        // socket.onmessage = function(e) {
        //     console.log('message', e.data);
        // }
        // stompClient = Stomp.over(socket);
        // stompClient.connect({}, () => {
        //     stompClient.subscribe("/topic/greetings", (response) => {
        //         console.log("РЕЗУЛЬТАТ!:");
        //         console.log(response.body);
        //     });
        // });
    }

    const sendMessage = () => {
        stompClient.send("/app/hello", {});
    }



    return {
        userData, loadUserData, idUser, connect, sendMessage,
        question, pointsLabel, oppositeGender, askQuestion
    }
});