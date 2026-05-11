// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.document;

import org.fitframework.fel.core.pattern.Synthesizer;

import java.util.List;

/**
 * 表示文档检索结果的合成器。
 *
 * @author 易文渊
 * @since 2024-08-12
 */
public interface DocumentSynthesizer extends Synthesizer<List<? extends Document>> {}