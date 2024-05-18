package viktor.tsvetkov.conversations.security.dto;

public record AuthenticationRequest(
        String username, String password
) {
}
