package viktor.tsvetkov.conversations.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import viktor.tsvetkov.conversations.dto.MessageNotification;
import viktor.tsvetkov.conversations.utils.JsonUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class SocketMessagesHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final JsonUtils jsonUtils;

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        MessageNotification messageNotification = jsonUtils.parseObject((String) message.getPayload(),
                MessageNotification.class);
        WebSocketSession sess = sessions.stream().
                filter(s -> s.getAttributes().get("idUser")
                        .equals(messageNotification.idInterloc())).findFirst()
                .orElse(null);
        if (sess != null) {
            sess.sendMessage(new TextMessage(jsonUtils.toJson(messageNotification)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        session.close(status);
        sessions.remove(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println();
    }
}
