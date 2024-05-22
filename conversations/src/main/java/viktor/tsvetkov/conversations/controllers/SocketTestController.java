package viktor.tsvetkov.conversations.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketTestController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String doTesting() {
        return "text from socket server";
    }
}
