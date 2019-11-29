package com.pv.autoconfigure.token;

import com.pv.autoconfigure.PrivateProperties;
import com.pv.autoconfigure.token.resolver.TokenIdHandlerMethodArgumentResolver;
import com.pv.autoconfigure.token.service.JwtTokenService;
import com.pv.autoconfigure.token.service.TokenService;
import io.jsonwebtoken.Jwt;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ConditionalOnClass(TokenService.class)
@EnableConfigurationProperties(PrivateProperties.class)
public class TokenAutoConfiguration {
    @ConditionalOnMissingBean
    @ConditionalOnClass(Jwt.class)
    @Bean
    public TokenService<String> jwtTokenService(PrivateProperties privateProperties) {
        return JwtTokenService.of(privateProperties.getToken().getJwt());
    }

    @ConditionalOnBean(TokenService.class)
    @ConditionalOnClass(HandlerMethodArgumentResolver.class)
    @Bean
    TokenIdHandlerMethodArgumentResolver tokenIdHandlerMethodArgumentResolver(TokenService<String> tokenService) {
        return new TokenIdHandlerMethodArgumentResolver(tokenService);
    }

    @ConditionalOnWebApplication(type = Type.SERVLET)
    @ConditionalOnBean(TokenIdHandlerMethodArgumentResolver.class)
    @Bean
    WebMvcConfigurer customResolverWebMvcConfigurer(TokenIdHandlerMethodArgumentResolver tokenIdHandlerMethodArgumentResolver) {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(0, tokenIdHandlerMethodArgumentResolver);
            }
        };
    }
}
