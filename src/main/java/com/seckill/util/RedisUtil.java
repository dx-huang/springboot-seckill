package com.seckill.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Redis工具类
 */
@Component
public class RedisUtil {
    @Autowired
    RedisTemplate redisTemplate;

    //设置key和value
    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    //设置key和value 以及过期时间
    public void set(String key,Object value,long timeout){
        redisTemplate.opsForValue().set(key,value,timeout);
    }

    //查询key的value
    public Object get(String key){
        if (redisTemplate.hasKey(key)) {
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }

    //删除key
    public boolean delete(String key){
        redisTemplate.delete(key);
        if (redisTemplate.hasKey(key)) return false;
        return true;
    }

    //数字减一
    public void decr(String key,int decrNum){
        redisTemplate.opsForValue().decrement(key,decrNum);
    }

    //事务执行
    public Object execute(SessionCallback sessionCallback){
        return redisTemplate.execute(sessionCallback);
    }

    //判断redis中是否含有key
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    //redis脚本执行
    public Object execute(RedisScript script, List keys){
        return redisTemplate.execute(script,keys);
    }



}
