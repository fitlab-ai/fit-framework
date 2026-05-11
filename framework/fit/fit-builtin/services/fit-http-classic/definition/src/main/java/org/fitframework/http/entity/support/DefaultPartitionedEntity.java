// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import static org.fitframework.util.ObjectUtils.getIfNull;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.NamedEntity;
import org.fitframework.http.entity.PartitionedEntity;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.inspection.Nonnull;
import org.fitframework.util.UuidUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 表示 {@link PartitionedEntity} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-10-12
 */
public class DefaultPartitionedEntity extends AbstractEntity implements PartitionedEntity {
    private static final String BOUNDARY_PREFIX = "FitFormBoundary";
    private final List<NamedEntity> namedEntities;
    private final String boundary;

    /**
     * 创建分块的消息体数据对象。
     *
     * @param httpMessage 表示消息体数据所属的 Http 消息的 {@link HttpMessage}。
     * @param namedEntities 表示带名字的消息体数据列表的 {@link List}{@code <}{@link NamedEntity}{@code >}。
     */
    public DefaultPartitionedEntity(HttpMessage httpMessage, List<NamedEntity> namedEntities) {
        super(httpMessage);
        this.namedEntities = getIfNull(namedEntities, Collections::emptyList);
        this.boundary = this.generateBoundary();
    }

    /**
     * 生成随机的 boundary 分隔符。
     * <p>格式：FitFormBoundary-{32位随机十六进制字符}</p>
     * <p>示例：FitFormBoundary-1a2b3c4d5e6f7g8h9i0j1k2l3m4n5o6p</p>
     * <p>注意：实际写入消息体时会自动添加 {@code --} 前缀。</p>
     *
     * @return 表示生成的 boundary 分隔符的 {@link String}。
     */
    private String generateBoundary() {
        return BOUNDARY_PREFIX + "-" + UuidUtils.randomUuidString().replace("-", "");
    }

    @Override
    public List<NamedEntity> entities() {
        return Collections.unmodifiableList(this.namedEntities);
    }

    @Nonnull
    @Override
    public MimeType resolvedMimeType() {
        return MimeType.MULTIPART_FORM_DATA;
    }

    @Nonnull
    @Override
    public Map<String, String> resolvedParameters() {
        return Map.of("boundary", this.boundary);
    }

    @Override
    public void close() throws IOException {
        super.close();
        for (NamedEntity entity : this.namedEntities) {
            if (entity != null) {
                entity.close();
            }
        }
    }
}
