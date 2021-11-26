package com.hereWeGo.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.common.enums.BaseResultEnum;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.result.BaseResult;
import com.hereWeGo.manager.service.CookieService;
import com.hereWeGo.sso.service.SSOService;
import org.apache.ibatis.annotations.Param;
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

    @RequestMapping("login")
    @ResponseBody
    public BaseResult login(Admin admin, HttpServletRequest request, HttpServletResponse response, @Param("vertify") String vertify){
        //获取生成后存入session的验证码
        String capText =(String) request.getSession().getAttribute("pictureVerifyKey");
        BaseResult baseResult = new BaseResult();
        if (StringUtils.isEmpty(vertify.trim()) || !vertify.trim().equals(capText) ){
            baseResult.setCode(BaseResultEnum.PASS_ERROR_03.getCode());
            baseResult.setMessage(BaseResultEnum.PASS_ERROR_03.getMessage());
            return baseResult;
        }
        //登录成功并生成票据
        String ticket = ssoService.login(admin);
        //如果票据生成成功，将票据写入cookie
        if (!StringUtils.isEmpty(ticket)) {
            boolean result = cookieService.setCookie(request, response, ticket);
            //用户信息存入session，用于页面反显
            request.getSession().setAttribute("user",admin);
            return result?BaseResult.success():BaseResult.error();
        }
        baseResult.setCode(BaseResultEnum.PASS_ERROR_04.getCode());
        baseResult.setMessage(BaseResultEnum.PASS_ERROR_04.getMessage());
        return baseResult;
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
}
