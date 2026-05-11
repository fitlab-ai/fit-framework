// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.image;

import org.fitframework.resource.web.Media;

import java.util.List;

/**
 * 表示大模型图像生成服务。
 *
 * @author 何嘉斌
 * @since 2024-12-17
 */
public interface ImageModel {
    /**
     * 调用图像生成模型生成结果。
     *
     * @param prompt 表示提示词的 {@link String}。
     * @param chatOption 表示图像生成模型参数的 {@link ImageOption}。
     * @return 表示图像生成模型生成结果的 {@link List}{@code <}{@link Media}{@code >}。
     */
    List<Media> generate(String prompt, ImageOption chatOption);
}