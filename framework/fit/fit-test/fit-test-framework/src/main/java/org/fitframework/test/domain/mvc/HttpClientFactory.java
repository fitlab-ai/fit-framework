// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.domain.mvc;

import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.http.client.okhttp.OkHttpClassicClientFactory;
import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;
import org.fitframework.value.fastjson.FastJsonValueHandler;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.MapBuilder;
import org.fitframework.value.ValueFetcher;

import java.util.Map;

/**
 * 为模拟的 MVC 所使用的 http 客户端提供工厂。
 *
 * @author 王攀博
 * @since 2024-04-09
 */
public class HttpClientFactory {
    // 测试框架的默认 Config - OkHttpClient 的缓存数量。
    private static final int DEFAULT_CLIENT_CACHE_MAX_SIZE = 1;

    /**
     * 为模拟的 MVC 创建客户端。
     *
     * @return 表示用于模拟测试的 {@link HttpClassicClient}。
     */
    public static HttpClassicClient create() {
        ObjectSerializer jsonSerializer = new JacksonObjectSerializer(null, null, null, true);
        Map<String, ObjectSerializer> serializers =
                MapBuilder.<String, ObjectSerializer>get().put("json", jsonSerializer).build();
        ValueFetcher valueFetcher = new FastJsonValueHandler();
        HttpClassicClientFactory jdkFactory =
                new OkHttpClassicClientFactory(serializers, valueFetcher, DEFAULT_CLIENT_CACHE_MAX_SIZE);
        return jdkFactory.create();
    }
}
