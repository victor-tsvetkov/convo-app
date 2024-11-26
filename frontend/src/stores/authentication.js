import {defineStore} from "pinia";
import axios from "axios";
import {useRouter} from "vue-router";
import {ref} from "vue";

export const useAuthenticationStore = defineStore("authentication", () => {

    let idUser = ref(localStorage.getItem("idUser") !== null ? localStorage.getItem("idUser") : null);
    let token = localStorage.getItem("token") !== null ? localStorage.getItem("token") : null;
    const router = useRouter();

    const signUp = () => {
        const name = registerFormData[0].value;
        const username = registerFormData[2].value;
        const password = registerFormData[3].value;
        const sex = registerFormData[1].value;
        if (!!name && !!username && !!password && !!sex) {
            const registerRequest = {name, username, password, sex};
            axios.post('auth/register', registerRequest)
            .then(result => {
                if (result.status === 200) {
                    localStorage.setItem("idUser", result.data.user.id);
                    localStorage.setItem("token", result.data.token);
                    idUser.value = result.data.user.id;
                    token = result.data.token;
                    console.log("User with id " + idUser.value + " successfully signed up");
                }
            })
        }
    }

    const signIn = () => {
        const username = signInFormData[0].value;
        const password = signInFormData[1].value;
        if (!!username && !!password) {
            axios.post('auth/authenticate', {username, password})
            .then(result => {
                if (result.status === 200) {
                    localStorage.setItem("idUser", result.data.user.id);
                    localStorage.setItem("token", result.data.token);
                    idUser.value = result.data.user.id;
                    token = result.data.token;
                    router.push({path: '/user'});
                    console.log("User with id " + idUser.value + " successfully logged in");
                }
            })
        }
    }

    const logOut = () => {
        localStorage.removeItem("idUser");
        localStorage.removeItem("token");
        idUser.value = null;
        token = null;
        router.push({name: 'Home'})
    }

    const registerFormData = [
        {
            id: 1,
            label: 'Имя',
            value: '',
            type: 'text'
        },
        {
            id: 2,
            label: 'Пол',
            value: '',
            type: 'select',
            options: [{id: 1, label: 'мужской', value: 'MALE'}, {id: 2, label: 'женский', value: 'FEMALE'}],
            placeholder: 'Выберите пол'
        },
        {
            id: 3,
            label: 'Имя пользователя',
            value: '',
            type: 'text'
        },
        {
            id: 4,
            label: 'Пароль',
            value: '',
            type: 'password'
        }
    ];

    const signInFormData = [
        {
            id: 1,
            label: 'Имя пользователя',
            value: '',
            type: 'text'
        },
        {
            id: 2,
            label: 'Пароль',
            value: '',
            type: 'password'
        }
    ];

    const registerData = {
        data: registerFormData,
        submitHandler: signUp,
        buttonName: "Зарегистрироваться"
    };

    const signInData = {
        data: signInFormData,
        submitHandler: signIn,
        buttonName: "Войти"
    };

    return {
        idUser, token, logOut, registerData, signInData
    }

});