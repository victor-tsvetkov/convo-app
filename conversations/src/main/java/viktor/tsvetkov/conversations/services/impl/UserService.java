package viktor.tsvetkov.conversations.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viktor.tsvetkov.conversations.dto.AgeRange;
import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.enums.Sex;
import viktor.tsvetkov.conversations.exceptions.NoUsersToTalkException;
import viktor.tsvetkov.conversations.repositories.UserRepository;
import viktor.tsvetkov.conversations.services.QueryService;
import viktor.tsvetkov.conversations.utils.query.SqlQueries;

import static viktor.tsvetkov.conversations.utils.RandomUtils.getRandomInt;
import static viktor.tsvetkov.conversations.utils.query.SqlQueries.USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH;

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

    public List<User> getUsersForLiking(UUID currentUserId, String sex, AgeRange ageRange) {
        return queryService.executeSql(SqlQueries.USERS_FOR_LIKING, User.class, Map.of(
                "currentUserId", currentUserId, "min", ageRange.min(), "max", ageRange.max(),
                "sex", sex));
    }

    @Transactional
    public User save(UserDto userDto) {
        User user;
        if (userDto.id() != null) {
            user = userRepository.findById(userDto.id()).orElseThrow();
        } else {
            user = new User();
            user.setPoints(50);
            user.setCreationDate(LocalDateTime.now());
        }
        if (userDto.name() != null) {
            user.setName(user.getName());
        }
        if (userDto.sex() != null) {
            user.setSex(userDto.sex());
        }
        if (userDto.description() != null && !userDto.description().isEmpty()) {
            user.setDescription(userDto.description());
        }
        if (userDto.username() != null) {
            user.setUsername(userDto.username());
        }
        if (userDto.password() != null) {
            user.setPassword(userDto.password());
        }
        if (userDto.age() != null) {
            user.setAge(userDto.age());
        }
        return userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User findById(UUID id) {
        log.info("Getting user with id {}", id);
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("User with id " + id + " wasn't found"));
    }

    public void remove(UUID id) {
        userRepository.deleteById(id);
    }

    public User getRandomUserToChat(UUID currentUserId, AgeRange ageRange, Sex sex) {
        List<User> users = usersCurrentUserDoesNotHaveChatWith(currentUserId, ageRange, sex);
        if (users.isEmpty()) {
            throw new NoUsersToTalkException("Упс! Похоже, вы общались уже со всеми пользователями =)");
        }
        return getRandomUser(users);
    }

    public List<User> usersCurrentUserDoesNotHaveChatWith(UUID currentUserId, AgeRange ageRange, Sex sex) {
        Map<String, Object> params = new HashMap<>();
        params.put("currentUserId", currentUserId);
        params.put("min", ageRange.min());
        params.put("max", ageRange.max());
        params.put("sex", sex);
        return queryService.executeSql(USERS_CURRENT_USER_DOES_NOT_HAVE_CHAT_WITH, User.class, params);
    }

    public User getRandomUser(List<User> users) {
        return users.get(getRandomInt(0, users.size() - 1));
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
