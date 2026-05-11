// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.actuator.entity;

import lombok.Data;

/**
 * 表示连接端点的信息。
 *
 * @author 季聿阶
 * @since 2024-07-05
 */
@Data
public class EndpointVo {
    private String protocol;
    private int code;
    private int port;
}
