package viktor.tsvetkov.conversations.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "black_list")
@IdClass(BlackListItemID.class)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BlackListItem {

    @Id
    @Column(name = "who_blocks")
    private UUID whoBlocks;

    @Id
    @Column(name = "being_blocked")
    private UUID beingBlocked;
}
