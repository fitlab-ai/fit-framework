// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import static org.fitframework.http.protocol.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.http.entity.Entity;
import org.fitframework.http.entity.EntitySerializer;
import org.fitframework.http.entity.ObjectEntity;
import org.fitframework.http.entity.TextEntity;
import org.fitframework.http.entity.TextEventStreamEntity;
import org.fitframework.http.protocol.HttpResponse;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.http.server.DoHttpHandlerException;
import org.fitframework.http.server.ErrorResponse;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.HttpHandler;
import org.fitframework.http.server.HttpServerResponseException;
import org.fitframework.http.server.handler.comparator.ClassComparator;
import org.fitframework.flowable.Choir;
import org.fitframework.log.Logger;
import org.fitframework.serialization.ObjectSerializer;
import org.fitframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 表示 {@link AbstractReflectibleHttpHandler} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-08-11
 */
public class DefaultReflectibleHttpHandler extends AbstractReflectibleHttpHandler implements HttpHandler {
    private static final Logger log = Logger.get(DefaultReflectibleHttpHandler.class);

    private Map<Class<Throwable>, Map<String, HttpExceptionHandler>> globalExceptionHandlers = new HashMap<>();
    private final Map<Class<Throwable>, HttpExceptionHandler> pluginExceptionHandlers =
            new ConcurrentSkipListMap<>(ClassComparator.INSTANCE);
    private EntitySerializer<ObjectEntity<Object>> customJsonEntitySerializer;
    private ObjectSerializer customJsonSerializer;

    public DefaultReflectibleHttpHandler(StaticInfo staticInfo, ExecutionInfo executionInfo) {
        super(staticInfo, executionInfo);
    }

    /**
     * 设置全局的异常处理器集合。
     *
     * @param exceptionHandlers 表示待设置的全局异常处理器集合的 {@link Map}{@code <}{@link Class}{@code <}{@link Throwable}
     * {@code >, }{@link Map}{@code <}{@link String}{@code , }{@link HttpExceptionHandler}{@code >>}。
     */
    public void setGlobalExceptionHandler(Map<Class<Throwable>, Map<String, HttpExceptionHandler>> exceptionHandlers) {
        this.globalExceptionHandlers = exceptionHandlers;
    }

    /**
     * 向当前处理器中添加插件范围的异常处理器集合。
     *
     * @param exceptionHandlers 表示待添加的插件范围的异常处理器集合的 {@link Map}{@code <}{@link Class}{@code
     * <}{@link Throwable}{@code >}{@code , }{@link HttpExceptionHandler}{@code >}。
     */
    public void addPluginExceptionHandler(Map<Class<Throwable>, HttpExceptionHandler> exceptionHandlers) {
        // TODO 此处未考虑插件卸载逻辑。
        this.pluginExceptionHandlers.putAll(ObjectUtils.getIfNull(exceptionHandlers, Collections::emptyMap));
    }

    /**
     * 向当前处理器中添加自定义的 Json 序列化器。
     *
     * @param jsonSerializer 表示待添加的自定义 Json 序列化器的 {@link ObjectSerializer}。
     */
    public void addCustomJsonSerializer(ObjectSerializer jsonSerializer) {
        if (jsonSerializer != null) {
            this.customJsonSerializer = jsonSerializer;
            this.customJsonEntitySerializer = EntitySerializer.jsonSerializer(jsonSerializer);
        }
    }

    @Override
    public void handle(HttpClassicServerRequest request, HttpClassicServerResponse response)
            throws DoHttpHandlerException {
        request.customEntitySerializer(MimeType.APPLICATION_JSON, this.customJsonEntitySerializer);
        request.customJsonSerializer(this.customJsonSerializer);
        response.customEntitySerializer(MimeType.APPLICATION_JSON, this.customJsonEntitySerializer);
        response.customJsonSerializer(this.customJsonSerializer);
        super.handle(request, response);
    }

    @Override
    protected void handleResult(HttpClassicServerRequest request, HttpClassicServerResponse response, Object[] args,
            Object result) {
        if (result instanceof HttpResponse) {
            HttpResponse httpResponse = cast(result);
            response.statusCode(httpResponse.status().statusCode());
            this.setEntity(response, httpResponse.entity());
        } else {
            response.statusCode(this.statusCode());
            this.setEntity(response, result);
        }
    }

