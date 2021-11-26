package com.hereWeGo.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Test
    public void testGetIncrement(){
        RedisAtomicLong entityIdCounter = new RedisAtomicLong("order:",redisTemplate.getConnectionFactory());
        long increment = entityIdCounter.getAndIncrement();
        System.out.println(increment);
    }
}
