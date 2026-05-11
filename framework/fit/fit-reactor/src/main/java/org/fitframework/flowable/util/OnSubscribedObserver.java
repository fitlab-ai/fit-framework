// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.flowable.util;

/**
 * 表示订阅关系发生的观察者。
 *
 * @author 何天放
 * @since 2024-05-22
 */
public interface OnSubscribedObserver {
    /**
     * 通知订阅关系发生。
     */
    void notifyOnSubscribed();
}
