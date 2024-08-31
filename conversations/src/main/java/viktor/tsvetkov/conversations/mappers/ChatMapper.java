package viktor.tsvetkov.conversations.mappers;

import viktor.tsvetkov.conversations.entities.Chat;
import static viktor.tsvetkov.conversations.utils.DateTimeUtils.parseStringToLocalDateTime;

import java.util.UUID;

public class ChatMapper {
    public static Chat mapChat(Object[] data) {
        return Chat.builder()
                .id(UUID.fromString(data[0].toString()))
                .creationDate(parseStringToLocalDateTime(data[1].toString()))
                .build();
    }
}
