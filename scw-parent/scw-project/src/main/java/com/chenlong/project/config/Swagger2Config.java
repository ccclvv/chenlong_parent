package com.chenlong.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2Config {

    @Bean("项目模块")
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chenlong.project.controller")) //去对应的包swagger2的注解
                .paths(PathSelectors.any()) //所有路径都生成接口文档
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("七易众筹-系统平台接口文档")
                .description("提供项目模块的文档")
                .termsOfServiceUrl("http://www.ujiuye.com/")
                .version("1.0")
                .build();
    }

}
