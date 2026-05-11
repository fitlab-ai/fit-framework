// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.client.filter.route;

import org.fitframework.annotation.Fit;
import org.fitframework.annotation.Genericable;
import org.fitframework.broker.client.Router;

/**
 * 优先使用规则路由的过滤器。
 * <p><b>优先使用规则路由时，请直接使用 {@link Fit} 注入该过滤器。</b></p>
 * <p>该过滤器的行为如下：</p>
 * <ol>
 *     <li>如果存在规则，则使用规则路由，规则路由失败则报错。</li>
 *     <li>如果不存在规则，则使用默认路由，默认路由不存在则报错。</li>
 * </ol>
 *
 * @author 季聿阶
 * @since 2021-08-16
 */
@Genericable
public interface PreferRuleFilter extends Router.Filter {}
