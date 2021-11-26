package com.hereWeGo.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.pojo.AdminWithBLOBs;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.portal.service.CookieService;
import com.hereWeGo.portal.service.UserService;
import com.hereWeGo.rpc.service.SendMessageService;
import com.hereWeGo.sso.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserController {

    @Reference(interfaceClass = SSOService.class)
    private SSOService ssoService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private UserService userService;
    @Reference(interfaceClass = SendMessageService.class)
    private SendMessageService sendMessageService;

    @RequestMapping("login")
    @ResponseBody
    public BaseResult login(Admin admin, HttpServletRequest request, HttpServletResponse response){
        //登录成功并生成票据
        String ticket = ssoService.login(admin);
        //如果票据生成成功，将票据写入cookie
        if (!StringUtils.isEmpty(ticket)) {
            boolean result = cookieService.setCookie(request, response, ticket);
            //用户信息存入session，用于页面反显
            request.getSession().setAttribute("user",admin);
            return result?BaseResult.success():BaseResult.error();
        }
        return BaseResult.error();
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        //清除redis
        String ticket = cookieService.getCookie(request);
        ssoService.logout(ticket);
        //清除session
        request.getSession().removeAttribute("user");
        //清除cookie
        cookieService.deleteCookie(request,response);
        return "login";
    }

    @RequestMapping("register")
    @ResponseBody
    public BaseResult register(AdminWithBLOBs admin){
        BaseResult baseResult = userService.saveUser(admin);
        if(200 == baseResult.getCode()){
           /*
           加入rabbitmq前
            BaseResult baseResult1 = sendMessageService.sendMessage(admin.getEmail());
            if (200 == baseResult1.getCode()){
                return BaseResult.success();
            }
            */
            return BaseResult.success();
        }
        return BaseResult.error();
    }
}
