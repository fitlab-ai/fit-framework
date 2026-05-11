// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.okhttp;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.protocol.ClientRequest;
import org.fitframework.http.protocol.ClientResponse;
import org.fitframework.http.protocol.ConfigurableMessageHeaders;
import org.fitframework.http.protocol.HttpRequestMethod;
import org.fitframework.http.protocol.HttpVersion;
import org.fitframework.http.protocol.QueryCollection;
import org.fitframework.http.protocol.RequestLine;
import org.fitframework.http.protocol.WritableMessageBody;
import org.fitframework.http.protocol.support.ClientRequestBody;
import org.fitframework.model.MultiValueMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * 表示 {@link ClientRequest} 的使用 OkHttp 实现。
 *
 * @author 杭潇
 * @since 2024-04-09
 */
public class OkHttpClientRequest implements ClientRequest {
    private final HttpRequestMethod method;
    private final URL url;
    private final ConfigurableMessageHeaders headers;
    private final WritableMessageBody body;
    private boolean isClosed;
    private final OkHttpClient okHttpClient;
    private final okhttp3.Request.Builder requestBuilder;
    private final ByteArrayOutputStream stream = new ByteArrayOutputStream();

    /**
     * 表示 {@link OkHttpClientRequest} 类的构造方法。
     *
     * @param method 表示指定的 Http 方法的 {@link HttpRequestMethod}。
     * @param url 表示 Http 请求地址的 {@link String}。
     * @param okHttpClient 表示 OkHttp 客户端的 {@link OkHttpClient}。
     */
    public OkHttpClientRequest(HttpRequestMethod method, String url, OkHttpClient okHttpClient) {
        this.method = notNull(method, "The request method cannot be null.");
        this.okHttpClient = notNull(okHttpClient, "The okhttp client cannot be null.");
        try {
            this.url = new URL(url);
            this.requestBuilder = new Request.Builder().url(this.url);
        } catch (IOException e) {
            throw new IllegalStateException("The url is incorrect.", e);
        }
        this.headers = ConfigurableMessageHeaders.create();
        this.body = new ClientRequestBody(this);
    }

    @Override
    public void writeStartLineAndHeaders() throws IOException {
        this.checkIfClosed();
        this.requestBuilder.setMethod$okhttp(this.method.name());
        for (String headerName : this.headers.names()) {
            List<String> values = this.headers.all(headerName);
            for (String value : values) {
                this.requestBuilder.addHeader(headerName, value);
            }
        }
    }

    private void checkIfClosed() throws IOException {
        if (this.isClosed) {
            throw new IOException("The okhttp client request has already been closed.");
        }
    }

    @Override
    public void writeBody(int b) throws IOException {
        this.checkIfClosed();
        this.stream.write(b);
        this.updateRequestBody();
    }

    @Override
    public void writeBody(byte[] bytes, int off, int len) throws IOException {
        this.checkIfClosed();
        this.stream.write(bytes, off, len);
        this.updateRequestBody();
    }

    private void updateRequestBody() {
        this.requestBuilder.method(this.method.name(), RequestBody.create(this.stream.toByteArray(), null));
    }

    @Override
    public void flush() {}

    @Override
    public ClientResponse readResponse() throws IOException {
        this.checkIfClosed();
        Response response = this.okHttpClient.newCall(this.requestBuilder.build()).execute();
        notNull(response.body(), () -> new IllegalStateException("The response body cannot be null."));
        return ClientResponse.create(response.code(),
                response.message(),
                MultiValueMap.create(response.headers().toMultimap()),
                response.body().byteStream());
    }

    @Override
    public RequestLine startLine() {
        return RequestLine.create(HttpVersion.HTTP_1_1,
                this.method,
                this.url.getPath(),
                QueryCollection.create(this.url.getQuery()));
    }

    @Override
    public ConfigurableMessageHeaders headers() {
        return this.headers;
    }

    @Override
    public WritableMessageBody body() {
        return this.body;
    }

    @Override
    public void close() throws IOException {
        this.isClosed = true;
        this.body.close();
    }
}