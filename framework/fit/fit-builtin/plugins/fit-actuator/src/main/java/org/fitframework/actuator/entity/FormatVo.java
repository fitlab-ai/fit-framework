// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.actuator.entity;

import lombok.Data;

/**
 * 表示序列化协议的信息。
 *
 * @author 季聿阶
 * @since 2024-07-05
 */
@Data
public class FormatVo {
    private String name;
    private int code;
}
