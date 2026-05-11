// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.filter.route;

import org.fitframework.annotation.Fit;
import org.fitframework.annotation.Genericable;
import org.fitframework.broker.client.Router;

/**
 * 使用规则路由的过滤器。
 * <p><b>使用规则路由时，请直接使用 {@link Fit} 注入该过滤器。</b></p>
 *
 * @author 季聿阶
 * @since 2021-06-17
 */
@Genericable
public interface RuleFilter extends Router.Filter {}
