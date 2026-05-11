// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.resource.support;

import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.as;

import org.fitframework.inspection.Nullable;
import org.fitframework.protocol.jar.JarEntryLocation;
import org.fitframework.resource.ResourceTree;
import org.fitframework.util.StringUtils;
import org.fitframework.util.wildcard.Pattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 为资源树提供基类。
 *
 * @author 梁济时
 * @since 2023-02-10
 */
abstract class AbstractResourceTree implements ResourceTree {
    private final ResourceNodeCollection roots;

    AbstractResourceTree() {
        this.roots = new ResourceNodeCollection(this);
    }

    @Override
    public final ResourceNodeCollection roots() {
        return this.roots;
    }

    @Override
    public void traverse(Consumer<FileNode> consumer) {
        this.roots.traverse(null, consumer);
    }

    @Override
    public void traverse(Predicate<FileNode> filter, Consumer<FileNode> consumer) {
        this.roots.traverse(filter, consumer);
    }

    @Nullable
    @Override
    public Node nodeAt(String path) {
        notNull(path, "The path to retrieve node cannot be null.");
        String[] parts = StringUtils.split(path, JarEntryLocation.ENTRY_PATH_SEPARATOR);
        NodeCollection collection = this.roots();
        int index = parts.length - 1;
        for (int i = 0; i < index && collection != null; i++) {
            Node node = collection.get(parts[i]);
            DirectoryNode directory = as(node, DirectoryNode.class);
            if (directory == null) {
                collection = null;
            } else {
                collection = directory.children();
            }
        }
        if (collection == null) {
            return null;
        } else {
            return collection.get(parts[index]);
        }
    }

    @Override
    public List<FileNode> match(Pattern<String> pattern) {
        notNull(pattern, "The pattern to match file nodes cannot be null.");
        List<Node> nodes = pattern.match(listOf(this.roots()), AbstractResourceTree::children, Node::name);
        return nodes.stream()
                .filter(FileNode.class::isInstance)
                .map(FileNode.class::cast)
                .collect(Collectors.toList());
    }

    private static List<Node> listOf(NodeCollection nodes) {
        List<Node> list = new ArrayList<>(nodes.count());
        for (Node node : nodes) {
            list.add(node);
        }
        return list;
    }

    private static List<Node> children(Node node) {
        DirectoryNode directory = as(node, DirectoryNode.class);
        if (directory == null) {
            return Collections.emptyList();
        } else {
            return listOf(directory.children());
        }
    }
}
