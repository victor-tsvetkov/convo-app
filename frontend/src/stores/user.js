import {defineStore} from "pinia";
import {computed, ref, watch} from "vue";
import {loadUserData} from "@/api/loadUserData.js";
import axios from "axios";
import {ElMessage} from "element-plus";
import {useAuthenticationStore} from "@/stores/authentication.js";

export const useUserStore = defineStore("user", () => {

    const authStore = useAuthenticationStore();

    let userData = ref({});
    const pointsLabel = ref("Ваши баллы");
    let idUser = computed(() => authStore.idUser);
    let fileList = ref([]);
    let showSlider = ref(false);
    let ageRangeValues = ref([18, 40]);
    let description = ref("");
    let showDescrInput = ref(false);

    const setShowSlider = (value) => {
        showSlider.value = value;
    }

    const loadUser = () => {
        if (!!idUser.value) {
            loadUserData(idUser.value)
            .then(result => {
                userData.value = {
                    name: result.data.name,
                    id: result.data.id,
                    sex: result.data.sex,
                    points: result.data.points,
                    description: result.data.description
                };
            }).catch(e => console.error(e));
        }
    }

    watch(showDescrInput, () => {
        if (!showDescrInput.value) {
            console.log(description.value)
            userData.value.description = description.value;
            updateUser();
        }
    })

    const updateUser = () => {
        axios.put('user', userData.value)
        .then(() => {
            loadUserData(idUser.value)
            .then(result => {
                userData.value = {
                    name: result.data.name,
                    id: result.data.id,
                    sex: result.data.sex,
                    points: result.data.points,
                    description: result.data.description
                };
            }).catch(e => console.error(e));
        });
    }

    const uploadFile = (fileDto) => {
        axios.post('file', fileDto, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        .then(() => {
            loadFiles();
        });
    }

    const loadFiles = () => {
        axios.get('file', {
            params: {
                idUser: idUser.value
            }
        })
        .then(result => {
            fileList.value = result.data;
            console.log(fileList.value)
        });
    }

    const question = ref("");

    const oppositeGender = ref(false);

    function askQuestion(idUser) {
        if (question.value.trim().length > 0) {
            const questionDto = {
                idUser,
                question: question.value,
                oppositeGender: oppositeGender.value,
                min: ageRangeValues.value[0],
                max: ageRangeValues.value[1]
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
        userData, loadUser, idUser, uploadFile,
        fileList, showSlider, setShowSlider, ageRangeValues,
        question, pointsLabel, oppositeGender, askQuestion,
        loadFiles, description, showDescrInput
    }
});