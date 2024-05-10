package viktor.tsvetkov.conversations.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.repositories.UserRepository;
import viktor.tsvetkov.conversations.services.EntityService;
import viktor.tsvetkov.conversations.services.UserService;

import static viktor.tsvetkov.conversations.utils.RandomUtils.getRandomInt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public final class UserServiceImpl implements UserService {

    private final EntityService<User, UserRepository> entityService;
    private final UserRepository userRepository;

    @Override
    public void save(UserDto userDto) {
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
        try {
            entityService.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Exception while saving user: " + e.getMessage());
        }
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

    public User getRandomUser(UUID exceptId) {
        List<User> users = userRepository.findAllExceptOne(exceptId);
        return users.get(getRandomInt(0, users.size())-1);
    }
}
