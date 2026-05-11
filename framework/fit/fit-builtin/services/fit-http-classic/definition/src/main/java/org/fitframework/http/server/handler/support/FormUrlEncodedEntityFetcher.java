// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler.support;

import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.MultiValueEntity;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.handler.SourceFetcher;
import org.fitframework.http.server.handler.exception.RequestParamFetchException;
import org.fitframework.util.ObjectUtils;

/**
 * 表示从 {@link MultiValueEntity} 中获取值的 {@link SourceFetcher}。
 *
 * @author 季聿阶
 * @since 2022-08-28
 */
public class FormUrlEncodedEntityFetcher extends EntityFetcher {
    private final String key;

    /**
     * 用参数的键来实例化 {@link FormUrlEncodedEntityFetcher}。
     *
     * @param key 表示参数的键的 {@link String}。
     */
    public FormUrlEncodedEntityFetcher(String key) {
        this.key = notBlank(key, () -> new RequestParamFetchException("The key cannot be blank."));
    }

    /**
     * 用参数元数据来实例化 {@link FormUrlEncodedEntityFetcher}。
     *
     * @param paramValue 表示参数元数据的 {@link ParamValue}。
     */
    public FormUrlEncodedEntityFetcher(ParamValue paramValue) {
        this.key = notBlank(paramValue.name(), () -> new RequestParamFetchException("The key cannot be blank."));
    }

    @Override
    public boolean isArrayAble() {
        return true;
    }

    @Override
    protected Class<? extends Entity> entityType() {
        return MultiValueEntity.class;
    }

    @Override
    protected Object getFromRequest(HttpClassicServerRequest request, Entity entity) {
        MultiValueEntity multiValueEntity = ObjectUtils.cast(entity);
        return multiValueEntity.all(this.key);
    }
}
