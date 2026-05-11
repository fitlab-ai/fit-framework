// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.plugin.maven.task;

import org.apache.maven.plugin.MojoExecutionException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 为构建过程提供任务定义。
 *
 * @author 梁济时
 * @since 2020-11-20
 */
@FunctionalInterface
public interface BuildTask {
    /**
     * 执行任务。
     *
     * @throws MojoExecutionException 任务执行过程发生异常。
     */
    void run() throws MojoExecutionException;

    /**
     * 获取空的任务。
     *
     * @return 表示空任务的 {@link BuildTask}。
     */
    static BuildTask empty() {
        return () -> {};
    }

    /**
     * 将多个任务组合成为一个任务。
     *
     * @param tasks 表示待组合的任务的 {@link BuildTask}{@code []}。
     * @return 表示组合后的任务的 {@link BuildTask}。
     */
    static BuildTask combine(BuildTask... tasks) {
        if (tasks == null) {
            return empty();
        }
        List<BuildTask> actualTasks = Arrays.stream(tasks).filter(Objects::nonNull).collect(Collectors.toList());
        return () -> {
            for (BuildTask task : actualTasks) {
                task.run();
            }
        };
    }
}
