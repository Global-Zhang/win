package com.hereWeGo.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.rpc.service.CartService;
import com.hereWeGo.rpc.vo.CartResult;
import com.hereWeGo.rpc.vo.CartVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("cart")
public class CartController {

    @Reference(interfaceClass = CartService.class)
    private CartService cartService;

    /*
    * 加入购物车
    * */
    @RequestMapping("addCart")
    @ResponseBody
    public BaseResult addCart(CartVo cartVo, HttpServletRequest request, HttpServletResponse response){
        cartVo.setAddTime(new Date());
        Admin admin = (Admin) request.getSession().getAttribute("user");
        return cartService.addCart(cartVo, admin);
    }

    /*
    *获取购物车数量
    * */
    @RequestMapping("getCartNum")
    @ResponseBody
    public Integer getCartNum(HttpServletRequest request){
        Admin admin = (Admin) request.getSession().getAttribute("user");
        return cartService.getCartNum(admin);

    }

    /*
    * 跳转购物车页面
    * */
    @RequestMapping("getCartList")
    public String getCartList(HttpServletRequest request, HttpServletResponse response, Model model){

        Admin admin = (Admin) request.getSession().getAttribute("user");
        CartResult cartResult = cartService.getCartList(admin);
        model.addAttribute("cartResult",null==cartResult?new CartResult():cartResult);

        return "cart/list";
    }

}
