package viktor.tsvetkov.conversations.dto;

import lombok.Getter;
import lombok.Setter;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.entities.User;

@Getter
@Setter
public class ChatWithInterlocutor {
    private User interlocutor;
    private Chat chat;
}
