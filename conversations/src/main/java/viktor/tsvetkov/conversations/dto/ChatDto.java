package viktor.tsvetkov.conversations.dto;

import jakarta.annotation.Nullable;

import java.util.List;
import java.util.UUID;

public record ChatDto(
        @Nullable UUID id,
        List<UUID> idUsers) {
}
