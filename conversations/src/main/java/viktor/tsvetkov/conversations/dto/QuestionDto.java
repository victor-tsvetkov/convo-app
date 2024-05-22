package viktor.tsvetkov.conversations.dto;

import java.util.UUID;

public record QuestionDto(
        UUID idUser,
        String question,
        boolean oppositeGender
) {
}
