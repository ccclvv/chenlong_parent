package com.chenlong.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableEurekaClient  //启动注册
@MapperScan("com.chenlong.project.mapper") //配置数据库执行扫描包
@EnableSwagger2  //启动swagger2
public class ProjectApp {
    public static void main(String[] args) {
        SpringApplication.run(ProjectApp.class);
    }
}
