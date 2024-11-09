package viktor.tsvetkov.conversations.dto;

import java.util.UUID;

public record ChatWithInterlocutor(UUID chatId, UUID interlocutorId, String interlocutorName, String messageDate,
                                   String messageText) {
}
