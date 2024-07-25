package com.sparta.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import jakarta.annotation.PostConstruct;

@Configuration
public class RedisConfig {

    @Value("${spring.mail.host}")
    private String redisHost;

    @Value("${MAIL_PORT}")
    private int redisPort;

    private LettuceConnectionFactory lettuceConnectionFactory;

    @PostConstruct
    public void init() {
        lettuceConnectionFactory = new LettuceConnectionFactory(redisHost, redisPort);
        lettuceConnectionFactory.start();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return redisTemplate;
    }
}