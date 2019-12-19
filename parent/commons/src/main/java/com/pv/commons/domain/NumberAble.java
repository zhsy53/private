package com.pv.commons.domain;

import javax.validation.constraints.PositiveOrZero;

@FunctionalInterface
public interface NumberAble {
    @PositiveOrZero int number();
}
