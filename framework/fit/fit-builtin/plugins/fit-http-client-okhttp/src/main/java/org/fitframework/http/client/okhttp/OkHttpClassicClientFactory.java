// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.okhttp;

import static org.fitframework.http.client.okhttp.OkHttpClientBuilderFactory.getOkHttpClientBuilder;
import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.getIfNull;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import org.fitframework.http.Serializers;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Order;
import org.fitframework.annotation.Value;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.value.ValueFetcher;
import okhttp3.OkHttpClient;

import java.util.Map;

/**
 * 表示 {@link HttpClassicClientFactory} 的 OkHttp 实现。
 *
 * @author 杭潇
 * @since 2024-04-08
 */
@Order(Order.HIGH)
@Component
public class OkHttpClassicClientFactory implements HttpClassicClientFactory {
    private final Serializers serializers;
    private final ValueFetcher valueFetcher;
    private final Cache<Config, OkHttpClient> okHttpClientCache;

    public OkHttpClassicClientFactory(Map<String, ObjectSerializer> serializers, ValueFetcher valueFetcher,
            @Value("${okhttp.config-client.cache.max-size}") long cacheSize) {
        this.serializers = Serializers.create(serializers);
        this.valueFetcher = notNull(valueFetcher, "The value fetcher cannot be null.");
        this.okHttpClientCache = Caffeine.newBuilder().maximumSize(cacheSize).build();
    }

    @Override
    public HttpClassicClient create() {
        return this.create(Config.builder().build());
    }

    @Override
    public HttpClassicClient create(Config config) {
        Config actualConfig = getIfNull(config, () -> HttpClassicClientFactory.Config.builder().build());
        OkHttpClient okHttpClient =
                this.okHttpClientCache.get(actualConfig, tempConfig -> getOkHttpClientBuilder(tempConfig).build());
        return new OkHttpClassicClient(this.serializers, this.valueFetcher, okHttpClient);
    }
}
