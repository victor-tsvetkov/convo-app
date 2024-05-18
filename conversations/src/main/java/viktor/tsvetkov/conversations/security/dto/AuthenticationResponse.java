package viktor.tsvetkov.conversations.security.dto;

import lombok.Builder;
import viktor.tsvetkov.conversations.entities.User;

@Builder
public record AuthenticationResponse(
        String token, User user
) {
}
