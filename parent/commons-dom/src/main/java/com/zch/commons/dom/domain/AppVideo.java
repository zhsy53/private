package com.zch.commons.dom.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data(staticConstructor = "of")
public class AppVideo {
    @NotBlank
    private final String src;
    @NotBlank
    private final String ref;
}
