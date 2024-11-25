package viktor.tsvetkov.conversations.dto;

import java.util.UUID;

public record MessageWithInterlocutor(
        MessageDto messageDto,
        UUID idInterloc
) {
}
