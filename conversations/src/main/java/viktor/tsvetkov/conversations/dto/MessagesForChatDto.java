package viktor.tsvetkov.conversations.dto;

import java.util.UUID;

public record MessagesForChatDto (
        UUID id,
        String creationDate,
        UUID idUser,
        String text,
        String formattedDay
) {

}
