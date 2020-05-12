package com.zch.commons.dom.util;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.zch.commons.dom.util.BaseUtils.trimBeginAndEnd;
import static java.util.stream.Collectors.toList;

public interface DomUtils {
    static boolean isNeedToHandle(@NotNull Node node) {
        return node instanceof Element || (node instanceof TextNode && isNeedToHandleAfterTrimText((TextNode) node));
    }

    static boolean isNeedToHandleAfterTrimText(@NotNull TextNode node) {
        var text = trimBeginAndEnd(node.text());

        node.text(text);

        return !text.isBlank();
    }

    @NotNull
    static List<@NotNull Node> listNeedToHandleChild(@NotNull Node node) {
        return node.childNodes().stream().filter(DomUtils::isNeedToHandle).collect(toList());
    }
}
