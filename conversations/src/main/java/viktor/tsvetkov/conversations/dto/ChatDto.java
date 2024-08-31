package viktor.tsvetkov.conversations.dto;

import jakarta.annotation.Nullable;
import viktor.tsvetkov.conversations.entities.User;

import java.util.Set;
import java.util.UUID;

public record ChatDto(
        @Nullable UUID id) {
}
