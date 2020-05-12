package com.zch.commons.dom.service.handler;

import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import javax.validation.constraints.NotNull;
import java.util.Deque;
import java.util.LinkedList;

import static com.zch.commons.dom.domain.ElementConstant.*;
import static com.zch.commons.dom.util.BaseUtils.joinTogether;
import static com.zch.commons.dom.util.BaseUtils.reverseForeach;
import static com.zch.commons.dom.util.DomUtils.isNeedToHandleAfterTrimText;
import static com.zch.commons.dom.util.DomUtils.listNeedToHandleChild;

@NoArgsConstructor(staticName = "instance")
public class NodeParseHandler extends AbstractNodeHandler<@NotNull Deque<@NotNull Node>> {
    private final @NotNull Deque<@NotNull Node> stack = new LinkedList<>();

    @Override
    protected @NotNull Deque<@NotNull Node> handleTextNode(@NotNull TextNode textNode) {
        if (isNeedToHandleAfterTrimText(textNode)) {
            stack.push(textNode);
        }
        return stack;
    }

    @Override
    protected @NotNull Deque<@NotNull Node> handleElement(@NotNull Element element) {
        if (this.isMediaElement(element)) {
            stack.push(element);

            return stack;
        }

        if (this.isLinkElement(element)) {
            this.handleLinkElement(element);

            return stack;
        }

        this.markBlockIfNeeded(element);

        var childrenNodes = listNeedToHandleChild(element);

        if (element.children().size() == 0) {
            if (childrenNodes.size() == 1) {
                this.handleTextNode((TextNode) childrenNodes.get(0));
            }
        } else {
            reverseForeach(childrenNodes, this::handle);
        }

        this.markBlockIfNeeded(element);

        return stack;
    }

    private void handleLinkElement(@NotNull Element element) {
        var text = joinTogether(ExtractNodeTextHandler.instance().handle(element));

        var textNode = new TextNode(new Element(A_TAG).attr(HREF_ATTR, element.attr(HREF_ATTR)).html(text).toString());

        stack.push(textNode);
    }

    private void markBlockIfNeeded(@NotNull Element element) {
        if (OUTER_BLOCK_TAGS.contains(element.tagName())) {
            Node head = stack.peek();

            if (head instanceof TextNode || (head instanceof Element && !isBlockElement((Element) head))) {
                stack.push(new Element(INNER_BLOCK_TAG));
            }
        }
    }

    private boolean isMediaElement(@NotNull Element element) {
        return MEDIA_TAGS.contains(element.tagName());
    }

    private boolean isLinkElement(@NotNull Element element) {
        return A_TAG.equals(element.tagName());
    }

    private boolean isBlockElement(@NotNull Element element) {
        return ALL_BLOCK_TAGS.contains(element.tagName());
    }
}
