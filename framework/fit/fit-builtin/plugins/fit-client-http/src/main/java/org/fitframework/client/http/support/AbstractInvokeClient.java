// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.client.Request;
import org.fitframework.client.http.HttpsConstants;
import org.fitframework.client.http.InvokeClient;
import org.fitframework.client.http.util.HttpClientUtils;
import org.fitframework.http.client.HttpClassicClient;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.http.client.HttpClassicClientRequest;
import org.fitframework.http.entity.Entity;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.protocol.MessageHeaderNames;
import org.fitframework.http.protocol.MimeType;
import org.fitframework.http.protocol.Protocol;
import org.fitframework.security.Decryptor;
import org.fitframework.serialization.MessageSerializer;
import org.fitframework.serialization.util.MessageSerializerUtils;
import org.fitframework.conf.runtime.ClientConfig;
import org.fitframework.conf.runtime.SerializationFormat;
import org.fitframework.conf.runtime.WorkerConfig;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 表示 {@link InvokeClient} 的抽象实现类。
 *
 * @author 季聿阶
 * @since 2024-02-17
 */
public abstract class AbstractInvokeClient implements InvokeClient {
    private static final String APPLICATION_JSON = "application/json;charset=UTF-8";

    private final BeanContainer container;
    private final HttpClassicClientFactory factory;
    private final WorkerConfig workerConfig;
    private final ClientConfig clientConfig;

    protected AbstractInvokeClient(BeanContainer container, WorkerConfig workerConfig, ClientConfig clientConfig) {
        this.container = notNull(container, "The bean container cannot be null.");
        this.factory = container.all(HttpClassicClientFactory.class)
                .stream()
                .map(BeanFactory::<HttpClassicClientFactory>get)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("The http classic client factory cannot be null."));
        this.workerConfig = notNull(workerConfig, "The worker config cannot be null.");
        this.clientConfig = notNull(clientConfig, "The http config cannot be null.");
    }

    /**
     * 获取当前的 Bean 容器。
     *
     * @return 表示当前 Bean 容器的 {@link BeanContainer}。
     */
    protected BeanContainer getContainer() {
        return this.container;
    }

    /**
     * 获取当前进程的配置信息。
     *
     * @return 表示当前进程的配置信息的 {@link WorkerConfig}。
     */
    protected WorkerConfig getWorkerConfig() {
        return this.workerConfig;
    }

    /**
     * 构建 Http 客户端。
     *
     * @param request 表示 Http 请求的 {@link Request}。
     * @return 表示构建出来的 Http 客户端的 {@link HttpClassicClient}。
     */
    protected HttpClassicClient buildHttpClient(Request request) {
        Optional<ClientConfig.Secure> secureInfo = this.clientConfig.secure();
        Map<String, Object> config = new HashMap<>();
        if (secureInfo.isPresent()) {
            ClientConfig.Secure secure = secureInfo.get();
            boolean isEncrypted = secure.encrypted();
            String trustStorePassword = secure.trustStorePassword().orElse(StringUtils.EMPTY);
            String keyStorePassword = secure.keyStorePassword().orElse(StringUtils.EMPTY);
            if (isEncrypted) {
                Decryptor decryptor =
                        notNull(this.container.beans().lookup(Decryptor.class), "The Decryptor cannot be null.");
                if (StringUtils.isNotBlank(trustStorePassword)) {
                    trustStorePassword = decryptor.decrypt(trustStorePassword);
                }
                if (StringUtils.isNotBlank(keyStorePassword)) {
                    keyStorePassword = decryptor.decrypt(keyStorePassword);
                }
            }
            secure.keyStoreFile()
                    .ifPresent(keyStore -> config.put(HttpsConstants.CLIENT_SECURE_KEY_STORE_FILE, keyStore));
            secure.trustStoreFile()
                    .ifPresent(trustStore -> config.put(HttpsConstants.CLIENT_SECURE_TRUST_STORE_FILE, trustStore));
            secure.secureProtocol()
                    .ifPresent(protocol -> config.put(HttpsConstants.CLIENT_SECURE_SECURITY_PROTOCOL, protocol));
            config.put(HttpsConstants.CLIENT_SECURE_IGNORE_TRUST, String.valueOf(secure.ignoreTrust()));
            config.put(HttpsConstants.CLIENT_SECURE_IGNORE_HOSTNAME, String.valueOf(secure.ignoreHostName()));
            config.put(HttpsConstants.CLIENT_SECURE_KEY_STORE_PASSWORD, keyStorePassword);
            config.put(HttpsConstants.CLIENT_SECURE_TRUST_STORE_PASSWORD, trustStorePassword);
            config.put(HttpsConstants.CLIENT_SECURE_STRONG_RANDOM, String.valueOf(secure.secureRandomEnabled()));
        }
        int timeout = this.getTimeout(request);
        return this.factory.create(HttpClassicClientFactory.Config.builder()
                .connectTimeout(timeout)
                .connectionRequestTimeout(timeout)
                .socketTimeout(timeout)
                .custom(config)
                .build());
    }

    /**
     * 获取超时时间。
     * <p>单位为毫秒。</p>
     *
     * @param request 表示 Http 请求的 {@link Request}。
     * @return 表示超时时间的 {@code int}。
     */
    protected int getTimeout(Request request) {
        return Math.toIntExact(request.context().timeoutUnit().toMillis(request.context().timeout()));
    }

    /**
     * 构建 Http 客户端的请求。
     *
     * @param client 表示 Http 客户端的 {@link HttpClassicClient}。
     * @param request 表示 Http 请求的 {@link Request}。
     * @return 表示构建出来的 Http 客户端请求的 {@link HttpClassicClientRequest}。
     */
    protected HttpClassicClientRequest buildClientRequest(HttpClassicClient client, Request request) {
        ConnectionBuilder builder = ConnectionBuilderFactory.getConnectionBuilder(Protocol.from(request.protocol()));
        String url = builder.buildUrl(request);
        HttpClassicClientRequest clientRequest = client.createRequest(HttpRequestMethod.POST, url);
        HttpClientUtils.fillBaseHeaders(clientRequest, request, this.workerConfig);
        return clientRequest;
    }

    /**
     * 构建 Http 的消息体。
     *
     * @param clientRequest 表示 Http 客户端请求的 {@link HttpClassicClientRequest}。
     * @param request 表示 Http 请求的 {@link Request}。
     * @return 表示构建出来的 Http 消息体的 {@link Entity}。
     */
    protected Entity buildHttpEntity(HttpClassicClientRequest clientRequest, Request request) {
        int format = request.metadata().dataFormat();
        if (format == SerializationFormat.JSON.code()) {
            clientRequest.headers().add(MessageHeaderNames.CONTENT_TYPE, APPLICATION_JSON);
            return Entity.createObject(clientRequest, request.data());
        }
        clientRequest.headers().add(MessageHeaderNames.CONTENT_TYPE, MimeType.APPLICATION_OCTET_STREAM.value());
        MessageSerializer messageSerializer = MessageSerializerUtils.getMessageSerializer(this.container, format)
                .orElseThrow(() -> new IllegalStateException(StringUtils.format(
                        "MessageSerializer required but not found. [format={0}]",
                        format)));
        byte[] bytes = messageSerializer.serializeRequest(request.dataTypes(), request.data());
        clientRequest.headers().add(MessageHeaderNames.CONTENT_LENGTH, Integer.toString(bytes.length));
        return Entity.createBinaryEntity(clientRequest, new ByteArrayInputStream(bytes));
    }
}
