package viktor.tsvetkov.conversations.dto;

import java.util.Date;
import java.util.UUID;

public record MessagesForChatDto (
        UUID id,
        Date creationDate,
        UUID idUser,
        String text
) {
}
