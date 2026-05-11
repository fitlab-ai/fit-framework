// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.enums;

import static org.fitframework.waterflow.ErrorCodes.ENUM_CONVERT_FAILED;

import lombok.Getter;
import org.fitframework.waterflow.exceptions.WaterflowParamException;

import java.util.Arrays;

/**
 * 并行节点的操作类型
 *
 * @author 高诗意
 * @since 1.0
 */
@Getter
public enum ParallelMode {
    /**
     * 所有满足条件节点都执行
     */
    ALL("all"),

    /**
     * 满足条件节点执行
     */
    EITHER("either");

    private final String code;

    ParallelMode(String code) {
        this.code = code;
    }

    /**
     * parseFrom
     *
     * @param code code
     * @return ParallelMode
     */
    public static ParallelMode parseFrom(String code) {
        return Arrays.stream(values())
                .filter(value -> value.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> new WaterflowParamException(ENUM_CONVERT_FAILED, "ParallelMode", code));
    }
}
