package com.chenlong.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient  //启动注册
@MapperScan("com.chenlong.order.mapper") //配置数据库执行扫描包
@EnableSwagger2  //启动swagger2
@EnableFeignClients  //开启远程调用
@EnableCircuitBreaker  //开启熔断
public class OrderApp {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class);
    }
}
