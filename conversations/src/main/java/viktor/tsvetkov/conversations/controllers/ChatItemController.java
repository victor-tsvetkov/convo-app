package viktor.tsvetkov.conversations.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import viktor.tsvetkov.conversations.dto.ChatWithInterlocutor;
import viktor.tsvetkov.conversations.services.ChatItemService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("chatItem")
@RequiredArgsConstructor
public class ChatItemController {

    private final ChatItemService chatItemService;

    @GetMapping("getChatsWithInterlocutor")
    public List<ChatWithInterlocutor> getChatsWithInterlocutor(@RequestParam(value = "idUser") UUID idUser,
                                                               @RequestParam(value = "searchParam") String searchParam) {
        return chatItemService.getChatsWithInterlocutor(idUser, searchParam);
    }
}
