// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.http.server.handler.support.HttpResponseStatusResolverComposite;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 表示 {@link HttpResponseStatusResolver} 的单元测试。
 *
 * @author 白鹏坤
 * @since 2023-02-20
 */
@DisplayName("测试 HttpResponseStatusResolver 类")
class HttpResponseStatusResolverTest {
    @Test
    @DisplayName("将多个 Http 响应状态的解析器合并为一个")
    void shouldReturnCombinedHttpResponseStatusResolver() {
        final Method[] methods = this.getClass().getMethods();
        final HttpResponseStatusResolverComposite resolverComposite1 =
                new HttpResponseStatusResolverComposite(method -> Optional.of(HttpResponseStatus.OK));
        final HttpResponseStatusResolverComposite resolverComposite2 = new HttpResponseStatusResolverComposite(
                method -> Optional.of(HttpResponseStatus.INTERNAL_SERVER_ERROR));
        final HttpResponseStatusResolverComposite[] composites = {resolverComposite1, resolverComposite2};
        final HttpResponseStatusResolver combine = HttpResponseStatusResolver.combine(composites);
        final Optional<HttpResponseStatus> status = combine.resolve(methods[0]);
        assertThat(status).isPresent().get().isEqualTo(HttpResponseStatus.OK);
    }
}
