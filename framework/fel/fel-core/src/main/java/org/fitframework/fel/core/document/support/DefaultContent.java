// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.document.support;

import static org.fitframework.util.ObjectUtils.getIfNull;
import static org.fitframework.util.StringUtils.blankIf;

import org.fitframework.fel.core.document.Content;
import org.fitframework.inspection.Nonnull;
import org.fitframework.resource.web.Media;
import org.fitframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * 表示消息内容的实体。
 *
 * @author 易文渊
 * @since 2024-06-17
 */
public final class DefaultContent implements Content {
    private final String text;
    private final List<Media> medias;

    /**
     * 使用文本消息和媒体资源创建 {@link Content} 的实例。
     *
     * @param text 表示文本消息的 {@link String}。
     * @param medias 表示媒体资源列表的 {@link List}{@code <}{@link Media}{@code >}。
     */
    public DefaultContent(String text, List<Media> medias) {
        this.text = blankIf(text, StringUtils.EMPTY);
        this.medias = getIfNull(medias, Collections::emptyList);
    }

    @Nonnull
    @Override
    public String text() {
        return this.text;
    }

    @Override
    public List<Media> medias() {
        return this.medias;
    }

    @Override
    public String toString() {
        return this.text;
    }
}