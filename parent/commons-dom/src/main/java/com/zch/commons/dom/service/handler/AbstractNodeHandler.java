package com.zch.commons.dom.service.handler;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import javax.validation.constraints.NotNull;

public abstract class AbstractNodeHandler<T> implements NodeHandler<T> {
    @Override
    public T handle(@NotNull Node node) {
        if (node instanceof TextNode) {
            return this.handleTextNode((TextNode) node);
        }

        if (node instanceof Element) {
            return this.handleElement((Element) node);
        }

        throw new UnsupportedOperationException("handle node type " + node.getClass().getSimpleName());
    }

    protected abstract T handleTextNode(@NotNull TextNode textNode);

    protected abstract T handleElement(@NotNull Element element);
}
