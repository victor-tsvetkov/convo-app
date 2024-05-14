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
import viktor.tsvetkov.conversations.enums.Sex;
import viktor.tsvetkov.conversations.services.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final UserServiceImpl userService;
    private final ChatService chatService;
    private final MessageService messageService;

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
        User randomAskedUser;
        if (askedSex != null) {
            randomAskedUser = userService.getRandomUser(askingUser.getId(), askedSex);
        } else {
            randomAskedUser = userService.getRandomUser(askingUser.getId(), null);
        }
        ChatDto chatDto = new ChatDto(null, new ArrayList<>(Arrays.asList(askingUser.getId(), randomAskedUser.getId())));
        Chat chat = chatService.save(chatDto);
        messageService.save(new MessageDto(null, chat.getId(), askingUser.getId(), questionDto.question()));
        askingUser.setPoints(askingUser.getPoints() - points);
        userService.save(askingUser);
    }
}
