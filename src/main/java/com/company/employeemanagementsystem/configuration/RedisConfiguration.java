package com.company.employeemanagementsystem.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.company.employeemanagementsystem.service.AttendanceSubscriber;

@Configuration
public class RedisConfiguration {

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
            MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic("attendance-channel"));
        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter(AttendanceSubscriber attendanceSubscriber) {
        return new MessageListenerAdapter(attendanceSubscriber, "onMessage");
    }

}
