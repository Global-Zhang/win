package com.hereWeGo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
    /**
     * 页面跳转，restful风格
     */
    @RequestMapping("/{page}")
    public String page(@PathVariable String page){
        System.out.println(page);
        return page;
    }
    /**
     * 跳转到首页
     */
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /*
    * 精确匹配登录
    * 为com.hereWeGo.order.interceptor.OrderInterceptor{
    *                                   preHandle(){
    *                                       response.sendRedirect()
    *                                   }
    *                                 }
    * 提供跳转
    * */
    @RequestMapping("login")
    public String login(String redirectUrl, Model model){
        model.addAttribute("redirectUrl",redirectUrl);
        return "login";
    }
}
