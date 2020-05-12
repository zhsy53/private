package com.zch.commons.dom.service.handler;

import org.jsoup.nodes.Node;

import javax.validation.constraints.NotNull;

public interface NodeHandler<T> {
    T handle(@NotNull Node node);
}
