package com.pv.commons.domain;

import javax.validation.constraints.NotBlank;

@FunctionalInterface
public interface MessageHolder {
    @NotBlank String message();
}
