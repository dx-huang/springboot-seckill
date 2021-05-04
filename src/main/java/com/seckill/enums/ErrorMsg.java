package com.seckill.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 秒杀失败枚举类
 */
@AllArgsConstructor
@Getter
public enum ErrorMsg {
    UNSTART("1001","秒杀未开始"),
    CLEARED("1002","库存已告罄");

    private String code;
    private String msg;

}
