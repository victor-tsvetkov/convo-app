package viktor.tsvetkov.conversations.dto;

import java.util.UUID;

public record LikeItemDTO(
        UUID idUser1, UUID idUser2,
        boolean user1Liked, boolean user2Liked
) {
}
