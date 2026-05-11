// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.source;

import java.util.Map;
import java.util.function.Function;

/**
 * 表示 json 文件元数据萃取器接口。
 *
 * @author 易文渊
 * @since 2024-08-10
 */
public interface JsonMetadataExtractor extends Function<Map<String, Object>, Map<String, Object>> {}