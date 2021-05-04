package com.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表示层商品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo {
    private String name;
    private String goodsId;
    private String imgPath;
    private Double price;
    private Double seckillPrice;
    private Integer stock_cnt;
}
