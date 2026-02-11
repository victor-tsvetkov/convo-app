import axios from "axios";

export async function loadUserData(id){
    return axios.get('user/userById', {
        params: {
            id
        }
    });
}