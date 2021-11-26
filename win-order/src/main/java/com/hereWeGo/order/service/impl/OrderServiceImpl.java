package com.hereWeGo.order.service.impl;


import com.hereWeGo.common.enums.OrderStatus;
import com.hereWeGo.common.enums.PayStatus;
import com.hereWeGo.common.enums.PromTypeStatus;
import com.hereWeGo.common.enums.SendStatus;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.order.mapper.OrderGoodsMapper;
import com.hereWeGo.order.mapper.OrderMapper;
import com.hereWeGo.order.pojo.Order;
import com.hereWeGo.order.pojo.OrderExample;
import com.hereWeGo.order.pojo.OrderGoods;
import com.hereWeGo.order.service.OrderService;
import com.hereWeGo.rpc.vo.CartResult;
import com.hereWeGo.rpc.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderGoodsMapper orderGoodsMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Value("${redis.order.increment}")
    private String redisOrderIncrement;


    @Override
    public BaseResult orderSave(Admin admin, CartResult cartResult) {
        //创建order对象
        Order order = new Order();
        //订单编号 shop_yyyyy-MM-dd HH:mm:ss_自增id
        String orderSn = "shop_"+ DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                                                    .format(LocalDateTime.now())
                                                    +"_"
                                                    +getIncrement(redisOrderIncrement);
        order.setOrderSn(orderSn);
        //用户id
        order.setUserId(admin.getAdminId().intValue());
        //订单状态(待确认)
        order.setOrderStatus(OrderStatus.no_confirm.getStatus());
        //发货状态(未发货)
        order.setShippingStatus(SendStatus.no_pay.getStatus());
        //支付状态
        order.setPayStatus(PayStatus.no_pay.getStatus());
        //商品总价
        order.setGoodsPrice(cartResult.getTotalPrice());
        //应付金额
        order.setOrderAmount(cartResult.getTotalPrice());
        //订单总价
        order.setTotalAmount(cartResult.getTotalPrice());
        //订单时间
        Long addTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        order.setAddTime(addTime.intValue());
        int result = orderMapper.insertSelective(order);
        //存储成功
        if (result>0){
            List<OrderGoods> orderGoodsList  = new ArrayList<>();
            for (CartVo cartVo : cartResult.getCartList()) {
                //创建orderGoods对象
                OrderGoods orderGoods = new OrderGoods();
                //订单id
                orderGoods.setOrderId(order.getOrderId());
                //商品id
                orderGoods.setGoodsId(cartVo.getGoodsId());
                //商品名称
                orderGoods.setGoodsName(cartVo.getGoodsName());
                //商品价格
                orderGoods.setGoodsPrice(cartVo.getMarketPrice());
                //商品数量
                orderGoods.setGoodsNum(cartVo.getGoodsNum().shortValue());
                //订单方式
                orderGoods.setPromType(PromTypeStatus.normal.getStatus());
                //发货状态
                orderGoods.setIsSend(SendStatus.no_pay.getStatus());
                //添加到订单商品对象列表
                orderGoodsList.add(orderGoods);
            }
            //批量插入
            result = orderGoodsMapper.insertOrderGoodsBatch(orderGoodsList);
            if (result>0){
                BaseResult baseResult = BaseResult.success();
                baseResult.setMessage(orderSn);
                return baseResult;
            }
        }
        return BaseResult.error();
    }

    @Override
    public Order selectOrderByOrderSn(String orderSn) {
        OrderExample example = new OrderExample();
        example.createCriteria().andOrderSnEqualTo(orderSn);
        List<Order> orders = orderMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(orders) || orders.size()>1){
            return null;
        }
        return orders.get(0);
    }

    //自增key
    private Long getIncrement(String key){
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key+":",redisTemplate.getConnectionFactory());
        long increment = entityIdCounter.getAndIncrement();
        return increment;
    }
}
