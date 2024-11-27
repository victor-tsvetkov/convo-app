package viktor.tsvetkov.conversations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "files")
@Getter
@Setter
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "filepath")
    private String filePath;

    @Column(name = "id_user")
    private UUID idUser;
}
