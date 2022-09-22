package com.spring.config;

import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 监听对象(集成MessageListener就能拿到消息体和频道名)
 */
@Component
public class RedisMessageListener implements MessageListener {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        System.out.println("渠道:"+redisTemplate.getStringSerializer().deserialize(message.getChannel()));
        System.out.println("11111111"+message.getChannel());
        System.out.println("消息体："+new String(message.getBody()));
        System.out.println("渠道："+new String(message.getChannel()));
    }
}
