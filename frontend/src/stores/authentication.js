import {defineStore} from "pinia";
import axios from "axios";

export const useAuthenticationStore = defineStore("authentication", () => {

    let idUser = localStorage.getItem("idUser") !== null ? localStorage.getItem("idUser") : null;
    let token = localStorage.getItem("token") !== null ? localStorage.getItem("token") : null;

    const signUp = (name, username, password, sex) => {
        if (!!name && !!username && !!password && !!sex) {
            const registerRequest = {name, username, password, sex};
            axios.post('auth/register', registerRequest)
            .then(result => {
                if (result.status === 200) {
                    localStorage.setItem("idUser", result.data.user.id);
                    localStorage.setItem("token", result.data.token);
                    idUser = result.data.user.id;
                    token = result.data.token;
                    console.log("User with id " + idUser + " successfully signed up");
                }
            })
        }
    }

    const signIn = (username, password) => {
        if (!!username && !!password) {
            axios.post('auth/authenticate', {username, password})
            .then(result => {
                if (result.status === 200) {
                    localStorage.setItem("idUser", result.data.user.id);
                    localStorage.setItem("token", result.data.token);
                    idUser = result.data.user.id;
                    token = result.data.token;
                    console.log("User with id " + idUser + " successfully logged in");
                }
            })
        }
    }

    const logOut = () => {
        localStorage.setItem("idUser", null);
        localStorage.setItem("token", null);
        idUser = null;
        token = null;
    }

    return {
        idUser, token, signUp, signIn, logOut
    }

});