// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity;

/**
 * 表示带名字的消息体数据。
 *
 * @author 季聿阶
 * @since 2022-10-12
 */
public interface NamedEntity extends Entity {
    /**
     * 获取消息体数据的名字。
     *
     * @return 表示消息体数据的名字的 {@link String}。
     */
    String name();

    /**
     * 获取真正的消息体数据内容。
     *
     * @return 表示真正的消息体数据内容的 {@link Entity}。
     */
    Entity entity();

    /**
     * 判断消息体数据是否是文件类型。
     *
     * @return 表示消息体数据是否是文件类型的 {@code boolean}。
     */
    boolean isFile();

    /**
     * 判断消息体数据是否是文本类型。
     *
     * @return 表示消息体数据是否是文本类型的 {@code boolean}。
     */
    boolean isText();

    /**
     * 将消息体数据转化成文件类型的消息体。
     *
     * @return 表示转化后的文件类型消息体的 {@link FileEntity}。
     */
    FileEntity asFile();

    /**
     * 将消息体数据转化成文本类型的消息体。
     *
     * @return 表示转化后的文本类型消息体的 {@link TextEntity}。
     */
    TextEntity asText();
}