    @Override
    protected void handleException(HttpClassicServerRequest request, HttpClassicServerResponse response, Object[] args,
            Throwable cause) throws DoHttpHandlerException {
        Optional<HttpExceptionHandler> opExceptionHandler = this.findExceptionHandler(cast(cause.getClass()));
        Object result;
        if (opExceptionHandler.isPresent()) {
            HttpExceptionHandler exceptionHandler = opExceptionHandler.get();
            try {
                result = exceptionHandler.handle(request, response, cause);
            } catch (Exception e) {
                e.addSuppressed(cause);
                log.error("Failed to handle exception.", e);
                log.error("The previous exception is below.", cause);
                result =
                        ErrorResponse.create(INTERNAL_SERVER_ERROR, e.getMessage(), cause.getMessage(), request.path());
            }
            if (result instanceof HttpResponse) {
                HttpResponse httpResponse = cast(result);
                response.statusCode(httpResponse.status().statusCode());
                result = httpResponse.entity();
            } else {
                response.statusCode(exceptionHandler.statusCode());
            }
        } else {
            log.error("No concrete exception handler to handle exception.", cause);
            ErrorResponse errorResponse;
            if (cause instanceof HttpServerResponseException) {
                HttpServerResponseException actualException = cast(cause);
                errorResponse =
                        ErrorResponse.create(actualException.responseStatus(), cause.getMessage(), request.path());
            } else {
                errorResponse = ErrorResponse.create(INTERNAL_SERVER_ERROR, cause.getMessage(), request.path());
            }
            response.statusCode(errorResponse.getStatus());
            result = errorResponse;
        }
        this.setEntity(response, result);
    }

    private Optional<HttpExceptionHandler> findExceptionHandler(Class<Throwable> cause) {
        Optional<HttpExceptionHandler> handler = this.fromPluginExceptionHandlers(cause);
        if (handler.isPresent()) {
            return handler;
        }
        return this.fromGlobalExceptionHandlers(cause);
    }

    private Optional<HttpExceptionHandler> fromPluginExceptionHandlers(Class<Throwable> cause) {
        if (this.pluginExceptionHandlers.containsKey(cause)) {
            return Optional.of(this.pluginExceptionHandlers.get(cause));
        }
        for (Map.Entry<Class<Throwable>, HttpExceptionHandler> entry : this.pluginExceptionHandlers.entrySet()) {
            if (entry.getKey().isAssignableFrom(cause)) {
                return Optional.of(entry.getValue());
            }
        }
        return Optional.empty();
    }

    private Optional<HttpExceptionHandler> fromGlobalExceptionHandlers(Class<Throwable> cause) {
        if (this.globalExceptionHandlers.containsKey(cause)) {
            Iterator<Map.Entry<String, HttpExceptionHandler>> iterator =
                    this.globalExceptionHandlers.get(cause).entrySet().iterator();
            if (iterator.hasNext()) {
                return Optional.of(iterator.next().getValue());
            }
        }
        for (Map.Entry<Class<Throwable>, Map<String, HttpExceptionHandler>> entry :
                this.globalExceptionHandlers.entrySet()) {
            if (entry.getKey().isAssignableFrom(cause)) {
                Iterator<Map.Entry<String, HttpExceptionHandler>> iterator = entry.getValue().entrySet().iterator();
                if (iterator.hasNext()) {
                    return Optional.of(iterator.next().getValue());
                }
            }
        }
        return Optional.empty();
    }

    private void setEntity(HttpClassicServerResponse response, Object result) {
        if (result == null) {
            response.entity(null);
        } else if (result instanceof Entity) {
            response.entity(cast(result));
        } else if (result instanceof String) {
            response.entity(TextEntity.create(response, (String) result));
        } else if (result instanceof Choir) {
            response.entity(TextEventStreamEntity.create(response, (Choir<?>) result));
        } else {
            response.entity(ObjectEntity.create(response, result));
        }
    }
}