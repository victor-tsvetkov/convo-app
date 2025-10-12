package viktor.tsvetkov.conversations.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.LikeItemDTO;
import viktor.tsvetkov.conversations.entities.LikeItem;
import viktor.tsvetkov.conversations.repositories.LikeItemRepository;
import viktor.tsvetkov.conversations.utils.query.SqlQueries;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LikeItemService {
    private final LikeItemRepository repository;
    private final QueryService queryService;

    public LikeItem getLikeItemByIdUsers(UUID idUser1, UUID idUser2) {
        List<LikeItem> items = queryService.executeSql(
                SqlQueries.LIKE_ITEMS_BY_ID_USERS, LikeItem.class, Map.of("idUser1", idUser1, "idUser2", idUser2));
        if (!items.isEmpty()) {
            return items.get(0);
        }
        throw new EntityNotFoundException("Лайк с такими пользователями не найден");
    }

    public void save(LikeItemDTO likeItemDTO) {
        LikeItem item = LikeItem.builder()
                .idUser1(likeItemDTO.idUser1())
                .idUser2(likeItemDTO.idUser2())
                .user1Liked(likeItemDTO.user1Liked())
                .user2Liked(likeItemDTO.user2Liked())
                .build();
        repository.save(item);
    }
}
