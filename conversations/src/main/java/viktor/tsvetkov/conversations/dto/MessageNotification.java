package viktor.tsvetkov.conversations.dto;

import java.util.UUID;

public record MessageNotification(
        String messageText,
        String senderName,
        UUID idInterloc
) {
}
