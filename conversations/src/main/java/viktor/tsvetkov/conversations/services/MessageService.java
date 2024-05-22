package viktor.tsvetkov.conversations.services;

import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.entities.Message;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MessageService {
    void save(MessageDto message);
    Message findMessageById(UUID id);
    List<Message> findMessagesByIds(List<UUID> ids);
    void removeMessage(UUID id);
    List<Map<String, Object>> groupChatWithMessagesByIdUser(UUID idUser);
}
