// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.emitters;

/**
 * 完成能力的接口定义
 *
 * @author 宋永坦
 * @since 1.0
 */
public interface Completable {
    /**
     * 完成事件的通知方法
     */
    void complete();
}
