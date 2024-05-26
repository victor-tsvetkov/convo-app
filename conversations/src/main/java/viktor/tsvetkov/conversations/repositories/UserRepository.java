package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import viktor.tsvetkov.conversations.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByUsername(String username);

    @Query(nativeQuery = true,
            value = "select * from users where id != :exceptId and id not in :notInIds")
    List<User> findUsersByNotInListId(List<UUID> notInIds, UUID exceptId);
}
