package com.zch.commons.dom.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data(staticConstructor = "of")
public class AppElement {
    @NotNull
    private final Type type;
    @NotBlank
    private final String value;

    @NotNull
    public static AppElement text(@NotBlank String text) {
        return of(Type.TEXT, text);
    }

    @Override
    public String toString() {
        if (type == Type.TEXT) {
            return value;
        }

        var tag = type.name().toLowerCase();
        return "<" + tag + " src=\"" + value + "\">" + "<" + tag + "/>";
    }
}
