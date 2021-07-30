package com.slzh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("all")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
                .forCodeGeneration(true)
                .pathMapping("/")// base，最终调用接口后会和paths拼接在一起  当我们项 目放在服务器上面的时候 可能要加项目名
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.slzh.web.controller"))
                .paths(or(regex("/.*")))//过滤的接口  显示所有在 /demo下面的接口  .*表示所有
                .build().apiInfo(title())
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private List<ApiKey> securitySchemes() {
        List<ApiKey> lists=new ArrayList<>();
        lists.add(new ApiKey("Authorization", "portal_core_ck", "header"));
        return lists;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> lists=new ArrayList<>();
        lists.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
        return lists;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> lists=new ArrayList<>();
        lists.add(new SecurityReference("Authorization", authorizationScopes));
        return lists;
    }

    private ApiInfo title() {
        return new ApiInfoBuilder().title("数据平台").version("1.0").description("数据平台").build();
    }

// http://localhost:8999/portal/swagger-ui.html#/
}
