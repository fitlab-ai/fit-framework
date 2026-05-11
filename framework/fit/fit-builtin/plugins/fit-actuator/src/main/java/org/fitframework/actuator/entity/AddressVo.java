// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.actuator.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 表示地址信息
 *
 * @author 季聿阶
 * @since 2024-07-05
 */
@Data
public class AddressVo {
    private String workerId;
    private String host;
    private String environment;
    private List<FormatVo> formats;
    private List<EndpointVo> endpoints;
    private Map<String, String> extensions;
}
