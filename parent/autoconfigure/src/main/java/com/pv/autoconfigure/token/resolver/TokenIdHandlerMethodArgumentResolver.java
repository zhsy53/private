package com.pv.autoconfigure.token.resolver;

import com.pv.autoconfigure.token.annotation.UserId;
import com.pv.autoconfigure.token.service.TokenService;
import com.pv.commons.exception.TokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nullable;
import java.util.Optional;

@RequiredArgsConstructor
public class TokenIdHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    private final TokenService<String> tokenService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> type = parameter.getParameterType();
        return parameter.getParameterAnnotation(UserId.class) != null && (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {
        return Optional.ofNullable(webRequest.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(StringUtils::hasText)
                .map(tokenService::extractToken)
                .map(Long::parseLong)
                .orElseThrow(() -> new TokenException("用户未登录"));
    }
}
