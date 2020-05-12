package com.zch.commons.dom.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

public interface ElementConstant {
    @NotBlank String A_TAG = "a";
    @NotBlank String IMG_TAG = "img";
    @NotBlank String VIDEO_TAG = "video";

    /**
     * (内部)指定换行标签
     */
    @NotBlank String INNER_BLOCK_TAG = "br";

    /**
     * 媒体资源标签
     */
    @NotEmpty Set<@NotBlank String> MEDIA_TAGS = Set.of(IMG_TAG, VIDEO_TAG);

    /**
     * (外部)换行标签
     */
    @NotEmpty Set<@NotBlank String> OUTER_BLOCK_TAGS = Set.of("div", "p", INNER_BLOCK_TAG);

    /**
     * 解析后的换行标签
     *
     * @see ElementConstant#MEDIA_TAGS
     * @see ElementConstant#INNER_BLOCK_TAG
     */
    @NotEmpty Set<@NotBlank String> ALL_BLOCK_TAGS = Set.of(IMG_TAG, VIDEO_TAG, INNER_BLOCK_TAG);

    /**
     * src属性
     */
    @NotBlank String SRC_ATTR = "src";

    /**
     * href
     */
    @NotBlank String HREF_ATTR = "href";

    /**
     * 视频标签附加属性,用以解析src
     */
    @NotBlank String VIDEO_ADDITIONAL_ATTR = "ref";
}
