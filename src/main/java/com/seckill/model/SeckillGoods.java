package com.seckill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 秒杀商品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillGoods {
    private long id;
    private String goods_id;
    private double seckill_price;
    private Integer stock_cnt;
    private Date start_time;
    private Date end_time;
}
