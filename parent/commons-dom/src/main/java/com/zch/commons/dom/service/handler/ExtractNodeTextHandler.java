package com.zch.commons.dom.service.handler;

import com.zch.commons.dom.util.DomUtils;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.zch.commons.dom.util.DomUtils.isNeedToHandleAfterTrimText;

@NoArgsConstructor(staticName = "instance")
public class ExtractNodeTextHandler extends AbstractNodeHandler<@NotNull List<@NotBlank String>> {
    @NotNull
    private final @NotNull List<@NotBlank String> text = new ArrayList<>();

    @Override
    protected @NotNull List<@NotBlank String> handleTextNode(@NotNull TextNode textNode) {
        if (isNeedToHandleAfterTrimText(textNode)) {
            text.add(textNode.text());
        }
        return text;
    }

    @Override
    protected @NotNull List<@NotBlank String> handleElement(@NotNull Element element) {
        DomUtils.listNeedToHandleChild(element).forEach(this::handle);
        return text;
    }
}
