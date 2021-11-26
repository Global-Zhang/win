package com.hereWeGo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("order")
public class OrderController {


    //跳转订单系统
    @RequestMapping("/toPreOrder")
    public String toPreOrder(HttpServletRequest request, HttpServletResponse response){
        /*
        备用方案-暂不可用涉及到跨域session取不到值，导致重定向路径不可用
        String orderUrl = (String) request.getSession().getAttribute("orderUrl");
        return "redirect:"+orderUrl+"order/preOrder";
        */
        return "redirect:"+response.getHeader("Access-Control-Allow-Origin");
    }


    /**
     * 跳转到我的订单页面(同步通知)
     *
     * @return
     */
    @RequestMapping("myOrder")
    public String myOrder() {
        return "order/myOrder";
    }
}
