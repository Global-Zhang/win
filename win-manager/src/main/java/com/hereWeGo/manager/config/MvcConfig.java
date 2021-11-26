package com.hereWeGo.manager.config;
/*
* MVC配置类
* */

import com.hereWeGo.manager.interceptor.ManagerLoginInterceptor;
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
    private ManagerLoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                //放行验证码 ImagesController
                .excludePathPatterns("/image/**")
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
