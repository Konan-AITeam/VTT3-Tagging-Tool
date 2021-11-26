package com.konantech.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /*
    버전 올라갈때마다 약간씩 설정이 자주 바뀌니,
    http://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
    참고해서 업그레이드 할것!!
    */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.konantech.spring.controller.rest"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securitySchemes(newArrayList(authorization()))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        newArrayList(new ResponseMessageBuilder()
                                        .code(500)
                                        .message("500 Message!")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("403 Forbidden!")
                                        .build())
                );

    }
    private ApiKey authorization() {
        return new ApiKey("x-auth-token", "api-key", "header");
    }
    private ApiInfo apiInfo() {
        return new ApiInfo(
                "SPRING REST API DEMO",
                "Api 에 대한 상세 페이지 입니다.",
                "1.1",
                "Terms of service",
                new Contact("코난테크놀로지", "http://www.konantech.com", ""),
                "License of Konantechnology", "http://www.konantech.com", Collections.emptyList());
    }

}
