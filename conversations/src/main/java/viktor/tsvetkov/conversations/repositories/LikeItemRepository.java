package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import viktor.tsvetkov.conversations.entities.LikeItem;

import java.util.UUID;

public interface LikeItemRepository extends JpaRepository<LikeItem, UUID> {
}
