package viktor.tsvetkov.conversations.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class MessagesForChatDto {
    private UUID id;
    private boolean isRead;
    private String creationDate;
    private UUID idUser;
    private String text;
    private String formattedDay;
    private String userName;
}
