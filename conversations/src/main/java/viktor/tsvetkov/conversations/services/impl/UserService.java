package viktor.tsvetkov.conversations.services.impl;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.enums.Sex;
import viktor.tsvetkov.conversations.exceptions.NoUsersToTalkException;
import viktor.tsvetkov.conversations.repositories.UserRepository;
import viktor.tsvetkov.conversations.services.QueryService;
import viktor.tsvetkov.conversations.utils.query.SqlQueries;

import static viktor.tsvetkov.conversations.utils.RandomUtils.getRandomInt;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final QueryService queryService;

    public User save(UserDto userDto) {
        User user = User.builder()
                .id(userDto.id())
                .points(50)
                .name(userDto.name())
                .sex(userDto.sex())
                .creationDate(LocalDateTime.now())
                .username(userDto.username())
                .password(userDto.password())
                .build();
        return userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Cacheable("users")
    public User findById(UUID id) {
        log.info("Getting user with id {}", id);
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " wasn't found"));
    }

    public void remove(UUID id) {
        userRepository.deleteById(id);
    }

    public User getRandomUser(List<User> users) {
        return users.get(getRandomInt(0, users.size() - 1));
    }

    public List<User> usersCurrentUserDoesNotHaveChatWith(UUID currentUserId, @Nullable Sex sex) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("currentUserId", currentUserId);
        String sql;
        if (sex != null) {
            params.put("sex", sex.toString());
            sql = SqlQueries.USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH_BY_SEX;
        } else {
            sql = SqlQueries.USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH;
        }
        return queryService.executeSql(sql, User.class, params);
    }

    public User getRandomUserToChat(UUID currentUserId, @Nullable Sex sex) {
        List<User> users = usersCurrentUserDoesNotHaveChatWith(currentUserId, sex);
        if (users.isEmpty()) {
            throw new NoUsersToTalkException("Упс! Похоже, вы общались уже со всеми пользователями =)");
        }
        return getRandomUser(users);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
