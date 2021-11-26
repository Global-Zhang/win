package com.hereWeGo.portal.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.hereWeGo.common.pojo.Admin;
import com.hereWeGo.common.utils.CookieUtil;
import com.hereWeGo.common.utils.JsonUtil;
import com.hereWeGo.sso.service.SSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/*
* 前台登录拦截器
* */
@Component
public class PortalLoginInterceptor implements HandlerInterceptor {

    @Reference(interfaceClass = SSOService.class)
    private SSOService ssoService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${user.ticket}")
    private String userTicket;

    /*
    * 请求处理的方法之前执行
    * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取用户票据
        String ticket = CookieUtil.getCookieValue(request, "userTicket");
        if (!StringUtils.isEmpty(ticket)){
            //如果票据存在，进行验证
            Admin admin = ssoService.validate(ticket);
            if (null != admin){
                //将用户信息存入session中，用于页面反显
                request.getSession().setAttribute("user",admin);
                //重新设置失效时间
                ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
                valueOperations.set(userTicket+":"+ticket, JsonUtil.object2JsonStr(admin),30, TimeUnit.MINUTES);
                //返回true表示继续执行下一步操作
                return true;
            }else {
                //清除session，将原本的过期的用户票据进行清空
                request.getSession().removeAttribute("user");
            }
        }else {
            //清除session
            request.getSession().removeAttribute("user");
        }
        //票据不存在或者用户验证失败，重定向到登录页面
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }


    /*
    * 请求处理方法之后执行
    * */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    /*
    * 处理后执行的清理工作
    * */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
