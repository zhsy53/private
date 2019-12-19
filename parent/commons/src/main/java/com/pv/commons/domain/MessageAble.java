package com.pv.commons.domain;

import javax.validation.constraints.NotBlank;

@FunctionalInterface
public interface MessageAble {
    @NotBlank String message();
}
