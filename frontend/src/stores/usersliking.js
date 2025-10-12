import {defineStore} from "pinia";
import {ref} from "vue";
import axios from "axios";

export const useUsersLikingStore = defineStore("usersLiking", () => {
    let usersToLike = ref([]);
    let likedUsers = ref([]);
    let skippedUsers = ref([]);

    const loadUsersToLike = (currentUserId, sex, ageRange) => {
        console.log(ageRange);
        axios.get("user/usersForLiking", {
            headers: {
                "Content-Type": "application/json"
            },
            params: {
                currentUserId,
                sex,
                min: ageRange.min,
                max: ageRange.max
            }
        }).then(result => {
            console.log(result);
        }).catch(e => console.error(e));
    }

    return {
        loadUsersToLike
    }

})