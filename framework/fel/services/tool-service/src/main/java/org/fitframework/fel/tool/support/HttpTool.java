// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.support;

import static org.fitframework.inspection.Validation.isInstanceOf;
import static org.fitframework.inspection.Validation.isTrue;
import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.fel.tool.Tool;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.http.client.HttpClassicClientResponse;
import org.fitframework.http.client.HttpClientException;
import org.fitframework.http.client.proxy.Authorization;
import org.fitframework.http.client.proxy.DestinationSetter;
import org.fitframework.http.client.proxy.HttpEmitter;
import org.fitframework.http.client.proxy.PropertyValueApplier;
import org.fitframework.http.client.proxy.emitter.DefaultHttpEmitter;
import org.fitframework.http.client.proxy.support.applier.MultiDestinationsPropertyValueApplier;
import org.fitframework.http.client.proxy.support.setter.AuthorizationDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.CookieDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.DestinationSetterInfo;
import org.fitframework.http.client.proxy.support.setter.FormUrlEncodedEntitySetter;
import org.fitframework.http.client.proxy.support.setter.HeaderDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.ObjectEntitySetter;
import org.fitframework.http.client.proxy.support.setter.PathVariableDestinationSetter;
import org.fitframework.http.client.proxy.support.setter.QueryDestinationSetter;
import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.ObjectEntity;
import org.fitframework.http.entity.TextEntity;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.MapBuilder;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;
import org.fitframework.value.ValueFetcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 表示 {@link Tool} 的 Http 调用实现。
 *
 * @author 季聿阶
 * @since 2024-05-10
 */
public class HttpTool extends AbstractTool {
    /**
     * 表示 HTTP 调用工具的类型。
     */
    public static final String TYPE = "HTTP";
    private static final String KEY_OF_METHOD = "method";
    private static final String KEY_OF_PROTOCOL = "protocol";
    private static final String KEY_OF_DOMAIN = "domain";
    private static final String KEY_OF_PATH_PATTERN = "pathPattern";
    private static final String KEY_OF_MAPPINGS = "mappings";
    private static final String KEY_OF_HTTP_SOURCE = "httpSource";
    private static final String KEY_OF_ORDER = "order";
    private static final String KEY_OF_KEY_VALUE_SETTER = "key";
    private static final String KEY_OF_ENTITY_SETTER = "propertyPath";
    private static final String KEY_OF_PATH_AUTHORIZATION = "authorization";

    private final HttpClassicClientFactory factory;
    private final ValueFetcher valueFetcher;
    private final HttpEmitter emitter;
    private final Map<String, Function<Map<String, String>, DestinationSetter>> destinationSetterCreators =
            MapBuilder.<String, Function<Map<String, String>, DestinationSetter>>get()
                    .put("COOKIE", this::createCookieDestinationSetter)
                    .put("FORM_URL_ENCODED_ENTITY", this::createFormUrlEncodedEntitySetter)
                    .put("HEADER", this::createHeaderDestinationSetter)
                    .put("OBJECT_ENTITY", this::createObjectEntitySetter)
                    .put("PATH_VARIABLE", this::createPathVariableDestinationSetter)
                    .put("QUERY", this::createQueryDestinationSetter)
                    .put("AUTHORIZATION", this::createAuthorizationDestinationSetter)
                    .build();

    /**
     * 通过工具的基本信息和工具元数据来初始化 {@link AbstractTool} 的新实例。
     *
     * @param factory 表示 Http 客户端的工厂的 {@link HttpClassicClientFactory}。
     * @param serializer 表示 Json 序列化器的 {@link ObjectSerializer}。
     * @param valueFetcher 表示值获取工具的 {@link ValueFetcher}。
     * @param itemInfo 表示工具的基本信息的 {@link Tool.Info}。
     * @param metadata 表示工具的元数据的 {@link Tool.Metadata}。
     */
    protected HttpTool(HttpClassicClientFactory factory, ObjectSerializer serializer, ValueFetcher valueFetcher,
            Tool.Info itemInfo, Tool.Metadata metadata) {
        super(serializer, itemInfo, metadata);
        this.factory = notNull(factory, "The http classic client factory cannot be null.");
        this.valueFetcher = notNull(valueFetcher, "The value fetcher cannot be null.");
        this.emitter = this.createHttpEmitter();
    }

