import {defineStore} from "pinia";
import {computed, ref, watch} from "vue";
import {loadUserData} from "@/api/loadUserData.js";
import axios from "axios";
import {ElMessage} from "element-plus";
import {useAuthenticationStore} from "@/stores/authentication.js";
import {useUsersLikingStore} from "@/stores/usersliking.js";

export const useUserStore = defineStore("user", () => {

    const authStore = useAuthenticationStore();
    const usersLikingStore = useUsersLikingStore();

    let userData = ref({
        name: null,
        id: null,
        sex: null,
        points: null,
        description: null
    });
    const pointsLabel = ref("Ваши баллы");
    let idUser = computed(() => authStore.idUser);
    let fileList = ref([]);
    let showSlider = ref(false);
    let ageRangeValues = ref([18, 40]);
    let showDescription = ref(false);

    const setShowSlider = (value) => {
        showSlider.value = value;
    }

    const loadUser = () => {
        if (!!idUser.value) {
            loadUserData(idUser.value)
            .then(result => {
                userData.value.name = result.data.name;
                userData.value.id = result.data.id;
                userData.value.sex = result.data.sex;
                userData.value.points = result.data.points;
                userData.value.description = result.data.description;
                usersLikingStore.loadUsersToLike(idUser.value, 'FEMALE',
                        {min: ageRangeValues.value[0], max: ageRangeValues.value[1]});
            }).catch(e => console.error(e));
        }
    }

    const handleShowDescription = () => {
        showDescription.value = !showDescription.value;
        if (!showDescription.value) {
            updateUser();
        }
    }

    const updateUser = () => {
        axios.put('user', userData.value);
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

    function askQuestion(idUser) {
        if (question.value.trim().length > 0) {
            const questionDto = {
                idUser,
                question: question.value,
                min: ageRangeValues.value[0],
                max: ageRangeValues.value[1]
            };
            axios.post("question", questionDto)
            .then(result => {
                console.log(result);
                question.value = "";
            }).catch(e => {
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
        question, pointsLabel, askQuestion,
        loadFiles, showDescription, handleShowDescription
    }
});