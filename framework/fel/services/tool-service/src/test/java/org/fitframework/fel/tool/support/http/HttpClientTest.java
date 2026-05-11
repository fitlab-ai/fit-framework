// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support.http;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.fel.tool.support.http.server.RuntimeForServer;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.http.client.HttpClassicClientResponse;
import org.fitframework.http.client.okhttp.OkHttpClassicClientFactory;
import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.PropertyValueApplier;
import org.fitframework.http.client.proxy.RequestBuilder;
import org.fitframework.http.client.proxy.emitter.DefaultHttpEmitter;
import org.fitframework.http.client.proxy.support.DefaultRequestBuilder;
import org.fitframework.http.client.proxy.support.applier.MultiDestinationsPropertyValueApplier;
import org.fitframework.http.client.proxy.support.applier.UniqueDestinationPropertyValueApplier;
import org.fitframework.http.client.proxy.support.setter.CookieDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.DestinationSetterInfo;
import org.fitframework.http.client.proxy.support.setter.FormUrlEncodedEntitySetter;
import org.fitframework.http.client.proxy.support.setter.HeaderDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.ObjectEntitySetter;
import org.fitframework.http.client.proxy.support.setter.PathVariableDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.QueryDestinationSetter;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.serialization.json.jackson.JacksonObjectSerializer;
import org.fitframework.value.fastjson.FastJsonValueHandler;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.ObjectUtils;
import org.fitframework.value.ValueFetcher;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试 Http 提供。
 *
 * @author 王攀博
 * @since 2024-06-15
 */
@DisplayName("测试 Http 构建规则")
public class HttpClientTest {
    private static RuntimeForServer runtime;
    private static int port;

    private RequestBuilder requestBuilder;
    private String protocol;
    private String domain;
    private HttpRequestMethod method;
    private String pathPattern;
    private HttpClassicClient client;
    private String[] keys;
    private Object[] values;
    private Map<String, Object> weather;
    private Map<String, Object> formBody;
    private Authorization authorization;

    @BeforeAll
    static void setUpAll() {
        HttpClientTest.runtime = new RuntimeForServer(HttpClientTest.class);
        HttpClientTest.port = getLocalAvailablePort();
        HttpClientTest.runtime.start(HttpClientTest.port);
    }

    @AfterAll
    static void tearDownAll() {
        HttpClientTest.runtime.stop();
    }

    @BeforeEach
    void setup() {
        this.protocol = "http";
        this.domain = "localhost:" + HttpClientTest.port;
        this.pathPattern = "/test/travel/{type}";
        this.method = HttpRequestMethod.POST;
        this.client = this.createHttpClient();
        this.requestBuilder = new DefaultRequestBuilder().client(this.client)
                .protocol(this.protocol)
                .domain(this.domain)
                .pathPattern(this.pathPattern)
                .method(this.method);
        this.keys = new String[] {
                "type", "name", "age", "hobby", "phoneNumber", "transportations", "weather"
        };
        this.weather = MapBuilder.<String, Object>get().put("level", 1).put("weather", "sunny").build();
        this.values = new Object[] {
                "tourism", "test_name", 12, "running", "19988666666", Arrays.asList("train", "airplanes"), this.weather
        };
        this.formBody = new HashMap<>();
        this.formBody.put("strings", Arrays.asList("string1", "string2"));
        this.formBody.put("integer", 666);
        this.authorization = this.buildAuthorization();
    }

    private Authorization buildAuthorization() {
        Map<String, Object> auth = new HashMap<>();
        auth.put("type", "ApiKey");
        return Authorization.create(auth);
    }

    @Test
    @DisplayName("测试 Http 客户端构建规则")
    void shouldReturnValueWhenEmitGivenParam() {
        // given
        List<PropertyValueApplier> appliers = this.createAppliers();

        // when
        DefaultHttpEmitter emitter = new DefaultHttpEmitter(appliers,
                this.client,
                this.method,
                this.protocol,
                this.domain,
                this.pathPattern,
                this.authorization);

        HttpClassicClientResponse<?> response = emitter.emit(this.values);
        Map<String, Object> result = ObjectUtils.cast(response.objectEntity().get().object());

        // then
        for (int i = 0; i < this.keys.length; ++i) {
            assertThat(result.get(this.keys[i])).isEqualTo(this.values[i]);
        }
    }

    @Test
    @DisplayName("测试 Http formBody")
    void shouldReturnFormBodyInfoWhenEmitGivenParam() {
        // given
        List<DestinationSetterInfo> setterInfos = new ArrayList<>();
        this.formBody.entrySet()
                .forEach(element -> setterInfos.add(new DestinationSetterInfo(new FormUrlEncodedEntitySetter(element.getKey()),
                        element.getKey())));

        List<PropertyValueApplier> appliers = new ArrayList<>();
        PropertyValueApplier applier =
                new MultiDestinationsPropertyValueApplier(setterInfos, this.client.valueFetcher());
        appliers.add(applier);

        // when
        DefaultHttpEmitter emitter = new DefaultHttpEmitter(appliers,
                this.client,
                this.method,
                this.protocol,
                this.domain,
                "/test/form",
                this.authorization);

        HttpClassicClientResponse<?> response = emitter.emit(new Object[] {this.formBody});
        Map<String, Object> result = ObjectUtils.cast(response.objectEntity().get().object());

        // then
        for (Map.Entry<String, Object> entry : result.entrySet()) {
            assertThat(result.get(entry.getKey())).isEqualTo(this.formBody.get(entry.getKey()));
        }
    }

    private List<PropertyValueApplier> createAppliers() {
        List<DestinationSetter> setters = new ArrayList<>();
        setters.add(new PathVariableDestinationSetter(this.keys[0]));
        setters.add(new HeaderDestinationSetter(this.keys[1]));
        setters.add(new HeaderDestinationSetter(this.keys[2]));
        setters.add(new CookieDestinationSetter(this.keys[3]));
        setters.add(new CookieDestinationSetter(this.keys[4]));
        setters.add(new QueryDestinationSetter(this.keys[5]));
        setters.add(new ObjectEntitySetter("")); // address

        List<PropertyValueApplier> appliers = new ArrayList<>();
        setters.forEach(setter -> appliers.add(new UniqueDestinationPropertyValueApplier(setter,
                this.client.valueFetcher())));
        return appliers;
    }

    private HttpClassicClient createHttpClient() {
        ObjectSerializer jsonSerializer = new JacksonObjectSerializer(null, null, null, true);
        Map<String, ObjectSerializer> serializers =
                MapBuilder.<String, ObjectSerializer>get().put("json", jsonSerializer).build();
        ValueFetcher valueFetcher = new FastJsonValueHandler();
        HttpClassicClientFactory httpClassicClientFactory =
                new OkHttpClassicClientFactory(serializers, valueFetcher, 1);
        return httpClassicClientFactory.create();
    }

    private static int getLocalAvailablePort() {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        } catch (IOException e) {
            throw new IllegalStateException("Get local available port failed.", e);
        }
    }
}