    @Override
    public Object execute(Object... args) throws HttpClientException {
        try (HttpClassicClientResponse<Object> response = this.emitter.emit(args)) {
            if (response.statusCode() == HttpResponseStatus.NO_CONTENT.statusCode()) {
                return null;
            }
            Optional<Entity> opEntity = response.entity();
            isTrue(opEntity.isPresent(), () -> new HttpClientException("Cannot get response entity."));
            Entity entity = opEntity.get();
            if (entity instanceof ObjectEntity) {
                return ((ObjectEntity<?>) entity).object();
            } else if (entity instanceof TextEntity) {
                return ((TextEntity) entity).content();
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new HttpClientException("Failed to execute http tool.", e);
        }
    }

    private HttpEmitter createHttpEmitter() {
        Map<String, Object> httpRunnables = cast(getElementFromMap(this.info().runnables(), TYPE, Map.class));
        return new DefaultHttpEmitter(getPropertyValueAppliers(httpRunnables),
                this.factory.create(),
                getHttpRequestMethod(httpRunnables),
                getProtocol(httpRunnables),
                getDomain(httpRunnables),
                getPathPattern(httpRunnables),
                getAuthorization(httpRunnables));
    }

    private List<PropertyValueApplier> getPropertyValueAppliers(Map<String, Object> httpRunnables) {
        Map<String, Map<String, Map<String, String>>> mappings =
                cast(getElementFromMap(httpRunnables, KEY_OF_MAPPINGS, Map.class));
        List<String> order = cast(getElementFromMap(this.info().schema(), KEY_OF_ORDER, List.class));
        if (order.size() != mappings.size() || !isSameSet(new HashSet<>(order), mappings.keySet())) {
            throw new IllegalArgumentException("Arguments in order is not same to key in mappings.");
        }
        return this.createAppliers(order, mappings);
    }

    private static HttpRequestMethod getHttpRequestMethod(Map<String, Object> httpRunnables) {
        String methodAsString = getElementFromMap(httpRunnables, KEY_OF_METHOD, String.class);
        return notNull(HttpRequestMethod.from(methodAsString),
                StringUtils.format("Cannot create HttpRequestMethod by field \"{0}\". [methodAsString={1}]",
                        KEY_OF_METHOD,
                        methodAsString));
    }

    private static String getProtocol(Map<String, Object> httpRunnables) {
        return getElementFromMap(httpRunnables, KEY_OF_PROTOCOL, String.class);
    }

    private static String getDomain(Map<String, Object> httpRunnables) {
        return getElementFromMap(httpRunnables, KEY_OF_DOMAIN, String.class);
    }

    private static String getPathPattern(Map<String, Object> httpRunnables) {
        return getElementFromMap(httpRunnables, KEY_OF_PATH_PATTERN, String.class);
    }

    private static Authorization getAuthorization(Map<String, Object> httpRunnables) {
        Object authorization = httpRunnables.get(KEY_OF_PATH_AUTHORIZATION);
        if (authorization != null && !(authorization instanceof Map)) {
            return Authorization.createEmpty();
        }
        return Authorization.create(ObjectUtils.cast(authorization));
    }

    private List<PropertyValueApplier> createAppliers(List<String> order,
            Map<String, Map<String, Map<String, String>>> mappings) {
        List<PropertyValueApplier> appliers = new ArrayList<>();
        for (String argumentName : order) {
            List<DestinationSetterInfo> setterInfos = new ArrayList<>();
            for (Map.Entry<String, Map<String, String>> entry : mappings.get(argumentName).entrySet()) {
                String jsonPath = entry.getKey();
                Map<String, String> info = entry.getValue();
                DestinationSetter destinationSetter = this.createDestinationSetter(info);
                DestinationSetterInfo destinationSetterInfo = new DestinationSetterInfo(destinationSetter, jsonPath);
                setterInfos.add(destinationSetterInfo);
            }
            appliers.add(new MultiDestinationsPropertyValueApplier(setterInfos, this.valueFetcher));
        }
        return appliers;
    }

    private DestinationSetter createDestinationSetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_HTTP_SOURCE), "Setter info must contain field httpSource.");
        String httpSource = info.get(KEY_OF_HTTP_SOURCE);
        isTrue(this.destinationSetterCreators.containsKey(httpSource),
                StringUtils.format("Cannot find destination setter creator. [httpSource={0}]", httpSource));
        return this.destinationSetterCreators.get(httpSource).apply(info);
    }

    private DestinationSetter createCookieDestinationSetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_KEY_VALUE_SETTER),
                "Setter info of cookie destination setter must contain field key.");
        return new CookieDestinationSetter(info.get(KEY_OF_KEY_VALUE_SETTER));
    }

    private DestinationSetter createFormUrlEncodedEntitySetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_KEY_VALUE_SETTER),
                "Setter info of form url encode destination setter must contain field key.");
        return new FormUrlEncodedEntitySetter(info.get(KEY_OF_KEY_VALUE_SETTER));
    }

    private DestinationSetter createHeaderDestinationSetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_KEY_VALUE_SETTER),
                "Setter info of header destination setter must contain field key.");
        return new HeaderDestinationSetter(info.get(KEY_OF_KEY_VALUE_SETTER));
    }

    private DestinationSetter createObjectEntitySetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_ENTITY_SETTER),
                "Setter info of object entity destination setter must contain field propertyPath.");
        return new ObjectEntitySetter(info.get(KEY_OF_ENTITY_SETTER));
    }

    private DestinationSetter createPathVariableDestinationSetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_KEY_VALUE_SETTER),
                "Setter info of path variable destination setter must contain field key.");
        return new PathVariableDestinationSetter(info.get(KEY_OF_KEY_VALUE_SETTER));
    }

    private DestinationSetter createQueryDestinationSetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_KEY_VALUE_SETTER),
                "Setter info of query destination setter must contain field key.");
        return new QueryDestinationSetter(info.get(KEY_OF_KEY_VALUE_SETTER));
    }

    private DestinationSetter createAuthorizationDestinationSetter(Map<String, String> info) {
        isTrue(info.containsKey(KEY_OF_KEY_VALUE_SETTER),
                "Setter info of authorization destination setter must contain field key.");
        return new AuthorizationDestinationSetter(info.get(KEY_OF_KEY_VALUE_SETTER));
    }

    private static <T> T getElementFromMap(Map<String, Object> map, String fieldName, Class<T> clazz) {
        isTrue(map.containsKey(fieldName), StringUtils.format("The map not contains field \"{0}\"", fieldName));
        Object ele = map.get(fieldName);
        isInstanceOf(ele, clazz, "The value of the field \"{0}\" is not an instance of {1}.", fieldName, clazz);
        return cast(ele);
    }

    private static <T> boolean isSameSet(Set<T> first, Set<T> second) {
        if (first.size() != second.size()) {
            return false;
        }
        return first.containsAll(second);
    }
}