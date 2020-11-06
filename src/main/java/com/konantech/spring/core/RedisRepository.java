package com.konantech.spring.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static Logger log = LoggerFactory.getLogger(RedisRepository.class);

    public boolean pushObject(String key, Object value) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            log.debug("SET expire: " + redisTemplate.getExpire(key) + " ," + key + " ," + value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public Object popObject(String key) {
        try {
            Object value = redisTemplate.opsForList().rightPop(key);
            log.debug("GET expire: " + redisTemplate.getExpire(key) + " ," + key + " ," + value);
            return value;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public List<Object> popObjectRO(String key) {
        try {
            Long size = redisTemplate.opsForList().size(key);
            List<Object> result = redisTemplate.opsForList().range(key, 0, size);
            log.debug("GET expire: " + redisTemplate.getExpire(key) + " ," + key + " ," + size);
            return result;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }



    public boolean setObject(String key, Object value) {
        return setObject(key,value,-1);
    }

    public boolean setObject(String key, Object value, int second) {
        try {
            redisTemplate.opsForValue().set(key, value);
            if(second > 0) {
                redisTemplate.expire(key, second, TimeUnit.SECONDS);
            }
            log.debug("SET expire: " + redisTemplate.getExpire(key) + " ," + key + " ," + value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public Object getObject(String key) {
        try {
            Object value = redisTemplate.opsForValue().get(key);
            log.debug("GET expire: " + redisTemplate.getExpire(key) + " ," + key + " ," + value);
            return value;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public boolean delString(String key) {
        try {
            redisTemplate.delete(key);
            System.out.println("GET delete key = " + key );
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
