package com.zch.commons.dom.service;

import com.zch.commons.dom.service.handler.NodeParseHandler;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.nodes.Node;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.zch.commons.dom.domain.ElementConstant.INNER_BLOCK_TAG;

@NoArgsConstructor(staticName = "instance")
class SimpleNodeParser implements NodeParser {
    @Override
    public @NotNull List<@NotNull Node> parse(@NotBlank String s) {
        Element body = Jsoup.parse(s).outputSettings(new OutputSettings().escapeMode(EscapeMode.xhtml)).body();

        var stack = NodeParseHandler.instance().handle(body);

        //can ignore when used for parse node list
        this.fixHead(stack);

        return new ArrayList<>(stack);
    }

    private void fixHead(@NotNull Deque<@NotNull Node> stack) {
        Node head = stack.peek();
        if (head != null && head.nodeName().equals(INNER_BLOCK_TAG)) {
            stack.pop();
        }
    }
}
