package com.pv.commons.domain;

import javax.validation.constraints.NotBlank;

@FunctionalInterface
public interface Descriptive {
    @NotBlank String description();
}
