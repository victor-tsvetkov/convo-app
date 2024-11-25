package viktor.tsvetkov.conversations.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;
import viktor.tsvetkov.conversations.utils.JsonUtils;

import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketMessagesHandler(), "/messages-socket/{idUser}")
                .addInterceptors(getInterceptor())
                .setAllowedOrigins("*");
    }

    private HandshakeInterceptor getInterceptor() {
        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) {
                String path = request.getURI().getPath();
                UUID idUser = UUID.fromString(path.substring(17));
                attributes.put("idUser", idUser);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Exception exception) {
            }
        };
    }

    @Bean
    public JsonUtils jsonUtils() {
        return new JsonUtils();
    }

    @Bean
    public SocketMessagesHandler socketMessagesHandler() {
        return new SocketMessagesHandler(jsonUtils());
    }

}
