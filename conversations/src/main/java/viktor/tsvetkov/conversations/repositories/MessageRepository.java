package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viktor.tsvetkov.conversations.entities.Message;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query(nativeQuery = true, value = "select * from messages where id_user = :idUser")
    List<Message> findAllByIdUser(@Param("idUser") UUID idUser);
}
