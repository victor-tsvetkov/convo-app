package viktor.tsvetkov.conversations.configs;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Collections;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CaffeineCache caffeineCache() {
        return new CaffeineCache("cache", Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(1))
                .initialCapacity(2)
                .maximumSize(2000)
                .build());
    }

    @Bean
    public CacheManager caffeineCacheManager(CaffeineCache caffeineCache) {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(Collections.singletonList(caffeineCache));
        return manager;
    }
}
