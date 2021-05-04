package com.seckill.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private long id;
    private String goods_id;
    private String name;
    private String type;
    private Double price;
    private String img_path;
    private int stock_cnt;
}
