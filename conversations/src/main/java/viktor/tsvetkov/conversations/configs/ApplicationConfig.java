package viktor.tsvetkov.conversations.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import viktor.tsvetkov.conversations.entities.Chat;
import viktor.tsvetkov.conversations.entities.Message;
import viktor.tsvetkov.conversations.entities.User;
import viktor.tsvetkov.conversations.repositories.ChatRepository;
import viktor.tsvetkov.conversations.repositories.MessageRepository;
import viktor.tsvetkov.conversations.repositories.UserRepository;
import viktor.tsvetkov.conversations.services.EntityService;

@Configuration
public class ApplicationConfig {

    @Bean
    public EntityService<Chat, ChatRepository> chatEntityService(ChatRepository repository) {
        return new EntityService<>(repository);
    }

    @Bean
    public EntityService<Message, MessageRepository> messageEntityService(MessageRepository repository) {
        return new EntityService<>(repository);
    }

    @Bean
    public EntityService<User, UserRepository> userEntityService(UserRepository repository) {
        return new EntityService<>(repository);
    }
}
