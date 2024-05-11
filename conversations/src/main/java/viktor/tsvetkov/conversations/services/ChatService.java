package viktor.tsvetkov.conversations.services;

import viktor.tsvetkov.conversations.dto.ChatDto;
import viktor.tsvetkov.conversations.entities.Chat;

import java.util.List;
import java.util.UUID;

public interface ChatService {
    Chat save(ChatDto chat);
    Chat findChatById(UUID id);
    List<Chat> findChatsByIds(List<UUID> ids);
    void remove(UUID id);
    List<Chat> findChatsByIdUser(UUID id);
}
