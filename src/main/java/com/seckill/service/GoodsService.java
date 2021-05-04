package com.seckill.service;

import com.seckill.vo.GoodsDetailVo;
import com.seckill.vo.GoodsVo;

import java.util.List;

/**
 * 商品业务逻辑层接口
 */
public interface GoodsService {
    List<GoodsVo> selectList();
    GoodsDetailVo selectGoodsDetailById(String goodsId);
    int reduceStockCnt(String goodsId);
}
