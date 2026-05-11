// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util;

/**
 * 当对象被销毁时被回调的方法。
 *
 * @author 梁济时
 * @since 2021-02-25
 */
@FunctionalInterface
public interface DisposedCallback {
    /**
     * 当对象被销毁时被通知。
     *
     * @param disposable 表示被销毁的对象的 {@link Disposable}。
     */
    void onDisposed(Disposable disposable);
}
