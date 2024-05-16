package viktor.tsvetkov.conversations.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SocketTestController {

    @MessageMapping("/chat")
    public String doTesting() {
        return "text from socket server";
    }
}
