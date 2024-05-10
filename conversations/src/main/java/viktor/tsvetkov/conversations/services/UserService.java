package viktor.tsvetkov.conversations.services;

import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void save(UserDto user);
    User findById(UUID id);
    List<User> findByIds(List<UUID> ids);
    void remove(UUID id);
}
