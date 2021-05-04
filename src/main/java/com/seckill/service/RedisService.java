package com.seckill.service;

public interface RedisService {
    String seckillAPI(String userId,String goodsId);
    void initData(String goodsId,int stockCnt);
}
