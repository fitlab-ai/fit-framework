// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.actuator.entity;

import lombok.Data;

/**
 * 表示插件信息。
 *
 * @author 季聿阶
 * @since 2024-07-05
 */
@Data
public class PluginVo {
    private String group;
    private String name;
    private String version;
    private String category;
    private int level;
}
