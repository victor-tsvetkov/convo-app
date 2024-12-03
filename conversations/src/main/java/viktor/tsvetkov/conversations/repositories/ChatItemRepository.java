package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import viktor.tsvetkov.conversations.entities.ChatItem;

import java.util.UUID;

public interface ChatItemRepository extends JpaRepository<ChatItem, UUID> {

    void removeChatItemByChatId(UUID chatId);
}
