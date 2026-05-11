// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.template;

import java.util.List;
import java.util.Map;

/**
 * 批量字符串模板接口定义。
 *
 * @author 何嘉斌
 * @since 2024-05-13
 */
public interface BulkStringTemplate extends GenericTemplate<List<Map<String, String>>, String> {}