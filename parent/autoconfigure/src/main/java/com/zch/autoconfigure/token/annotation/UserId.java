package com.zch.autoconfigure.token.annotation;

import org.checkerframework.checker.index.qual.Positive;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {
    boolean required() default true;

    @Positive long defaultValue() default GUEST_ID;

    @Positive long GUEST_ID = Long.MAX_VALUE - 110;
}
