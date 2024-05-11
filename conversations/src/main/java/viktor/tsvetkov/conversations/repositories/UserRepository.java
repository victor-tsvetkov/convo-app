package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.enums.Sex;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(nativeQuery = true, value = "select * from users where id != :exceptId")
    List<User> findAllExceptOne(UUID exceptId);

    List<User> findAllBySex(Sex sex);
}
