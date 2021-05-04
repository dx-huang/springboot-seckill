package com.seckill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 秒杀订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeckillOrder {
    private long id;
    private String order_id;
    private String user_id;
    private String goods_id;
    private String telephone;
    private String address;

}
