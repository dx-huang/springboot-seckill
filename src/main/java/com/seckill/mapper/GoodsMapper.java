package com.seckill.mapper;

import com.seckill.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 商品数据持久化层
 */
@Mapper
public interface GoodsMapper {

    List<Goods> selectList();
    Goods selectOne(String goods_id);
}
