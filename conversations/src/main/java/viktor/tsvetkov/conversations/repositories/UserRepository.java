package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import viktor.tsvetkov.conversations.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(nativeQuery = true,
            value = "select * from users\n" +
                    "where users.id != :exceptId and id not in (" +
                    "select users.id from users, chats where users.id = any(chats.id_users))")
    List<User> findUsersNotInChat(@Param("exceptId") UUID exceptId);
}
