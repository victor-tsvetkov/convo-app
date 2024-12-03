package viktor.tsvetkov.conversations.services.impl;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.dto.MessagesForChatDto;
import viktor.tsvetkov.conversations.entities.Message;
import viktor.tsvetkov.conversations.repositories.MessageRepository;
import viktor.tsvetkov.conversations.services.QueryService;
import static viktor.tsvetkov.conversations.utils.query.SqlQueries.MESSAGES_IN_CHAT;
import static viktor.tsvetkov.conversations.utils.query.SqlQueries.MESSAGES_QUANTITY_IN_CHAT;

import static viktor.tsvetkov.conversations.utils.DateTimeUtils.transformDate;

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
            message.setRead(false);
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

    public void removeMessagesByIdChat(UUID idChat) {
        messageRepository.removeMessagesByIdChat(idChat);
    }

    public Map<String, Object> findMessagesByIdChat(UUID idChat, int start, int pageSize) {
        List<MessagesForChatDto> messages = queryService.executeSql(MESSAGES_IN_CHAT,
                MessagesForChatDto.class, Map.of("chatId", idChat), start, pageSize);
        for (MessagesForChatDto message : messages) {
            if (message.getFormattedDay() != null) {
                message.setFormattedDay(transformDate(message.getFormattedDay()));
            }
        }
        long totalQuantity = queryService.executeCountSql(MESSAGES_QUANTITY_IN_CHAT, Map.of("chatId", idChat));
        Map<String, Object> result = new HashMap<>();
        result.put("messages", messages);
        result.put("totalQuantity", totalQuantity);
        return result;
    }

    @Transactional
    public void readMessages(@Nonnull List<UUID> idsOfMessages) {
        messageRepository.readMessages(idsOfMessages);
    }
}
