// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.broker.Endpoint;
import org.fitframework.broker.Format;
import org.fitframework.util.StringUtils;

import java.util.Objects;

/**
 * 表示 {@link Format} 的默认实现。
 *
 * @author 季聿阶
 * @since 2024-01-22
 */
public class DefaultFormat implements Format {
    private final String name;
    private final int code;

    private DefaultFormat(String name, int code) {
        this.name = StringUtils.toLowerCase(nullIf(name, StringUtils.EMPTY));
        this.code = code;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (another == null || this.getClass() != another.getClass()) {
            return false;
        }
        DefaultFormat that = (DefaultFormat) another;
        return this.code == that.code && Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.code);
    }

    @Override
    public String toString() {
        return StringUtils.format("/{\"formatName\": {0}, \"formatCode\": {1}/}", this.name, this.code);
    }

    /**
     * 表示 {@link Format.Builder} 的默认实现。
     */
    public static class Builder implements Format.Builder {
        private String name;
        private int code;

        /**
         * 使用已知的序列化方式初始化 {@link DefaultFormat.Builder} 类的新实例。
         *
         * @param value 表示已知的序列化方式的 {@link Endpoint}。
         */
        public Builder(Format value) {
            if (value != null) {
                this.name = value.name();
                this.code = value.code();
            }
        }

        @Override
        public Format.Builder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public Format.Builder code(int code) {
            this.code = code;
            return this;
        }

        @Override
        public Format build() {
            return new DefaultFormat(this.name, this.code);
        }
    }
}
