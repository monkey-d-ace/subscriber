package org.onepiece.message.config;

import org.onepiece.message.component.EmailService;
import org.onepiece.message.component.ShortMessageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {
    @Bean
    public EmailService emailService() {
        return new EmailService();
    }

    @Bean
    public ShortMessageService shortMessageService() {
        return new ShortMessageService();
    }

    @Bean(name = "emailListener")
    public MessageListenerAdapter messageListenerAdapter(EmailService emailService) {
        return new MessageListenerAdapter(emailService, "onReceiveMessage");
    }

    @Bean(name = "shortMessageListener")
    public MessageListenerAdapter messageListenerAdapter(ShortMessageService shortMessageService) {
        return new MessageListenerAdapter(shortMessageService, "whenReceiveThen");
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(@Qualifier(value = "emailListener") MessageListenerAdapter emailListener,
                                                                       @Qualifier(value = "shortMessageListener") MessageListenerAdapter shortMessageListener,
                                                                       RedisConnectionFactory redisConnectionFactory
                                                                       ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.addMessageListener(emailListener, new PatternTopic("email"));
        container.addMessageListener(shortMessageListener, new PatternTopic("short message"));
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }
}
