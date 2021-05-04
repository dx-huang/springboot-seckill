package com.seckill.service.serviceImpl;

import com.seckill.mapper.SeckillOrderMapper;
import com.seckill.model.SeckillOrder;
import com.seckill.service.SeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 秒杀订单业务逻辑层实现类
 */
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {
    @Autowired
    SeckillOrderMapper seckillOrderMapper;

    /**
     * 添加订单
     * @param order 订单实体类
     */
    @Override
    public void addOrder(SeckillOrder order) {
        seckillOrderMapper.addOrder(order);
    }
}
