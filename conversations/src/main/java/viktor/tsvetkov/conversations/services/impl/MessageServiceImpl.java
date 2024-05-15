package viktor.tsvetkov.conversations.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.entities.Message;
import viktor.tsvetkov.conversations.entities.User;
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

    public List<Message> findMessagesByChatsIds(List<UUID> ids) {
        return messageRepository.findAllByChatsId(ids);
    }

    @Override
    @Transactional
    public List<Map<String, Object>> groupChatWithMessagesByIdUser(UUID idUser) {
        List<Chat> chats = chatService.findChatsByIdUser(idUser);
        List<UUID> idUsers = new ArrayList<>();
        chats.forEach(chat -> idUsers.addAll(chat.getIdUsers()));
        List<User> users = userService.findByIds(idUsers);

        List<UUID> idChats = chats.stream().map(Chat::getId).collect(Collectors.toList());
        List<Message> messages = messageRepository.findAllByChatsId(idChats);
        List<Map<String, Object>> result = new ArrayList<>();
        for (Chat chat : chats) {
            HashMap<String, Object> hashMap = new HashMap<>(3);
            hashMap.put("chatInfo", chat);
            List<Message> chatMessages = messages.stream().filter(m -> m.getChat().getId().equals(chat.getId()))
                    .collect(Collectors.toList());
            hashMap.put("messages", chatMessages);
            List<User> chatUsers = users.stream().filter(user -> chat.getIdUsers().contains(user.getId()))
                            .collect(Collectors.toList());
            hashMap.put("users", chatUsers);
            result.add(hashMap);
        }
        return result;
    }
}
