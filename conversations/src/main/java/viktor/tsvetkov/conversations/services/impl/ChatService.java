package viktor.tsvetkov.conversations.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.ChatDto;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.repositories.ChatRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat save(ChatDto chatDto) {
        Chat chat = new Chat();
        if (chatDto.id() == null) {
            chat.setCreationDate(LocalDateTime.now());
        }
        chatRepository.save(chat);
        return chat;
    }

    public Chat findChatById(UUID id) {
        return chatRepository.findById(id).orElseThrow();
    }

    public void remove(UUID id) {
        chatRepository.deleteById(id);
    }

    public List<Chat> findChatsByIdUser(UUID id) {
        return chatRepository.findChatsByIdUser(id);
    }
}
