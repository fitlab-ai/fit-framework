// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity;

import java.util.List;

/**
 * 表示分块的消息体数据。
 *
 * @author 季聿阶
 * @since 2022-10-12
 */
public interface PartitionedEntity extends Entity {
    /**
     * 获取分块带名字的消息体数据的列表。
     *
     * @return 表示分块带名字的消息体数据的列表的 {@link List}{@code <}{@link NamedEntity}{@code >}。
     */
    List<NamedEntity> entities();
}
