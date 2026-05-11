// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.entity.support;

import static org.fitframework.util.ObjectUtils.getIfNull;

import org.fitframework.http.HttpMessage;
import org.fitframework.http.entity.MultiValueEntity;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.inspection.Nonnull;
import org.fitframework.model.MultiValueMap;
import org.fitframework.model.support.DefaultMultiValueMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 表示 {@link MultiValueEntity} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-08-05
 */
public class DefaultMultiValueEntity extends AbstractEntity implements MultiValueEntity {
    private final MultiValueMap<String, String> values;

    /**
     * 创建多值格式的消息体数据的实现对象、
     *
     * @param httpMessage 表示消息体数据所属的 Http 消息的 {@link HttpMessage}。
     * @param values 表示指定的键值对映射的 {@link MultiValueMap}{@code <}{@link String}{@code , }{@link String}{@code >}。
     */
    public DefaultMultiValueEntity(HttpMessage httpMessage, MultiValueMap<String, String> values) {
        super(httpMessage);
        this.values = getIfNull(values, DefaultMultiValueMap::new);
    }

    @Override
    public List<String> keys() {
        return Collections.unmodifiableList(new ArrayList<>(this.values.keySet()));
    }

    @Override
    public Optional<String> first(String key) {
        return Optional.ofNullable(this.values.getFirst(key));
    }

    @Override
    public List<String> all(String key) {
        return Optional.ofNullable(this.values.get(key)).orElseGet(Collections::emptyList);
    }

    @Override
    public int size() {
        return this.values.size();
    }

    @Override
    public String toString() {
        List<String> keyValues = new ArrayList<>();
        for (String key : this.keys()) {
            List<String> matchedValues = this.all(key);
            keyValues.add(matchedValues.stream().map(value -> key + '=' + value).collect(Collectors.joining("&")));
        }
        return String.join("&", keyValues);
    }

    @Nonnull
    @Override
    public MimeType resolvedMimeType() {
        return MimeType.APPLICATION_X_WWW_FORM_URLENCODED;
    }
}
