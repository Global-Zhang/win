package com.hereWeGo.portal.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 前台门户全局拦截器
* 将${win.order.url}值配置到controller时，不能使用@Value注解，特采用此类来替代
* */
@Component
public class PortalCommonInterceptor implements HandlerInterceptor {

    @Value("${win.order.url}")
    private  String winOrderUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取application对象
        ServletContext servletContext = request.getSession().getServletContext();
        /*
        备用方案-暂不可用涉及到跨域session取不到值，导致重定向路径不可用
        servletContext.setAttribute("orderUrl", winOrderUrl);
        */
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:9098/win-order/order/preOrder");

        return true;
    }
}
