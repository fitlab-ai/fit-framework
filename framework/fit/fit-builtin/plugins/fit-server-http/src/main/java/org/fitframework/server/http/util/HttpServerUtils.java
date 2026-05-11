// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.server.http.util;

import static org.fitframework.http.header.HttpHeaderKey.FIT_CODE;
import static org.fitframework.http.header.HttpHeaderKey.FIT_DATA_FORMAT;
import static org.fitframework.http.header.HttpHeaderKey.FIT_GENERICABLE_VERSION;
import static org.fitframework.http.header.HttpHeaderKey.FIT_MESSAGE;
import static org.fitframework.http.header.HttpHeaderKey.FIT_TLV;

import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.support.DefaultObjectEntity;
import org.fitframework.http.header.ContentType;
import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.http.protocol.MessageHeaderNames;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.http.server.DoHttpHandlerException;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.HttpHandler;
import org.fitframework.serialization.MessageSerializer;
import org.fitframework.serialization.http.HttpUtils;
import org.fitframework.serialization.util.MessageSerializerUtils;
import org.fitframework.broker.GenericableMetadata;
import org.fitframework.broker.server.Response;
import org.fitframework.conf.runtime.SerializationFormat;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.log.Logger;
import org.fitframework.resource.UrlUtils;
import org.fitframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;

/**
 * 表示 {@link HttpHandler} 相关的工具类。
 *
 * @author 王成
 * @since 2023-11-17
 */
public class HttpServerUtils {
    private static final Logger log = Logger.get(HttpServerUtils.class);

    /**
     * 获取请求中的序列化方式。
     *
     * @param request 表示请求的 {@link HttpClassicServerRequest}。
     * @return 表示请求中序列化方式的 {@code int}。
     */
    public static int getDataFormat(HttpClassicServerRequest request) {
        return request.headers()
                .first(FIT_DATA_FORMAT.value())
                .map(Integer::parseInt)
                .orElseGet(() -> request.contentType()
                        .map(ContentType::mediaType)
                        .filter(mediaType -> StringUtils.equalsIgnoreCase(mediaType, MimeType.APPLICATION_JSON.value()))
                        .map(mediaType -> SerializationFormat.JSON.code())
                        .orElseThrow(() -> new DoHttpHandlerException("No specified FIT-Data-Format.")));
    }

    /**
     * 向响应中设置消息头。
     *
     * @param response 表示响应的 {@link HttpClassicServerResponse}。
     * @param result 表示结果的 {@link Response}。
     */
    public static void setResponseHeaders(HttpClassicServerResponse response, Response result) {
        response.headers()
                .set(FIT_DATA_FORMAT.value(), String.valueOf(result.metadata().dataFormat()))
                .set(FIT_CODE.value(), String.valueOf(result.metadata().code()))
                .set(FIT_MESSAGE.value(), UrlUtils.encodePath(result.metadata().message()))
                .set(FIT_TLV.value(), HttpUtils.encode(result.metadata().tagValues().serialize()));
    }

    /**
     * 获取请求中的服务版本号。
     *
     * @param request 表示请求的 {@link HttpClassicServerRequest}。
     * @return 表示请求中的服务版本号的 {@link String}。
     */
    public static String getGenericableVersion(HttpClassicServerRequest request) {
        return request.headers().first(FIT_GENERICABLE_VERSION.value()).orElseGet(() -> {
            log.warn("No specified FIT-Genericable-Version in headers, use default value instead. "
                    + "[defaultGenericableVersion={}]", GenericableMetadata.DEFAULT_VERSION);
            return GenericableMetadata.DEFAULT_VERSION;
        });
    }

    /**
     * 向响应中设置返回状态码。
     *
     * @param response 表示响应的 {@link HttpClassicServerResponse}。
     * @param status 表示返回状态码的 {@link HttpResponseStatus}。
     */
    public static void setResponseCode(HttpClassicServerResponse response, HttpResponseStatus status) {
        response.statusCode(status.statusCode());
    }

    /**
     * 向响应中设置消息体。
     *
     * @param container 表示 Bean 容器的 {@link BeanContainer}。
     * @param dataFormat 表示请求的数据格式的 {@code int}。
     * @param response 表示响应的 {@link HttpClassicServerResponse}。
     * @param result 表示响应结果的 {@link Response}。
     */
    public static void setResponseEntity(BeanContainer container, int dataFormat, HttpClassicServerResponse response,
            Response result) {
        if (dataFormat == SerializationFormat.JSON.code()) {
            response.entity(new DefaultObjectEntity<>(response, result.data()));
        } else {
            byte[] responseData = getResponseData(container, result.type(), result.data(), dataFormat);
            response.headers().set(MessageHeaderNames.CONTENT_LENGTH, Integer.toString(responseData.length));
            response.entity(Entity.createBinaryEntity(response, new ByteArrayInputStream(responseData)));
        }
    }

    private static byte[] getResponseData(BeanContainer container, Type returnType, Object data, int format) {
        MessageSerializer messageSerializer = MessageSerializerUtils.getMessageSerializer(container, format)
                .orElseThrow(() -> new IllegalStateException(StringUtils.format(
                        "MessageSerializer required but not found. [format={0}]",
                        format)));
        return messageSerializer.serializeResponse(returnType, data);
    }
}
