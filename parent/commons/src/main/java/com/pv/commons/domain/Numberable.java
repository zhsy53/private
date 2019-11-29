package com.pv.commons.domain;

import javax.validation.constraints.PositiveOrZero;

@FunctionalInterface
public interface Numberable {
    @PositiveOrZero int number();
}
