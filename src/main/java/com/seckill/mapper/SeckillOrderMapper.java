package com.seckill.mapper;

import com.seckill.model.SeckillOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 秒杀订单数据持久化层
 */
@Mapper
public interface SeckillOrderMapper {
    int addOrder(SeckillOrder order);
}
