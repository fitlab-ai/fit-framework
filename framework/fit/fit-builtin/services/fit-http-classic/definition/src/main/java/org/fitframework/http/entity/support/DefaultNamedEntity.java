// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.FileEntity;
import org.fitframework.http.entity.NamedEntity;
import org.fitframework.http.entity.TextEntity;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.io.IOException;

/**
 * 表示 {@link NamedEntity} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-10-12
 */
public class DefaultNamedEntity extends AbstractEntity implements NamedEntity {
    private final String name;
    private final Entity entity;

    /**
     * 创建带名字的消息体数据。
     *
     * @param httpMessage 表示消息体数据所属的 Http 消息的 {@link HttpMessage}。
     * @param name 表示消息体数据的名字的 {@link String}。
     * @param entity 表示消息体数据的 {@link Entity}。
     */
    public DefaultNamedEntity(HttpMessage httpMessage, String name, Entity entity) {
        super(httpMessage);
        this.name = ObjectUtils.nullIf(name, StringUtils.EMPTY);
        this.entity = notNull(entity, "The entity cannot be null.");
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Entity entity() {
        return this.entity;
    }

    @Override
    public boolean isFile() {
        return this.entity instanceof FileEntity;
    }

    @Override
    public boolean isText() {
        return this.entity instanceof TextEntity;
    }

    @Override
    public FileEntity asFile() {
        return ObjectUtils.as(this.entity, FileEntity.class);
    }

    @Override
    public TextEntity asText() {
        return ObjectUtils.as(this.entity, TextEntity.class);
    }

    @Nonnull
    @Override
    public MimeType resolvedMimeType() {
        return this.entity.resolvedMimeType();
    }

    @Override
    public void close() throws IOException {
        super.close();
        this.entity.close();
    }
}
