package viktor.tsvetkov.conversations.services.impl;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.enums.Sex;
import viktor.tsvetkov.conversations.repositories.UserRepository;
import viktor.tsvetkov.conversations.services.EntityService;
import viktor.tsvetkov.conversations.services.UserService;

import static viktor.tsvetkov.conversations.utils.RandomUtils.getRandomInt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Slf4j
public final class UserServiceImpl implements UserService {

    private final EntityService<User, UserRepository> entityService;
    private final UserRepository userRepository;

    @Override
    public User save(UserDto userDto) {
        User user;
        if (userDto.id() != null) {
            user = entityService.findEntityById(userDto.id());
        } else {
            user = new User();
            user.setCreationDate(LocalDateTime.now());
            user.setPoints(30);
        }
        user.setName(userDto.name());
        user.setSex(userDto.sex());
        user.setUsername(userDto.username());
        user.setPassword(userDto.password());
        try {
            entityService.save(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Exception while saving user: " + e.getMessage());
        }
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(UUID id) {
        return entityService.findEntityById(id);
    }

    @Override
    public List<User> findByIds(List<UUID> ids) {
        return entityService.findEntitiesByIds(ids);
    }

    @Override
    public void remove(UUID id) {
        entityService.remove(id);
    }

    public User getRandomUser(UUID exceptId, @Nullable Sex sex) {
        List<User> users = userRepository.findUsersNotInChat(exceptId);
        if (!users.isEmpty()) {
            if (sex != null) {
                List<User> filtered = users.stream().filter(it -> it.getSex().equals(sex)).collect(toList());
                if (!filtered.isEmpty()) {
                    return filtered.get(getRandomInt(0, users.size()-1));
                } else {
                    throw new RuntimeException("Упс! Похоже, вы общались уже со всеми пользователями " +
                            "противоположного пола! =)");
                }
            }
            return users.get(getRandomInt(0, users.size()-1));
        }
        throw new RuntimeException("Упс! Похоже, вы общались уже со всеми пользователями! =)");
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
