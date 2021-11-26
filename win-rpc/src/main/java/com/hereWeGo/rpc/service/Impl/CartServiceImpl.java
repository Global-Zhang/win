package com.hereWeGo.rpc.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.common.utils.JsonUtil;
import com.hereWeGo.rpc.service.CartService;
import com.hereWeGo.rpc.vo.CartResult;
import com.hereWeGo.rpc.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
 * 购物车服务
 * */
@Service(interfaceClass = CartService.class)
@Component
public class CartServiceImpl implements CartService{

    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${user.cart}")
    private String userCart;
    private HashOperations<String,String,String> hashOperations = null;

    /*
     * 添加购物车服务
     * */
    @Override
    public BaseResult addCart(CartVo cartVo, Admin admin) {
        //如果用户信息不存在，直接返回
        if (null == admin || null == admin.getAdminId()){
            return BaseResult.error();
        }
        Short userId = admin.getAdminId();
        //查询当前购物车用户信息
        hashOperations = redisTemplate.opsForHash();
        Map<String, String> cartMap = hashOperations.entries(userCart + ":" + userId);
        if (!CollectionUtils.isEmpty(cartMap)){
            //如果信息不为空，修改购物车信息
            //根据商品id获取购物车信息
            String cartStr = cartMap.get(String.valueOf(cartVo.getGoodsId()));
            //如果商品存在，修改数量和价格
            if (!StringUtils.isEmpty(cartStr)){
                CartVo vo = JsonUtil.jsonStr2Object(cartStr, CartVo.class);
                //修改商品数量
                vo.setGoodsNum(vo.getGoodsNum()+cartVo.getGoodsNum());
                //修改商品价格
                vo.setMarketPrice(cartVo.getMarketPrice());
                //从新添加值map，覆盖之前的商品对象
                cartMap.put(String.valueOf(vo.getGoodsId()),JsonUtil.object2JsonStr(vo));
            }else{
                //如果商品不存在，新增
                cartMap.put(String.valueOf(cartVo.getGoodsId()),JsonUtil.object2JsonStr(cartVo));
            }
        }else{
            //购物车为空，新增购物车信息
            cartMap = new HashMap<>();
            cartMap.put(String.valueOf(cartVo.getGoodsId()),JsonUtil.object2JsonStr(cartVo));
        }
        hashOperations.putAll(userCart+":"+userId,cartMap);
        return BaseResult.success();
    }




    /*
     * 查询购物车服务
     * */
    @Override
    public Integer getCartNum(Admin admin) {
        //如果用户信息不存在，直接返回
        if (null == admin || null == admin.getAdminId()){
            return 0;
        }
        //初始化购物车数量
        int result = 0;
        hashOperations = redisTemplate.opsForHash();
        Map<String, String> cartMap = hashOperations.entries(userCart + ":" + admin.getAdminId());

        //如果购物车信息不为空，累加购物车数量
        if(!CollectionUtils.isEmpty(cartMap)){
            for (Map.Entry<String, String> entry : cartMap.entrySet()) {
                CartVo cartVo = JsonUtil.jsonStr2Object(entry.getValue(), CartVo.class);
                result += cartVo.getGoodsNum();
            }
        }
        return result;
    }


    /*
    * 清除购物车信息
    * */
    @Override
    public BaseResult clearCart(Admin admin) {
        //判断用户是否存在
        if (null == admin || null == admin.getAdminId()){
            return null;
        }
        //从redis获取购物车信息
        hashOperations = redisTemplate.opsForHash();
        Map<String, String> cartMap = hashOperations.entries(userCart + ":" + admin.getAdminId());

        //判断是否存在购物车信息
        if(CollectionUtils.isEmpty(cartMap)){
            BaseResult.error();
        }

        redisTemplate.delete(userCart+":"+admin.getAdminId());
        return BaseResult.success();
    }

    /*
    * 获取购物车列表--进入购物车详情页
    * */
    @Override
    public CartResult getCartList(Admin admin) {

        //判断用户是否存在
        if (null == admin || null == admin.getAdminId()){
            return null;
        }

        //初始化返回对象
        CartResult cartResult = null;

        //从redis获取购物车信息
        hashOperations = redisTemplate.opsForHash();
        Map<String, String> cartMap = hashOperations.entries(userCart + ":" + admin.getAdminId());

        //判断是否存在购物车信息
        if(!CollectionUtils.isEmpty(cartMap)){

            //如果存在
            cartResult = new CartResult();

            //购物车列表
            List<CartVo> cartList = cartMap.values()
                                            .stream()
                                            .map(e -> JsonUtil.jsonStr2Object(e, CartVo.class))
                                            .collect(Collectors.toList());

            //总价
            BigDecimal totalPrice = cartList.stream()
                                            .map(e -> e.getMarketPrice()
                                                    .multiply(new BigDecimal(String.valueOf(e.getGoodsNum()))))
                                                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            //保留小数点后两位，四舍五入
            totalPrice.setScale(2,BigDecimal.ROUND_HALF_UP);

            cartResult.setCartList(cartList);
            cartResult.setTotalPrice(totalPrice);
        }
        return cartResult;
    }
}
