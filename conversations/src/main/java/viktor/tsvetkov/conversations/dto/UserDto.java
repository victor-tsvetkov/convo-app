package viktor.tsvetkov.conversations.dto;

import jakarta.annotation.Nullable;
import viktor.tsvetkov.conversations.enums.Sex;

import java.util.UUID;

public record UserDto(
        @Nullable UUID id, String name,
        Sex sex, String username, String password) {
}
