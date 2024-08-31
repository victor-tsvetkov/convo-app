package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import viktor.tsvetkov.conversations.entities.UserChatItem;

import java.util.UUID;

public interface UserChatItemRepository extends JpaRepository<UserChatItem, UUID> {
}
