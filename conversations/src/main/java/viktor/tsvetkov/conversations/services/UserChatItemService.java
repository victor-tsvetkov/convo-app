package viktor.tsvetkov.conversations.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.entities.UserChatItem;
import viktor.tsvetkov.conversations.repositories.UserChatItemRepository;

@Service
@RequiredArgsConstructor
public class UserChatItemService {
    private final UserChatItemRepository userChatItemRepository;

    public void save(UserChatItem userChatItem) {
        userChatItemRepository.save(userChatItem);
    }
}
