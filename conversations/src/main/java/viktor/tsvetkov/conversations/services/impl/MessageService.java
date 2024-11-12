package viktor.tsvetkov.conversations.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.dto.MessagesForChatDto;
import viktor.tsvetkov.conversations.entities.Message;
import viktor.tsvetkov.conversations.repositories.MessageRepository;
import viktor.tsvetkov.conversations.services.QueryService;
import static viktor.tsvetkov.conversations.utils.query.Queries.GET_MESSAGES_BY_ID_CHAT;
import static viktor.tsvetkov.conversations.utils.query.Queries. MESSAGES_COUNT_CHAT;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final MessageRepository messageRepository;
    private final QueryService queryService;

    public Message save(MessageDto messageDto) {
        Message message = new Message();
        if (messageDto.id() != null) {
            message.setUpdateDate(LocalDateTime.now());
        } else {
            message.setCreationDate(LocalDateTime.now());
        }
        message.setIdChat(messageDto.idChat());
        message.setIdUser(messageDto.idUser());
        message.setText(messageDto.text());
        return messageRepository.save(message);
    }

    public Message findMessageById(UUID id) {
        return messageRepository.findById(id).orElseThrow();
    }

    public void removeMessage(UUID id) {
        messageRepository.deleteById(id);
    }

    public Map<String, Object> findMessagesByIdChat(UUID id, int start, int pageSize) {
        List<MessagesForChatDto> data = queryService.executeSql(GET_MESSAGES_BY_ID_CHAT,
                MessagesForChatDto.class, Map.of("chatId", id), start, pageSize);
        long totalQuantity = queryService.executeCountSql(MESSAGES_COUNT_CHAT, Map.of("chatId", id));
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("totalQuantity", totalQuantity);
        return result;
    }
}
