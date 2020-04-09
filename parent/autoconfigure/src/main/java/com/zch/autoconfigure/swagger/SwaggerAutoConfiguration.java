package com.zch.autoconfigure.swagger;

import com.zch.autoconfigure.ZchProperties;
import com.zch.autoconfigure.ZchProperties.Swagger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Optional.ofNullable;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@EnableConfigurationProperties(ZchProperties.class)
@ConditionalOnClass(Docket.class)
@Configuration
class SwaggerAutoConfiguration {
    @Value("${spring.application.name:}")
    private String path;

//    @ConditionalOnProperty(prefix = "zch.swagger", name = "enabled", havingValue = "true")
    @Bean
    Docket createRestApi(ZchProperties properties) {
        var param = new ParameterBuilder()
                .name(HttpHeaders.AUTHORIZATION)
                .description("Token 填这里")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();

        @NotNull Swagger swagger = properties.getSwagger();
        var info = new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(ofNullable(swagger.getDescription()).orElse(swagger.getTitle()))
                .version(swagger.getVersion())
                .build();

        return new Docket(SWAGGER_2)
                .pathMapping(path)
                .apiInfo(info)
                .enable(swagger.isEnabled())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zch"))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(List.of(param));
    }

//    @ConditionalOnProperty(prefix = "zch.swagger", name = "enabled", havingValue = "false", matchIfMissing = true)
//    @RestController
//    @Log4j2
//    static class DisableSwaggerUiController {
//        @GetMapping(value = "swagger-ui.html")
//        public void getSwagger(HttpServletResponse response) {
//            log.error("swagger disabled");
//            response.setStatus(NOT_FOUND.value());
//        }
//    }
}
