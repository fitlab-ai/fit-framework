// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.scanner.resolver;

import org.fitframework.http.annotation.PathVariable;
import org.fitframework.http.client.proxy.scanner.ParamResolver;
import org.fitframework.http.client.proxy.support.setter.DestinationSetterInfo;
import org.fitframework.http.client.proxy.support.setter.PathVariableDestinationSetter;

/**
 * Resolves the {@link PathVariable} annotation into a destination setter information object.
 * This class implements the {@link ParamResolver} interface and is responsible for parsing
 * the {@link PathVariable} annotation and converting it into a {@link DestinationSetterInfo}
 * object that can be used to set path variables on HTTP request objects.
 *
 * @author 王攀博
 * @since 2025-02-10
 */
public class PathVariableResolver implements ParamResolver<PathVariable> {
    @Override
    public DestinationSetterInfo resolve(PathVariable annotation, String jsonPath) {
        return new DestinationSetterInfo(new PathVariableDestinationSetter(annotation.name()), jsonPath);
    }
}