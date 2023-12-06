package kr.co.imguru.global.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("6379")
    private int port;

    @Value("localhost")
    private String host;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
//        final SocketOptions socketoptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(10)).build();
//
//        final ClientOptions clientoptions = ClientOptions.builder().socketOptions(socketoptions).build();
//
//        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder().clientOptions(clientoptions)
//                .commandTimeout(Duration.ofMinutes(1))
//                .shutdownTimeout(Duration.ZERO)
//                .build();
//
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
//
//        redisStandaloneConfiguration.setDatabase(0);
//
//        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);

        try {
            final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(10)).build();
            final ClientOptions clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();

            LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                    .clientOptions(clientOptions)
                    .commandTimeout(Duration.ofMinutes(1))
                    .shutdownTimeout(Duration.ZERO)
                    .build();

            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
            redisStandaloneConfiguration.setDatabase(0);

            return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
        } catch (Exception e) {
            // Redis 연결 실패 시, 예외 처리
            // 여기에서는 로깅을 하고, 예외를 무시하여 애플리케이션이 종료되지 않도록 합니다.
            // 실제 운영에서는 로깅을 통해 이슈를 파악할 수 있도록 해야 합니다.
            System.err.println("Failed to connect to Redis. The application will continue running without Redis.");
            e.printStackTrace();
            return null; // 또는 적절한 fallback 설정을 반환합니다.
        }
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
