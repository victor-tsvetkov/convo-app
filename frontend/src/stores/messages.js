import {defineStore} from "pinia";
import axios from "axios";
import {ref} from "vue";

export const useMessagesStore = defineStore("messages", () => {
    let dataMessages = ref(null);

    function loadDataMessages(idUser) {
        console.log(idUser);
        axios.get("message/groupChatWithMessages", {
            params: {
                idUser
            }
        }).then(result => {
            console.log(result);
            dataMessages.value = result.data;
        }).catch(e => console.error(e));
    }

    return {
        dataMessages, loadDataMessages
    }
});