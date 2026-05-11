// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import org.fitframework.http.entity.FileEntity;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;

/**
 * 表示用户自定义资源处理器。
 *
 * @author 邬涨财
 * @since 2024-12-30
 */
public interface CustomResourceHandler {
    /**
     * 表示自定义资源的处理。
     *
     * @param positionName 表示文件消息体数据的显示位置名的 {@link String}。
     * @param request 表示服务端的 Http 请求的 {@link HttpClassicServerRequest}。
     * @param response 表示服务端的 Http 响应的 {@link HttpClassicServerResponse}。
     * @return 表示需要访问的资源的消息体数据的 {@link FileEntity}。
     */
    FileEntity handle(String positionName, HttpClassicServerRequest request, HttpClassicServerResponse response);

    /**
     * 表示资源处理器是否可以处理当前请求。
     *
     * @param positionName 表示文件消息体数据的显示位置名的 {@link String}。
     * @param request 表示服务端的 Http 请求的 {@link HttpClassicServerRequest}。
     * @return 表示资源处理器是否可以处理请求的 {@code boolean}。
     */
    boolean canHandle(String positionName, HttpClassicServerRequest request);
}
