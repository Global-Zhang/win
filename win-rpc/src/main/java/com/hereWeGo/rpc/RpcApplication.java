package com.hereWeGo.rpc;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hereWeGo.rpc.mapper")
@EnableDubboConfiguration
public class RpcApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcApplication.class,args);
    }
}
