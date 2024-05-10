package viktor.tsvetkov.conversations.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.ChatDto;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.repositories.ChatRepository;
import viktor.tsvetkov.conversations.services.ChatService;
import viktor.tsvetkov.conversations.services.EntityService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final EntityService<Chat, ChatRepository> entityService;
    private final ChatRepository chatRepository;

    @Override
    public void save(ChatDto chatDto) {
        Chat chat;
        if (chatDto.id() != null) {
            chat = entityService.findEntityById(chatDto.id());
        } else {
            chat = new Chat();
            chat.setCreationDate(LocalDateTime.now());
        }
        chat.setIdUsers(chatDto.idUsers());
        try {
            entityService.save(chat);
        } catch (Exception e) {
            throw new RuntimeException("Exception while saving chat: " + e.getMessage());
        }
    }

    @Override
    public Chat findChatById(UUID id) {
        return entityService.findEntityById(id);
    }

    @Override
    public List<Chat> findChatsByIds(List<UUID> ids) {
        return entityService.findEntitiesByIds(ids);
    }

    @Override
    public void remove(UUID id) {
        entityService.remove(id);
    }

    @Override
    public List<Chat> findChatsByIdUser(UUID id) {
        return chatRepository.findChatsByIdUser(id);
    }
}
