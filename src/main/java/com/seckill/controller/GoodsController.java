package com.seckill.controller;

import com.seckill.enums.ErrorMsg;
import com.seckill.model.SeckillOrder;
import com.seckill.service.GoodsService;
import com.seckill.service.SeckillOrderService;
import com.seckill.vo.GoodsDetailVo;
import com.seckill.vo.GoodsVo;
import com.seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Date;
import java.util.List;

/**
 * 商品控制类
 */
@Controller
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    SeckillOrderService orderService;

    /**
     * 商品列表
     * @param model 传输List<GoodsVo>给前端页面
     * @return 响应回list.html
     */
    @GetMapping("/")
    public String goodsList(Model model){
        List<GoodsVo> goodsList = goodsService.selectList();
        model.addAttribute("goodsList",goodsList);
        return "list";
    }

    /**
     * 商品详情表
     * @param goodsId 接收前端请求的商品Id
     * @param model 传输GoodsDetailVo给前端页面
     * @return
     */
    @GetMapping("/goodsDetail/{goodsId}")
    public String goodsDetail(@PathVariable String goodsId,Model model){
        GoodsDetailVo goodsDetailVo = goodsService.selectGoodsDetailById(goodsId);
        model.addAttribute("goodsDetailVo",goodsDetailVo);
        //返回秒杀状态status 0:秒杀开始前 1:秒杀已结束 2:秒杀进行中
        int status;
        int remainSeconds = -1;
        Date startTime = goodsDetailVo.getStartTime();
        Date endTime = goodsDetailVo.getEndTime();
        Date nowTime = new Date();
        if (nowTime.before(startTime)){ //秒杀开始前
            status = 0;
            remainSeconds = (int)((startTime.getTime() - nowTime.getTime())/1000);
        }else if (nowTime.after(endTime)){ //秒杀已经结束
            status = 1;
        }else {
            status = 2; //秒杀进行中
        }
        model.addAttribute("status",status);
        model.addAttribute("remainSeconds",remainSeconds);
        return "goodsDetail";
    }

    /**
     * 商品秒杀抢购
     * @param goodsId 获取抢购的商品id
     * @param model 存储信息和数据给前端
     * @return 错误返回fail.html 成功返回OrderDetail.html
     */
    @PostMapping("/toSeckill")
    public String toSeckill(String goodsId,Model model){
        //判断秒杀时间是否开始以及秒杀时间是否已截止
        GoodsDetailVo gdv = goodsService.selectGoodsDetailById(goodsId);
        if (new Date().before(gdv.getStartTime())){
            model.addAttribute("msg", ErrorMsg.UNSTART.getMsg());
            return "fail";
        }
        if(new Date().after(gdv.getEndTime())){
            model.addAttribute("msg",ErrorMsg.CLEARED.getMsg());
            return "fail";
        }

        //库存减一
        goodsService.reduceStockCnt(goodsId);
        //订单加一
        SeckillOrder order = new SeckillOrder();
        order.setOrder_id("111xxx");
        order.setUser_id("222222");
        order.setGoods_id(goodsId);
        order.setTelephone("10086");
        order.setAddress("白云学院");
        orderService.addOrder(order);

        //返回OrderDetailVo给前端页面
        OrderDetailVo odv = new OrderDetailVo();
        odv.setName(gdv.getName());
        odv.setImgPath(gdv.getImgPath());
        odv.setPrice(gdv.getSeckillPrice());
        odv.setStartTime(new Date());
        odv.setTelephone(order.getTelephone());
        odv.setAddress(order.getAddress());
        model.addAttribute("orderDetailVo",odv);
        return "orderDetail";
    }


}
