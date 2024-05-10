package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viktor.tsvetkov.conversations.entities.Chat;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query(nativeQuery = true, value = "select * from chats where :id = ANY(id_users)")
    List<Chat> findChatsByIdUser(@Param("id") UUID id);
}
