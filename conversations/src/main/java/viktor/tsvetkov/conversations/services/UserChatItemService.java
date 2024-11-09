package viktor.tsvetkov.conversations.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.ChatWithInterlocutor;
import viktor.tsvetkov.conversations.entities.UserChatItem;
import viktor.tsvetkov.conversations.repositories.UserChatItemRepository;
import static viktor.tsvetkov.conversations.utils.query.Queries.INTERLOCUTOR_CHAT_LATEST_MESSAGE;
import static viktor.tsvetkov.conversations.utils.query.Queries.SEARCH_MESSAGE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserChatItemService {

    private final UserChatItemRepository userChatItemRepository;
    private final QueryService queryService;

    public void save(UserChatItem userChatItem) {
        userChatItemRepository.save(userChatItem);
    }

    public List<ChatWithInterlocutor> getChatsWithInterlocutor(UUID idUser, String searchParam) {
        String sql;
        Map<String, Object> params = new HashMap<>();
        params.put("idUser", idUser);
        if (searchParam != null && !searchParam.trim().isEmpty()) {
            sql = SEARCH_MESSAGE;
            params.put("searchParam", "%" + searchParam + "%");
        } else {
            sql = INTERLOCUTOR_CHAT_LATEST_MESSAGE;
        }
        return queryService.executeSql(sql, ChatWithInterlocutor.class, params);
    }
}
