package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viktor.tsvetkov.conversations.entities.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Modifying
    @Query(nativeQuery = true,
    value = "update messages set is_read = true where id in :idsOfMessages")
    void readMessages(@Param("idsOfMessages") List<UUID> idsOfMessages);
}
