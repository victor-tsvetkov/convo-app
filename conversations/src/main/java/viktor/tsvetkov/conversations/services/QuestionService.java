package viktor.tsvetkov.conversations.services;

import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.ChatDto;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.dto.QuestionDto;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.entities.UserChatItem;
import viktor.tsvetkov.conversations.enums.Sex;
import viktor.tsvetkov.conversations.services.impl.ChatService;
import viktor.tsvetkov.conversations.services.impl.MessageService;
import viktor.tsvetkov.conversations.services.impl.UserService;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final UserChatItemService userChatItemService;

    @Transactional
    public void askQuestion(@Nonnull QuestionDto questionDto) {
        User askingUser = userService.findById(questionDto.idUser());
        Sex askedSex = null;
        long points = 10;
        if (questionDto.oppositeGender()) {
            if (askingUser.getSex().equals(Sex.MALE)) {
                askedSex = Sex.FEMALE;
            } else {
                askedSex = Sex.MALE;
            }
            points = 30;
        }
        User randomAskedUser = userService.getRandomUserToChat(askingUser, askedSex);
        ChatDto chatDto = new ChatDto(null);
        Chat chat = chatService.save(chatDto);
        messageService.save(new MessageDto(null, chat.getId(), askingUser.getId(), questionDto.question()));
        askingUser.setPoints(askingUser.getPoints() - points);
        userService.save(askingUser);
        UserChatItem askingUserChatItem = new UserChatItem();
        askingUserChatItem.setChat(chat);
        askingUserChatItem.setUser(askingUser);
        UserChatItem randomUserChatItem = new UserChatItem();
        randomUserChatItem.setChat(chat);
        randomUserChatItem.setUser(randomAskedUser);
        userChatItemService.save(askingUserChatItem);
        userChatItemService.save(randomUserChatItem);
    }

    public void sendMessage(MessageDto messageDto) {
        messageService.save(messageDto);
    }
}
