package com.zch.commons.dom.service;

import org.jsoup.nodes.Node;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@FunctionalInterface
public interface NodeParser {
    @NotNull List<@NotNull Node> parse(@NotBlank String s);
}
