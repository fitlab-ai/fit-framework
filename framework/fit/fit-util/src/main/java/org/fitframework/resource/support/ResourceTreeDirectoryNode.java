// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.resource.support;

import org.fitframework.inspection.Nonnull;
import org.fitframework.inspection.Nullable;
import org.fitframework.protocol.jar.JarEntryLocation;
import org.fitframework.resource.ResourceTree;
import org.fitframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 为资源树中的目录节点提供实现。
 *
 * @author 梁济时
 * @since 2023-02-10
 */
final class ResourceTreeDirectoryNode extends AbstractResourceNode implements ResourceTree.DirectoryNode {
    private final ResourceNodeCollection collection;
    private final String name;
    private final ResourceNodeCollection children;

    private String path;

    ResourceTreeDirectoryNode(ResourceNodeCollection collection, String name) {
        this.collection = collection;
        this.name = name;
        this.children = new ResourceNodeCollection(this);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String path() {
        if (this.path == null) {
            List<String> parts = new LinkedList<>();
            ResourceTree.DirectoryNode directory = this;
            while (directory != null) {
                parts.add(0, directory.name());
                directory = directory.parent();
            }
            this.path = StringUtils.join(JarEntryLocation.ENTRY_PATH_SEPARATOR, parts);
        }
        return this.path;
    }

    @Nonnull
    @Override
    public ResourceTree tree() {
        return this.collection.tree();
    }

    @Nullable
    @Override
    public ResourceTree.DirectoryNode parent() {
        return this.collection.directory();
    }

    @Override
    public ResourceNodeCollection children() {
        return this.children;
    }

    @Override
    public void traverse(Consumer<ResourceTree.FileNode> consumer) {
        this.children.traverse(null, consumer);
    }

    @Override
    public void traverse(Predicate<ResourceTree.FileNode> filter, Consumer<ResourceTree.FileNode> consumer) {
        this.children.traverse(filter, consumer);
    }
}
