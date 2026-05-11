// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.support;

import static org.fitframework.inspection.Validation.between;
import static org.fitframework.inspection.Validation.notNull;
import static org.fitframework.util.ObjectUtils.getIfNull;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.serialization.ByteSerializer;
import org.fitframework.serialization.CommunicationVersion;
import org.fitframework.serialization.ResponseMetadata;
import org.fitframework.serialization.TagLengthValues;
import org.fitframework.util.Convert;
import org.fitframework.util.IoUtils;
import org.fitframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * 为 {@link ResponseMetadata} 提供默认实现。
 *
 * @author 季聿阶
 * @since 2021-05-14
 */
public class DefaultResponseMetadata implements ResponseMetadata {
    private final int dataFormat;
    private final int code;
    private final boolean isDegradable;
    private final boolean isRetryable;
    private final String message;
    private final TagLengthValues tagValues;

    private DefaultResponseMetadata(int dataFormat, int code, boolean isDegradable, boolean isRetryable, String message,
            TagLengthValues tagLengthValues) {
        this.dataFormat =
                between(dataFormat, -1, 0x7F, "The data format is out of range. [dataFormat={0}]", dataFormat);
        this.code = code;
        this.isDegradable = isDegradable;
        this.isRetryable = isRetryable;
        this.message = nullIf(message, StringUtils.EMPTY);
        this.tagValues = getIfNull(tagLengthValues, TagLengthValues::create);
    }

    @Override
    public byte dataFormatByte() {
        return (byte) this.dataFormat;
    }

    @Override
    public int dataFormat() {
        return this.dataFormat;
    }

    @Override
    public int code() {
        return this.code;
    }

    @Override
    public boolean isDegradable() {
        return this.isDegradable;
    }

    @Override
    public boolean isRetryable() {
        return this.isRetryable;
    }

    @Override
    public String message() {
        return this.message;
    }

    @Override
    public TagLengthValues tagValues() {
        return this.tagValues;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.serialize());
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof ResponseMetadata) {
            return this.hashCode() == another.hashCode();
        }
        return false;
    }

    @Override
    public String toString() {
        return "{\"dataFormat\": " + this.dataFormat + ", \"code\": " + this.code + ", \"isDegradable\": "
                + this.isDegradable + ", \"isRetryable\": " + this.isRetryable + ", \"message\": \"" + this.message
                + "\", \"tagValues\": " + this.tagValues + '}';
    }

    /**
     * 为创建 {@link DefaultResponseMetadata} 的实例提供构建器。
     *
     * @author 季聿阶
     * @since 2021-05-14
     */
    public static final class Builder implements ResponseMetadata.Builder {
        private int dataFormat;
        private int code;
        private boolean isDegradable;
        private boolean isRetryable;
        private String message;
        private TagLengthValues tagValues;

        /**
         * 使用已知的远端调用元数据初始化 {@link Builder} 类的新实例。
         *
         * @param metadata 表示已知的远端调用的元数据的 {@link ResponseMetadata}。构建器中会以该实例中的现有数据作为初始值。
         */
        public Builder(ResponseMetadata metadata) {
            if (metadata != null) {
                this.dataFormat = metadata.dataFormat();
                this.code = metadata.code();
                this.isDegradable = metadata.isDegradable();
                this.isRetryable = metadata.isRetryable();
                this.message = metadata.message();
                this.tagValues = metadata.tagValues();
            }
            if (this.tagValues == null) {
                this.tagValues = TagLengthValues.create();
            }
        }

        @Override
        public Builder dataFormat(int dataFormat) {
            this.dataFormat = dataFormat;
            return this;
        }

        @Override
        public Builder code(int code) {
            this.code = code;
            return this;
        }

        @Override
        public ResponseMetadata.Builder isDegradable(boolean isDegradable) {
            this.isDegradable = isDegradable;
            return this;
        }

        @Override
        public ResponseMetadata.Builder isRetryable(boolean isRetryable) {
            this.isRetryable = isRetryable;
            return this;
        }

        @Override
        public Builder message(String message) {
            this.message = message;
            return this;
        }

        @Override
        public Builder tagValues(TagLengthValues tagValues) {
            if (tagValues != null) {
                this.tagValues = tagValues;
            } else {
                this.tagValues = TagLengthValues.create();
            }
            return this;
        }

        @Override
        public ResponseMetadata build() {
            return new DefaultResponseMetadata(this.dataFormat,
                    this.code,
                    this.isDegradable,
                    this.isRetryable,
                    this.message,
                    this.tagValues);
        }
    }

    /**
     * 为 {@link ResponseMetadata} 提供序列化程序。
     *
     * @author 季聿阶
     * @since 2021-05-14
     */
    public static class Serializer implements ByteSerializer<ResponseMetadata> {
        /** 获取序列化程序的唯一实例。 */
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public void serialize(ResponseMetadata metadata, OutputStream out) throws IOException {
            CommunicationVersion version =
                    notNull(CommunicationVersion.latest().peek(), "No supported communication version.");
            out.write(Convert.toBytes(version.supported()));
            version.serializeResponseMetadata(metadata, out);
        }

        @Override
        public ResponseMetadata deserialize(InputStream in) throws IOException {
            short versionNum = Convert.toShort(IoUtils.read(in, 2));
            CommunicationVersion version = CommunicationVersion.choose(versionNum)
                    .orElseThrow(() -> new IllegalStateException(StringUtils.format(
                            "No supported communication version. [num={0}]",
                            versionNum)));
            return version.deserializeResponseMetadata(in);
        }
    }
}
