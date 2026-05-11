// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.enums;

/**
 * 节点事件处理类型
 * 分为前置处理和后置处理，PRE_PROCESS类型为发送人工任务通知，PROCESS类型为节点本身的任务处理
 *
 * @author 高诗意
 * @since 1.0
 */
public enum ProcessType {
    /**
     * 前置处理
     */
    PRE_PROCESS,

    /**
     * 后置处理
     */
    PROCESS
}
