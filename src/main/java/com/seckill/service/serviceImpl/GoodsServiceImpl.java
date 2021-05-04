package com.seckill.service.serviceImpl;

import com.seckill.mapper.GoodsMapper;
import com.seckill.mapper.SeckillGoodsMapper;
import com.seckill.model.Goods;
import com.seckill.model.SeckillGoods;
import com.seckill.service.GoodsService;
import com.seckill.vo.GoodsDetailVo;
import com.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品业务逻辑层实现类
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    SeckillGoodsMapper seckillGoodsMapper;

    private String name;
    private String goodsId;
    private String imgPath;
    private Double price;
    private Double seckillPrice;
    private Integer stock_cnt;
    private Date startTime;

    /**
     * 商品列表
     * @return 返回表示层的商品列表集合
     */
    @Override
    public List<GoodsVo> selectList() {
        List<GoodsVo> resultList = new ArrayList<>();
        List<Goods> goodsList =  goodsMapper.selectList();
        for (Goods goods : goodsList) {
            SeckillGoods seckillGoods = seckillGoodsMapper.selectSeckillGoodsById(goods.getGoods_id());
            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setName(goods.getName());
            goodsVo.setGoodsId(goods.getGoods_id());
            goodsVo.setImgPath(goods.getImg_path());
            goodsVo.setPrice(goods.getPrice());
            goodsVo.setSeckillPrice(seckillGoods.getSeckill_price());
            goodsVo.setStock_cnt(seckillGoods.getStock_cnt());
            resultList.add(goodsVo);
        }
        return resultList;
    }


    /**
     * 获取商品详情
     * @param goodsId 接收控制层的goodId
     * @return 返回GoodsDetailVo
     */
    @Override
    public GoodsDetailVo selectGoodsDetailById(String goodsId) {
        Goods goods = goodsMapper.selectOne(goodsId);
        SeckillGoods seckillGoods = seckillGoodsMapper.selectSeckillGoodsById(goodsId);
        GoodsDetailVo goodsDetailVo = new GoodsDetailVo();
        goodsDetailVo.setName(goods.getName());
        goodsDetailVo.setGoodsId(goods.getGoods_id());
        goodsDetailVo.setImgPath(goods.getImg_path());
        goodsDetailVo.setPrice(goods.getPrice());
        goodsDetailVo.setSeckillPrice(seckillGoods.getSeckill_price());
        goodsDetailVo.setStockCnt(seckillGoods.getStock_cnt());
        goodsDetailVo.setStartTime(seckillGoods.getStart_time());
        goodsDetailVo.setEndTime(seckillGoods.getEnd_time());
        return goodsDetailVo;
    }

    /**
     * 库存减去一
     * @param goodsId 获取库存以及传递给Dao层
     * @return 返回执行的操作数
     */
    @Override
    public int reduceStockCnt(String goodsId) {
        SeckillGoods goods = seckillGoodsMapper.selectSeckillGoodsById(goodsId);
        goods.setStock_cnt(goods.getStock_cnt()-1);
        return seckillGoodsMapper.reduceStockCnt(goods);
    }
}
