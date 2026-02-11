import {defineStore} from "pinia";
import {ref} from "vue";
import axios from "axios";

export const useUsersLikingStore = defineStore("usersLiking", () => {
    let usersToLike = ref([]);
    let likedUsers = ref([]);
    let skippedUsers = ref([]);
    let currentUserToLike = ref();

    const skipUser = () => {
        skippedUsers.value.push(currentUserToLike.value);
        updateCurrentUserToLike();
    }

    const likeUser = () => {
        likedUsers.value.push(currentUserToLike.value);
        updateCurrentUserToLike();
    }

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
            usersToLike.value = [...result.data];
            updateCurrentUserToLike();
        }).catch(e => console.error(e));
    }

    const updateCurrentUserToLike = () => {
        if (usersToLike.value.length > 0) {
            currentUserToLike.value = usersToLike.value.shift();
        }
    }

    return {
        loadUsersToLike
    }

})