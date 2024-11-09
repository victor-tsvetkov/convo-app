package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.entities.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query(nativeQuery = true,
    value = "select m.id, m.creation_date, m.id_user, m.text " +
            "from messages m where m.id_chat = :chatId order by m.creation_date")
    List<MessageDto> findMessagesByIdChat(@Param("chatId") UUID chatId);
}
