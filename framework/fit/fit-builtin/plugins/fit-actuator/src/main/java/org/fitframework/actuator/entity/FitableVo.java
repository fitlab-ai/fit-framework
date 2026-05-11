// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.actuator.entity;

import lombok.Data;

import java.util.Set;

/**
 * 表示服务实现信息。
 *
 * @author 季聿阶
 * @since 2024-07-05
 */
@Data
public class FitableVo {
    private String id;
    private String version;
    private Set<String> aliases;
    private Set<String> tags;
    private String degradation;
}
