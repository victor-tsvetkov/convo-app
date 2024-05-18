package viktor.tsvetkov.conversations.security.dto;

import viktor.tsvetkov.conversations.enums.Sex;

public record RegisterRequest(
        String name, String username, String password, Sex sex
) {
}
