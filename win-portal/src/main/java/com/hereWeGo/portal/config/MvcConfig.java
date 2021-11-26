package com.hereWeGo.portal.config;
/*
* MVC配置类
* */

import com.hereWeGo.portal.interceptor.PortalCommonInterceptor;
import com.hereWeGo.portal.interceptor.PortalLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
*  addInterceptor 添加自定义拦截器
*  addPathPatterns 添加拦截请求 /**  表示拦截所有
*  excludePathPatterns 不拦截的请求
* */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private PortalLoginInterceptor loginInterceptor;

    @Autowired
    private PortalCommonInterceptor commonInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor)
                .addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor)
                //本来是拦截所有，除了下面放行界面都需要拦截，现在前台或后台没登陆前也可以浏览商品页,但进入购物车时拦截，目的是登录
                .addPathPatterns("/cart/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/user/login/**")
                .excludePathPatterns("/user/logout/**");
    }

    /*
    * 放行静态资源
    * 因为涉及到放行，现在需要将那些（本来路径属于static的资源是默认可以不用在路径添加static的)添加上static
    * 仅限资源，方法不用
    * */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
                //对应上个 addInterceptors 方法中 registry.excludePathPatterns("/static/**") 的内容
        registry.addResourceHandler("/static/**")
                //对应编译好存在文件中的内容   ./target/classes/static/**  的内容
                .addResourceLocations("classpath:/static/");
    }
}
