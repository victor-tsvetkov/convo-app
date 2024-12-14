package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import viktor.tsvetkov.conversations.entities.BlackListItem;

import java.util.UUID;

public interface BlackListRepository extends JpaRepository<BlackListItem, UUID> {
}
