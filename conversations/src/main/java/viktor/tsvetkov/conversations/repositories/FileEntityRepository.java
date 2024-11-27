package viktor.tsvetkov.conversations.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import viktor.tsvetkov.conversations.entities.FileEntity;

import java.util.UUID;

public interface FileEntityRepository extends JpaRepository<FileEntity, UUID> {
}
