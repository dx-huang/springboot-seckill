package com.seckill.service.serviceImpl;

import com.seckill.service.RedisService;
import com.seckill.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Redis业务逻辑层实现类
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisUtil redisUtil;

    /**
     * 使用lua脚本处理超卖问题
     * @param userId  用户id
     * @param goodsId 商品id
     * @return
     */
    @Override
    public String seckillAPI(String userId, String goodsId) {
        //引入lua脚本
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setResultType(Long.class);
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/seckill.lua")));
        //lua脚本所需的key
        List<String> keysList = new ArrayList<>();
        keysList.add(userId);
        keysList.add(goodsId);
        Object result = redisUtil.execute(script, keysList);
        String str = String.valueOf(result);
        switch (str) {
            case "0":
                return "商品" + goodsId + "已售馨";
            case "1":
                return "用户" + userId + "秒杀成功！！！";
            case "2":
                return "用户" + userId + "已秒杀过";
            default:
                return "秒杀异常";
        }
    }



    /**
     * 用redis事务处理超卖问题
     * @param userId 用户id
     * @param goodsId 商品id
     * @return
     */
    public String seckillAPI2(String userId, String goodsId) {
        SessionCallback sessionCallback = new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                //判断商品是否为秒杀时间
                Date time = (Date) redisUtil.get(goodsId + "_startTime");
                if (time == null) return "秒杀时间为空";
                if (new Date().before(time)) return "秒杀时间还未开始";
                //监控goodsId_count这个key
                redisOperations.watch(goodsId + "_count");
                //判断库存是否为0或空
                Object stockCnt = redisUtil.get(goodsId + "_count");
                if (stockCnt == null || (int) stockCnt <= 0) return false;
                //开启事务
                redisOperations.multi();
                redisUtil.decr(goodsId + "_count", 1);
                redisUtil.set(goodsId + "_" + userId, 1);
                return redisOperations.exec();
            }
        };
        redisUtil.execute(sessionCallback);
        if (redisUtil.hasKey(goodsId + "_" + userId)) return "用户" + userId + "秒杀成功！！！";
        return "用户" + userId + "秒杀失败";
    }



    /**
     * 单线程下秒杀商品
     * @param userId 用户id
     * @param goodsId 商品id
     * @return
     */
    public String seckillAPI1(String userId, String goodsId) {
        //判断用户是否已经秒杀过
        if (redisUtil.get(goodsId + "_" + userId) != null) return "用户" + userId + "已秒杀过 不可重复秒杀";
        //判断商品是否为秒杀时间
        Date time = (Date) redisUtil.get(goodsId + "_startTime");
//        if (time == null || new Date().before(time)) return "秒杀时间还未开始";
        if (time == null) return "秒杀时间为空";
        if (new Date().before(time)) return "秒杀时间还未开始";
        //商品库存减一
        int count = (int) redisUtil.get(goodsId + "_count");
        if (count > 0) redisUtil.decr(goodsId + "_count", 1);
        if (count <= 0) return "商品已售馨";

        redisUtil.set(goodsId + "_" + userId, 1);
        return "用户" + userId + "秒杀成功";
    }


    /**
     * Redis商品库存初始化
     * @param goodsId 商品id
     * @param stockCnt 商品库存
     */
    @Override
    public void initData(String goodsId, int stockCnt) {
        //存入商品库存
        redisUtil.set(goodsId + "_count", stockCnt);
        //存入商品秒杀开始时间
        try {
            String dateStr = "2021-04-25 22:44:00";
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = format.parse(dateStr);
            redisUtil.set(goodsId + "_startTime", date);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
