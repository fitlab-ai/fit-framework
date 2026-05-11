// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.enums;

import static org.fitframework.waterflow.ErrorCodes.ENUM_CONVERT_FAILED;

import org.fitframework.waterflow.exceptions.WaterflowParamException;

import java.util.Arrays;

/**
 * 流程实例状态枚举
 *
 * @author 刘海洋
 * @since 1.0
 */
public enum FlowTraceStatus {
    /**
     * 未执行
     */
    READY,

    /**
     * 执行中
     */
    RUNNING,

    /**
     * 执行完成
     */
    ARCHIVED,

    /**
     * 执行失败
     */
    ERROR,

    /**
     * 部分失败
     */
    PARTIAL_ERROR,

    /**
     * 已终止
     */
    TERMINATE;

    /**
     * 根据字符串获取FlowTraceStatus
     *
     * @param status status
     * @return FlowTraceStatus
     */
    public static FlowTraceStatus getFlowTraceStatus(String status) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new WaterflowParamException(ENUM_CONVERT_FAILED, "FlowTraceStatus", status));
    }
}
