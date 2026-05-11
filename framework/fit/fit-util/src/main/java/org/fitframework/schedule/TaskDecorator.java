// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.schedule;

/**
 * 表示任务的装饰器。
 *
 * @author 季聿阶
 * @since 2022-11-16
 */
@FunctionalInterface
public interface TaskDecorator {
    /**
     * 对一个指定任务进行装饰。
     *
     * @param task 表示指定任务的 {@link Task}。
     * @return 表示装饰后的任务的 {@link Task}。
     */
    Task decorate(Task task);
}
