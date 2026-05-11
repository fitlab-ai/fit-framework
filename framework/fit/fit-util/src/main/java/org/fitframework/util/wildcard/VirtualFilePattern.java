// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard;

import org.fitframework.io.virtualization.VirtualDirectory;
import org.fitframework.io.virtualization.VirtualFile;

import java.util.List;

/**
 * 为虚拟文件提供匹配模式。
 *
 * @author 梁济时
 * @since 2022-08-04
 */
public interface VirtualFilePattern extends Pattern<String> {
    /**
     * 在目录中查找符合预期的文件。
     *
     * @param parent 表示父目录的 {@link VirtualDirectory}。父目录不计入匹配范围。
     * @return 表示匹配到的子目录的列表的 {@link List}{@code <}{@link VirtualFile}{@code >}。
     */
    List<VirtualFile> match(VirtualDirectory parent);
}
