// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.util.wildcard.support;

import org.fitframework.io.virtualization.VirtualDirectory;
import org.fitframework.io.virtualization.VirtualFile;
import org.fitframework.io.virtualization.VirtualFileSystemElement;
import org.fitframework.util.wildcard.SymbolMatcher;
import org.fitframework.util.wildcard.SymbolSequence;
import org.fitframework.util.wildcard.VirtualFilePattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 为 {@link VirtualFilePattern} 提供默认实现。
 *
 * @author 梁济时
 * @since 2022-08-04
 */
public class DefaultVirtualFilePattern extends DefaultPattern<String> implements VirtualFilePattern {
    /**
     * 使用模式的长度初始化 {@link DefaultPattern} 类的新实例。
     *
     * @param pattern 表示匹配模式的符号序的 {@link SymbolSequence}。
     * @param multipleWildcard 表示用以匹配多个符号的通配符的 {@link String}。
     * @param nameMatcher 表示名称的匹配程序的 {@link SymbolMatcher}。
     */
    public DefaultVirtualFilePattern(SymbolSequence<String> pattern, String multipleWildcard,
            SymbolMatcher<String> nameMatcher) {
        super(pattern, new DefaultSymbolClassifier<>(null, multipleWildcard), nameMatcher);
    }

    @Override
    public List<VirtualFile> match(VirtualDirectory parent) {
        return this.match(children(parent), DefaultVirtualFilePattern::children, VirtualFileSystemElement::name)
                .stream()
                .filter(VirtualFile.class::isInstance)
                .map(VirtualFile.class::cast)
                .collect(Collectors.toList());
    }

    private static List<VirtualFileSystemElement> children(VirtualFileSystemElement parent) {
        if (parent instanceof VirtualDirectory) {
            VirtualDirectory directory = (VirtualDirectory) parent;
            int childrenCount = directory.children().size() + directory.files().size();
            List<VirtualFileSystemElement> children = new ArrayList<>(childrenCount);
            children.addAll(directory.children());
            children.addAll(directory.files());
            return children;
        } else {
            return Collections.emptyList();
        }
    }
}
