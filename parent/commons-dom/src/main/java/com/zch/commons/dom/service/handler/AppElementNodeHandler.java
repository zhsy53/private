package com.zch.commons.dom.service.handler;

import com.zch.commons.dom.domain.AppElement;
import com.zch.commons.dom.domain.Type;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static com.zch.commons.dom.domain.ElementConstant.SRC_ATTR;
import static com.zch.commons.dom.util.BaseUtils.getLast;
import static java.util.Optional.ofNullable;

@Getter
@NoArgsConstructor(staticName = "instance")
public class AppElementNodeHandler extends AbstractNodeHandler<Void> {
    @NotNull
    private final List<@NotNull AppElement> list = new ArrayList<>();
    private boolean newBlock = false;

    @Override
    protected Void handleTextNode(@NotNull TextNode textNode) {
        var text = textNode.text();

        if (newBlock) {
            newBlock = false;
            list.add(AppElement.text(text));
            return null;
        }

        newBlock = false;

        var last = getLast(list);
        if (last == null || last.getType() != Type.TEXT) {
            list.add(AppElement.text(text));
            return null;
        }

        list.remove(last);
        list.add(AppElement.text(last.getValue() + text));

        return null;
    }

    @Override
    protected Void handleElement(@NotNull Element element) {
        newBlock = true;
        ofNullable(Type.from(element.tagName())).ifPresent(type -> list.add(AppElement.of(type, element.attr(SRC_ATTR))));
        return null;
    }
}
