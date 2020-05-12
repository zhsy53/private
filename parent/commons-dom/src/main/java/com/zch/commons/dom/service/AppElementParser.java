package com.zch.commons.dom.service;

import com.zch.commons.dom.domain.AppElement;
import com.zch.commons.dom.domain.AppVideo;
import com.zch.commons.dom.service.handler.AppElementNodeHandler;
import org.jsoup.nodes.Element;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.zch.commons.dom.domain.ElementConstant.*;
import static java.util.stream.Collectors.toList;

public abstract class AppElementParser {
    private static final NodeParser NODE_PARSER = SimpleNodeParser.instance();

    @NotNull
    public static List<@NotNull AppElement> parse(@NotBlank String s) {
        var handler = AppElementNodeHandler.instance();

        NODE_PARSER.parse(s).forEach(handler::handle);

        return handler.getList();
    }

    @NotNull
    public static List<@NotBlank String> parseImgList(@NotBlank String s) {
        return NODE_PARSER.parse(s)
                .stream()
                .filter(node -> node instanceof Element)
                .map(node -> (Element) node)
                .filter(element -> element.tagName().equals(IMG_TAG))
                .map(element -> element.attr(SRC_ATTR))
                .collect(toList());
    }

    @NotNull
    public static List<@NotNull AppVideo> parseVideoList(@NotBlank String s) {
        return NODE_PARSER.parse(s)
                .stream()
                .filter(node -> node instanceof Element)
                .map(node -> (Element) node)
                .filter(element -> element.tagName().equals(VIDEO_TAG))
                .map(element -> AppVideo.of(element.attr(SRC_ATTR), element.attr(VIDEO_ADDITIONAL_ATTR)))
                .collect(toList());
    }
}
