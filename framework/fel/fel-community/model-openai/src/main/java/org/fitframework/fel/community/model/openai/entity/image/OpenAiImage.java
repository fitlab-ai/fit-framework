// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.community.model.openai.entity.image;

import org.fitframework.annotation.Property;
import org.fitframework.exception.FitException;
import org.fitframework.resource.web.Media;
import org.fitframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 表示 OpenAi 格式的图片。
 *
 * @author 何嘉斌
 * @since 2024-12-17
 */
public class OpenAiImage {
    @Property(name = "b64_json")
    private String b64Json;
    private String url;

    /**
     * 获取图片媒体资源。
     *
     * @return 表示图片媒体资源的 {@link Media}。
     */
    public Media media() {
        try {
            return StringUtils.isNotBlank(b64Json) ? new Media("image/jpeg", b64Json) : new Media(new URL(url));
        } catch (MalformedURLException ex) {
            throw new FitException(ex);
        }
    }
}