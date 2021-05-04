package com.seckill.controller;

import com.seckill.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Redis控制类
 */
@RestController
public class RedisController {
    @Autowired
    RedisService redisService;

    /**
     * 商品秒杀API
     * @param userId 用户id
     * @param goodsId 商品id
     * @return
     */
    @GetMapping("/seckillAPI")
    public String seckillAPI(String userId,String goodsId){

        if (userId == null || goodsId == null) return "seckillAPI fail";
        String result = redisService.seckillAPI(userId,goodsId);
        System.out.println(result);
        return result;
    }

    /**
     * 初始化Redis中的商品数据
     * @param goodsId 商品Id
     * @param stockCnt 商品库存
     * @return
     */
    @GetMapping("/initData")
    public String initData(String goodsId,int stockCnt){
        redisService.initData(goodsId,stockCnt);
        return "initData success";
    }

}
