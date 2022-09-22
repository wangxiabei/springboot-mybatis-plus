package com.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 配置监听适配器、消息监听容器
 * 消息监听容器增加监听的消息，第一个参数是监听适配器，第2个参数是监听的频道。
 */
@Configuration
public class RedisMessageConfig {

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //可以添加多个messageListener,配置不同的交换机
        container.addMessageListener(listenerAdapter, new PatternTopic("channel:set"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RedisMessageListener redisMessageListener){
        System.out.println("消息适配器1");
        return new MessageListenerAdapter(redisMessageListener);
    }
}
