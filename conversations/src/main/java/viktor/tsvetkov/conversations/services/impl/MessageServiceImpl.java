package viktor.tsvetkov.conversations.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.entities.Message;
import viktor.tsvetkov.conversations.repositories.MessageRepository;
import viktor.tsvetkov.conversations.services.ChatService;
import viktor.tsvetkov.conversations.services.EntityService;
import viktor.tsvetkov.conversations.services.MessageService;
import viktor.tsvetkov.conversations.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final EntityService<Message, MessageRepository> entityService;
    private final ChatService chatService;
    private final UserService userService;
    private final MessageRepository messageRepository;

    @Override
    public void save(MessageDto messageDto) {
        Message message;
        if (messageDto.id() != null) {
            message = entityService.findEntityById(messageDto.id());
            message.setUpdateDate(LocalDateTime.now());
        } else {
            message = new Message();
            message.setCreationDate(LocalDateTime.now());
        }
        message.setChat(chatService.findChatById(messageDto.idChat()));
        message.setUser(userService.findById(messageDto.idUser()));
        message.setText(messageDto.text());
        try {
            entityService.save(message);
        } catch (Exception e) {
            throw new RuntimeException("Exception while saving a message: " + e.getMessage());
        }
    }

    @Override
    public Message findMessageById(UUID id) {
        return entityService.findEntityById(id);
    }

    @Override
    public List<Message> findMessagesByIds(List<UUID> ids) {
        return entityService.findEntitiesByIds(ids);
    }

    @Override
    public void removeMessage(UUID id) {
        entityService.remove(id);
    }

    @Override
    public List<Map<String, Object>> groupChatWithMessagesByIdUser(UUID idUser) {
        List<Message> messages = messageRepository.findAllByIdUser(idUser);
        Map<Chat, List<Message>> structure = messages.stream().collect(Collectors.groupingBy(Message::getChat));
        List<Map<String, Object>> result = new ArrayList<>();
        Set<Chat> keys = structure.keySet();
        for (Chat key : keys) {
            HashMap<String, Object> hashMap = new HashMap<>(2);
            hashMap.put("chatInfo", key);
            hashMap.put("messages", structure.get(key));
            result.add(hashMap);
        }
        return result;
    }
}
