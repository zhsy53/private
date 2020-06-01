package com.zch.autoconfigure.token.resolver;

import com.zch.autoconfigure.token.annotation.UserId;
import com.zch.autoconfigure.token.service.TokenService;
import com.zch.commons.exception.TokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

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
        @NotNull UserId userId = parameter.getParameterAnnotation(UserId.class);
        assert userId != null;

        String header = webRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header)) {
            return Long.parseLong(tokenService.extractToken(header));
        }

        if (userId.required()) {
            throw new TokenException("用户未登录");
        }

        return userId.defaultValue();
    }
}
