package com.zch.autoconfigure.token.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface TokenService<@NotNull T> {
    @NotBlank String generateToken(@NotNull T data);

    @NotNull T extractToken(@NotBlank String token);
}
