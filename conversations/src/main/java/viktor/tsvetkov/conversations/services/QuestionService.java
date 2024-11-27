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
import viktor.tsvetkov.conversations.entities.ChatItem;
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
    private final ChatItemService chatItemService;

    @Transactional
    public void askQuestion(@Nonnull QuestionDto questionDto) {
        User currentUser = userService.findById(questionDto.idUser());
        Sex askedSex = null;
        long points = 10;
        if (questionDto.oppositeGender()) {
            if (currentUser.getSex().equals(Sex.MALE)) {
                askedSex = Sex.FEMALE;
            } else {
                askedSex = Sex.MALE;
            }
            points = 30;
        }
        User randomUser = userService.getRandomUserToChat(currentUser.getId(), askedSex);
        ChatDto chatDto = new ChatDto(null);
        Chat chat = chatService.save(chatDto);
        messageService.save(new MessageDto(null, chat.getId(), currentUser.getId(), questionDto.question()));
        currentUser.setPoints(currentUser.getPoints() - points);
        userService.save(currentUser);
        ChatItem chatItemOfCurrentUser = new ChatItem();
        chatItemOfCurrentUser.setChatId(chat.getId());
        chatItemOfCurrentUser.setUserId(currentUser.getId());
        ChatItem chatItemOfRandomUser = new ChatItem();
        chatItemOfRandomUser.setChatId(chat.getId());
        chatItemOfRandomUser.setUserId(randomUser.getId());
        chatItemService.save(chatItemOfCurrentUser);
        chatItemService.save(chatItemOfRandomUser);
    }
}
