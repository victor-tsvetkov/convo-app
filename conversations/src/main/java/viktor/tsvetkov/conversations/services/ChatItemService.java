package viktor.tsvetkov.conversations.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.ChatWithInterlocutor;
import viktor.tsvetkov.conversations.entities.ChatItem;
import viktor.tsvetkov.conversations.repositories.ChatItemRepository;
import static viktor.tsvetkov.conversations.utils.query.SqlQueries.INTERLOCUTOR_CHAT_LATEST_MESSAGE;
import static viktor.tsvetkov.conversations.utils.query.SqlQueries.SEARCH_MESSAGE;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatItemService {

    private final ChatItemRepository chatItemRepository;
    private final QueryService queryService;

    public void save(ChatItem chatItem) {
        chatItemRepository.save(chatItem);
    }

    public void removeByIdChat(UUID idChat) {
        chatItemRepository.removeChatItemByChatId(idChat);
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
