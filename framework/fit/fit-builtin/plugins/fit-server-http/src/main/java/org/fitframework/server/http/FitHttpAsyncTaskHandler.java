// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.server.http;

import static org.fitframework.http.header.HttpHeaderKey.FIT_TLV;

import org.fitframework.http.exception.AsyncTaskExecutionException;
import org.fitframework.http.exception.AsyncTaskNotCompletedException;
import org.fitframework.http.exception.AsyncTaskNotFoundException;
import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.http.server.DoHttpHandlerException;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.handler.AbstractHttpHandler;
import org.fitframework.serialization.http.HttpUtils;
import org.fitframework.server.http.support.AsyncTaskExecutor;
import org.fitframework.server.http.util.HttpServerUtils;
import org.fitframework.broker.server.Response;
import org.fitframework.conf.runtime.WorkerConfig;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.serialization.RequestMetadata;
import org.fitframework.serialization.ResponseMetadata;
import org.fitframework.serialization.TagLengthValues;
import org.fitframework.serialization.tlv.TlvUtils;
import org.fitframework.util.StringUtils;

import java.util.Optional;

/**
 * 表示处理 FIT 通信方式的处理器。
 *
 * @author 王成
 * @since 2023-11-16
 */
public class FitHttpAsyncTaskHandler extends AbstractHttpHandler {
    private final BeanContainer container;
    private final WorkerConfig worker;

    FitHttpAsyncTaskHandler(BeanContainer container, WorkerConfig worker, StaticInfo staticInfo,
            ExecutionInfo executionInfo) {
        super(staticInfo, executionInfo);
        this.container = container;
        this.worker = worker;
    }

    @Override
    public void handle(HttpClassicServerRequest request, HttpClassicServerResponse response)
            throws DoHttpHandlerException {
        RequestMetadata metadata = this.getRequestMetadata(request);
        try {
            String sourceWorkerId = TlvUtils.getWorkerId(metadata.tagValues());
            String sourceWorkerInstanceId = TlvUtils.getWorkerInstanceId(metadata.tagValues());
            Optional<Response> resultOp =
                    AsyncTaskExecutor.INSTANCE.longPolling(sourceWorkerId, sourceWorkerInstanceId);
            if (resultOp.isPresent()) {
                HttpServerUtils.setResponseCode(response, HttpResponseStatus.OK);
                Response result = resultOp.get();
                HttpServerUtils.setResponseHeaders(response, result);
                HttpServerUtils.setResponseEntity(this.container, metadata.dataFormat(), response, result);
            } else {
                this.fail(response, metadata.dataFormat(), AsyncTaskNotCompletedException.CODE, StringUtils.EMPTY);
            }
        } catch (AsyncTaskNotFoundException | AsyncTaskExecutionException e) {
            this.fail(response, metadata.dataFormat(), e.getCode(), e.getMessage());
        }
    }

    private void fail(HttpClassicServerResponse response, int dataFormatCode, int code, String message) {
        HttpServerUtils.setResponseCode(response, HttpResponseStatus.OK);
        Response result = Response.create(ResponseMetadata.custom()
                .dataFormat(dataFormatCode)
                .code(code)
                .message(message)
                .build());
        TlvUtils.setWorkerId(result.metadata().tagValues(), this.worker.id());
        TlvUtils.setWorkerInstanceId(result.metadata().tagValues(), this.worker.instanceId());
        HttpServerUtils.setResponseHeaders(response, result);
    }

    private RequestMetadata getRequestMetadata(HttpClassicServerRequest request) {
        int dataFormat = HttpServerUtils.getDataFormat(request);
        TagLengthValues tagLengthValues = request.headers()
                .first(FIT_TLV.value())
                .map(HttpUtils::decode)
                .map(TagLengthValues::deserialize)
                .orElseGet(TagLengthValues::create);
        return RequestMetadata.custom().dataFormat(dataFormat).tagValues(tagLengthValues).build();
    }
}
