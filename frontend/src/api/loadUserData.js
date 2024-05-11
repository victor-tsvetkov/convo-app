import axios from "axios";

export async function loadUserData(){
    return axios.get('user/userById', {
        params: {
            id: '77cf2aa1-fa06-427a-82d6-7027c350e3ac'
        }
    });
}