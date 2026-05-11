// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.io.virtualization;

/**
 * 为虚拟文件系统提供元素。
 *
 * @author 梁济时
 * @since 2022-08-01
 */
public interface VirtualFileSystemElement {
    /**
     * 获取文件系统元素的名称。
     *
     * @return 表示文件系统元素名称的 {@link String}。
     */
    String name();

    /**
     * 获取文件系统元素的路径。
     *
     * @return 表示文件系统元素路径的 {@link String}。
     */
    String path();
}
