package com.seckill.mapper;

import com.seckill.model.SeckillGoods;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀商品数据持久化层
 */
@Mapper
public interface SeckillGoodsMapper {

    SeckillGoods selectSeckillGoodsById(String goods_Id);
    int reduceStockCnt(SeckillGoods sg);
}
