package viktor.tsvetkov.conversations.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BlackListItemID implements Serializable {
    private UUID whoBlocks;
    private UUID beingBlocked;
}
