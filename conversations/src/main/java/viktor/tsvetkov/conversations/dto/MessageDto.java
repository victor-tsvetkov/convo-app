package viktor.tsvetkov.conversations.dto;

import jakarta.annotation.Nullable;

import java.util.UUID;

public record MessageDto(
        @Nullable UUID id,
        UUID idChat,
        UUID idUser,
        String text) {
}
