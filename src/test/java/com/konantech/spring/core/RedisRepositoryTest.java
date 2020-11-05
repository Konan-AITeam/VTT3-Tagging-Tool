package com.konantech.spring.core;

import com.konantech.spring.CommonTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class RedisRepositoryTest extends CommonTests {

    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void redisStringTest() throws Exception {

        String key = "test123";
        String value = "hello world";
        int second = 100;
        //assertTrue(redisRepository.setObject(key, value, second));
        //assertEquals(value, redisRepository.getObject(key));
    }
}