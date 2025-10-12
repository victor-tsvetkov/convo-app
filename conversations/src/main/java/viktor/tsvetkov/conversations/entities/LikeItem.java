package viktor.tsvetkov.conversations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "like_items")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LikeItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "id_user_1")
    private UUID idUser1;

    @Column(name = "id_user_2")
    private UUID idUser2;

    @Column(name = "user_1_liked")
    private boolean user1Liked;

    @Column(name = "user_2_liked")
    private boolean user2Liked;
}
