// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.support.DefaultObjectEntity;

/**
 * 表示消息体内对象类型的数据。
 *
 * @param <T> 表示消息体内数据类型的 {@link T}。
 * @author 季聿阶
 * @since 2022-09-01
 */
public interface ObjectEntity<T> extends Entity {
    /**
     * 获取实体转换成指定类型后的对象。
     *
     * @return 表示实体转换成指定类型后的对象的 {@link T}。
     * @throws EntityReadException 当读取过程出现异常时。
     */
    T object();

    /**
     * 通过指定的对象，创建对象消息体数据。
     *
     * @param httpMessage 表示消息体数据所属的 Http 消息的 {@link HttpMessage}。
     * @param obj 表示指定对象的 {@link Object}。
     * @return 表示创建出来的对象消息体数据的 {@link ObjectEntity}{@code <?>}。
     */
    static ObjectEntity<?> create(HttpMessage httpMessage, Object obj) {
        return new DefaultObjectEntity<>(httpMessage, obj);
    }
}
