package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viktor.tsvetkov.conversations.entities.Chat;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    @Query(nativeQuery = true,
            value = "select c.* from chats c " +
                    "join users_chats uc on c.id = uc.chat_id " +
                    "join users u on uc.user_id = u.id where u.id = :id")
    List<Chat> findChatsByIdUser(@Param("id") UUID id);
}
