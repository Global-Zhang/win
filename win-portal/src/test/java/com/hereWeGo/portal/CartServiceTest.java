package com.hereWeGo.portal;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.rpc.service.CartService;
import com.hereWeGo.rpc.vo.CartVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Date;


@SpringBootTest
public class CartServiceTest {

    @Reference(interfaceClass = CartService.class)
    private CartService cartService;

    @Test
    public void addCartTest(){
        Admin admin = new Admin();
        admin.setAdminId((short)1);
        CartVo cartVo = new CartVo();
        cartVo.setGoodsId(12345);
        cartVo.setGoodsName("小垃圾");
        cartVo.setGoodsNum(10);
        cartVo.setMarketPrice(new BigDecimal("100"));
        cartVo.setAddTime(new Date());
        cartService.addCart(cartVo,admin);

    }

    @Test
    public void getCartNumTest(){
        Admin admin = new Admin();
        admin.setAdminId((short)1);
        Integer cartNum =  cartService.getCartNum(admin);
        System.out.println(cartNum);
    }
}
