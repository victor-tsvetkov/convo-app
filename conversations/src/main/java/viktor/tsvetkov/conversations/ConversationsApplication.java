package viktor.tsvetkov.conversations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ConversationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConversationsApplication.class, args);
	}

}
