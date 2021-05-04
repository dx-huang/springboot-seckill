package com.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 表示层商品详情实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDetailVo {
    private String name;
    private String goodsId;
    private String imgPath;
    private Double price;
    private Double seckillPrice;
    private Integer stockCnt;
    private Date startTime;
    private Date endTime;
}
