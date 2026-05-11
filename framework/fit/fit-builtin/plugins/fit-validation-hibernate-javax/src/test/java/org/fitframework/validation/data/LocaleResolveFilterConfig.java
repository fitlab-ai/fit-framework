// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.validation.data;

import org.fitframework.http.util.i18n.LocaleResolveFilter;
import org.fitframework.annotation.Bean;
import org.fitframework.annotation.Component;

/**
 * 表示地区解析过滤器的配置类。
 *
 * @author 阮睿
 * @since 2025-09-11
 */
@Component
public class LocaleResolveFilterConfig {
    /**
     * 创建地区解析过滤器 bean 对象。
     *
     * @return 表示作为 bean 的地区解析过滤器对象的 {@link LocaleResolveFilter}。
     */
    @Bean
    public LocaleResolveFilter localeResolveFilter() {
        return new LocaleResolveFilter();
    }
}