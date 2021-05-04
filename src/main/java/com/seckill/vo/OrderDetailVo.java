package com.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

/**
 * 表示层订单实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailVo {
    private String name;
    private String imgPath;
    private Double price;
    private Date startTime;
    private String telephone;
    private String address;

}
