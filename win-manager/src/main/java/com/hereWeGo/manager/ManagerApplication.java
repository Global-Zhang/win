package com.hereWeGo.manager;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication(scanBasePackages = {"com.hereWeGo"})
//没有启动类注解会报错 ：Unable to start web server
@SpringBootApplication
@EnableDubboConfiguration
@MapperScan("com.hereWeGo.manager.mapper")
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);    }
}
