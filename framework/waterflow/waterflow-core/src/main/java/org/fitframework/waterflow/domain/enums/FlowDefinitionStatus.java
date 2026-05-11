// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.enums;

import static org.fitframework.waterflow.ErrorCodes.ENUM_CONVERT_FAILED;

import lombok.Getter;
import org.fitframework.waterflow.exceptions.WaterflowParamException;

import java.util.Arrays;

/**
 * 流程定义对应状态
 *
 * @author 杨祥宇
 * @since 1.0
 */
@Getter
public enum FlowDefinitionStatus {
    /**
     * 流程定义处于激活状态
     */
    ACTIVE("active"),

    /**
     * 流程定义处于非激活状态
     */
    INACTIVE("inactive");

    private final String code;

    FlowDefinitionStatus(String code) {
        this.code = code;
    }

    /**
     * 根据状态码获取definition的状态
     *
     * @param code 状态码
     * @return 状态
     */
    public static FlowDefinitionStatus getFlowDefinitionStatus(String code) {
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new WaterflowParamException(ENUM_CONVERT_FAILED, "FlowDefinitionStatus", code));
    }
}
