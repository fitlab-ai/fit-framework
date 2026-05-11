// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.globalization;

import org.fitframework.util.ArrayUtils;
import org.fitframework.util.MapUtils;
import org.fitframework.util.StringUtils;

import java.util.Locale;
import java.util.Map;

/**
 * 为 {@link StringResource} 提供空实现。
 *
 * @author 梁济时
 * @since 2022-11-22
 */
final class EmptyStringResource implements StringResource {
    /**
     * 表示当前类型的唯一实例。
     */
    static final EmptyStringResource INSTANCE = new EmptyStringResource();

    /**
     * 隐藏默认构造方法，避免单例类型被外部实例化。
     */
    private EmptyStringResource() {}

    @Override
    public String getMessageWithDefault(Locale locale, String key, String defaultMessage, Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            return defaultMessage;
        } else {
            return StringUtils.format(defaultMessage, args);
        }
    }

    @Override
    public String getMessageWithDefault(Locale locale, String key, String defaultMessage, Map<String, Object> args) {
        if (MapUtils.isEmpty(args)) {
            return defaultMessage;
        } else {
            return StringUtils.format(defaultMessage, args);
        }
    }
}
