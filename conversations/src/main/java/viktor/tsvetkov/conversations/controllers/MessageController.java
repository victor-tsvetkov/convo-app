package viktor.tsvetkov.conversations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import viktor.tsvetkov.conversations.dto.MessageDto;
import viktor.tsvetkov.conversations.entities.Message;
import viktor.tsvetkov.conversations.services.MessageService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("messageById")
    public Message findMessageById(@RequestParam(value = "id") UUID id) {
        return messageService.findMessageById(id);
    }

    @GetMapping("messagesByIds")
    public List<Message> findMessagesByIds(@RequestParam(value = "ids") List<UUID> ids) {
        return messageService.findMessagesByIds(ids);
    }

    @PutMapping
    public void save(@RequestBody MessageDto messageDto) {
        messageService.save(messageDto);
    }

    @DeleteMapping
    public void remove(@RequestParam(value = "id") UUID id) {
        messageService.removeMessage(id);
    }

}
