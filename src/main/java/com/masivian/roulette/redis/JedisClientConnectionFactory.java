package com.masivian.roulette.redis;

import com.masivian.roulette.data.Roulette;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;


public class JedisClientConnectionFactory {


    @Bean
    public RedisTemplate<Long, Roulette> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Roulette> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
   
}


