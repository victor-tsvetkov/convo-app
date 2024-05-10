package viktor.tsvetkov.conversations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import viktor.tsvetkov.conversations.dto.ChatDto;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.services.ChatService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PutMapping
    public void save(@RequestBody ChatDto chatDto) {
        chatService.save(chatDto);
    }

    @GetMapping("chatById")
    public Chat findChatById(@RequestParam(value = "id") UUID id) {
        return chatService.findChatById(id);
    }

    @GetMapping("chatsByIds")
    public List<Chat> findChatsByIds(@RequestParam(value = "ids") List<UUID> ids) {
        return chatService.findChatsByIds(ids);
    }

    @DeleteMapping
    public void remove(@RequestParam(value = "id") UUID id) {
        chatService.remove(id);
    }

    @GetMapping("chatsByIdUsers")
    public List<Chat> findChatsByIdUsers(@RequestParam(value = "id") UUID id) {
        return chatService.findChatsByIdUser(id);
    }
}
