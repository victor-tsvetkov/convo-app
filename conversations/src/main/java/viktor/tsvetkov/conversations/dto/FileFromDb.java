package viktor.tsvetkov.conversations.dto;

import java.util.UUID;

public record FileFromDb(
        UUID id, String filepath
) {
}
