package viktor.tsvetkov.conversations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import viktor.tsvetkov.conversations.dto.UserDto;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.services.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping
    public void save(@RequestBody UserDto userDto) {
        userService.save(userDto);
    }

    @GetMapping("userById")
    public User findUserById(@RequestParam(value = "id") UUID id) {
        return userService.findById(id);
    }

    @GetMapping("usersByIds")
    public List<User> findUsersByIds(@RequestParam(value = "id") List<UUID> ids) {
        return userService.findByIds(ids);
    }

    @DeleteMapping
    public void remove(@RequestParam(value = "id") UUID id) {
        userService.remove(id);
    }
}
