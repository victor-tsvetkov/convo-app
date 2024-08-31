package viktor.tsvetkov.conversations.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viktor.tsvetkov.conversations.dto.ChatWithInterlocutor;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.entities.Message;
import viktor.tsvetkov.conversations.repositories.MessageRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final ChatService chatService;
    private final UserService userService;
    private final MessageRepository messageRepository;

    public void save(MessageDto messageDto) {
        Message message = new Message();
        if (messageDto.id() != null) {
            message.setUpdateDate(LocalDateTime.now());
        } else {
            message.setCreationDate(LocalDateTime.now());
        }
        message.setChat(chatService.findChatById(messageDto.idChat()));
        message.setUser(userService.findById(messageDto.idUser()));
        message.setText(messageDto.text());
        messageRepository.save(message);
    }

    public Message findMessageById(UUID id) {
        return messageRepository.findById(id).orElseThrow();
    }

    public void removeMessage(UUID id) {
        messageRepository.deleteById(id);
    }

    public List<Message> findMessagesByIdChat(UUID id) {
        return messageRepository.findMessagesByChatId(id);
    }

    @Transactional
    public List<Map<String, Object>> groupChatWithMessagesByIdUser(UUID idUser) {
        List<ChatWithInterlocutor> chats = userService.getChatsOfCurrentUserIdWithInterlocutor(idUser);
        List<Map<String, Object>> result = new ArrayList<>(chats.size());
        for (ChatWithInterlocutor chat : chats) {
            HashMap<String, Object> hashMap = new HashMap<>(2);
            hashMap.put("chatInfo", chat.getChat());
            hashMap.put("interlocutor", chat.getInterlocutor());
            result.add(hashMap);
        }
        return result;
    }
}
