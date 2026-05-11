// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.scanner.resolver;

import org.fitframework.http.annotation.RequestBody;
import org.fitframework.http.client.proxy.scanner.ParamResolver;
import org.fitframework.http.client.proxy.support.setter.DestinationSetterInfo;
import org.fitframework.http.client.proxy.support.setter.ObjectEntitySetter;

/**
 * Resolves the {@link RequestBody} annotation into a destination setter information object.
 * This class implements the {@link ParamResolver} interface and is responsible for parsing
 * the {@link RequestBody} annotation and converting it into a {@link DestinationSetterInfo}
 * object that can be used to set the request body on HTTP request objects.
 *
 * @author 王攀博
 * @since 2025-02-10
 */
public class RequestBodyResolver implements ParamResolver<RequestBody> {
    @Override
    public DestinationSetterInfo resolve(RequestBody annotation, String jsonPath) {
        return new DestinationSetterInfo(new ObjectEntitySetter(annotation.key()), jsonPath);
    }
}