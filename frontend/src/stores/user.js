import {defineStore} from "pinia";
import {ref} from "vue";
import {loadUserData} from "@/api/loadUserData.js";
import axios from "axios";

export const useUserStore = defineStore("user", () => {
    let userData = ref({});
    const pointsLabel = ref("Ваши баллы");
    const idUser = '77cf2aa1-fa06-427a-82d6-7027c350e3ac';

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
            }).catch(e => console.error(e));
        }
    }

    return {
        userData, loadUserData, idUser,
        question, pointsLabel, oppositeGender, askQuestion
    }
});