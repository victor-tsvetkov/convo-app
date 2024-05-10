package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import viktor.tsvetkov.conversations.entities.Message;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
