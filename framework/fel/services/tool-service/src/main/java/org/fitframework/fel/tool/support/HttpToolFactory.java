// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.fel.tool.Tool;
import org.fitframework.fel.tool.ToolFactory;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.value.ValueFetcher;

/**
 * 表示创建 {@link HttpTool} 的工厂。
 *
 * @author 何天放
 * @since 2024-06-15
 */
public class HttpToolFactory implements ToolFactory {
    private final HttpClassicClientFactory factory;
    private final ObjectSerializer serializer;
    private final ValueFetcher valueFetcher;

    public HttpToolFactory(HttpClassicClientFactory factory, ObjectSerializer serializer, ValueFetcher valueFetcher) {
        this.factory = notNull(factory, "The factory cannot be null.");
        this.serializer = notNull(serializer, "The serializer cannot be null.");
        this.valueFetcher = notNull(valueFetcher, "The valueFetcher cannot be null.");
    }

    @Override
    public String type() {
        return HttpTool.TYPE;
    }

    @Override
    public Tool create(Tool.Info itemInfo, Tool.Metadata metadata) {
        return new HttpTool(this.factory, this.serializer, this.valueFetcher, itemInfo, metadata);
    }
}
