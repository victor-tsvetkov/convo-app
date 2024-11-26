import {defineStore} from "pinia";
import {computed, ref} from "vue";
import {loadUserData} from "@/api/loadUserData.js";
import axios from "axios";
import {ElMessage} from "element-plus";
import {useAuthenticationStore} from "@/stores/authentication.js";

export const useUserStore = defineStore("user", () => {

    const authStore = useAuthenticationStore();

    let userData = ref({});
    const pointsLabel = ref("Ваши баллы");
    let idUser = computed(() => authStore.idUser);

    const loadUser = () => {
        if (!!idUser.value) {
            loadUserData(idUser.value)
            .then(result => {
                userData.value = {
                    name: result.data.name,
                    id: result.data.id,
                    sex: result.data.sex,
                    points: result.data.points
                };
            }).catch(e => console.error(e));
        }
    }

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

    return {
        userData, loadUser, idUser,
        question, pointsLabel, oppositeGender, askQuestion
    }
});