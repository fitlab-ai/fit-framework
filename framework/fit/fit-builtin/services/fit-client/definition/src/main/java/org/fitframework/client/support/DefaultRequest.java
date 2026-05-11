// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.support;

import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.client.Address;
import org.fitframework.client.Request;
import org.fitframework.client.RequestContext;
import org.fitframework.serialization.RequestMetadata;
import org.fitframework.util.ObjectUtils;

import java.lang.reflect.Type;

/**
 * 表示 {@link Request} 的默认实现。
 *
 * @author 季聿阶
 * @since 2022-09-19
 */
public class DefaultRequest implements Request {
    private final String protocol;
    private final Address address;
    private final RequestMetadata metadata;
    private final Type[] dataTypes;
    private final Object[] data;
    private final Type returnType;
    private final RequestContext context;

    private DefaultRequest(String protocol, Address address, RequestMetadata metadata, Type[] dataTypes, Object[] data,
            Type returnType, RequestContext context) {
        this.protocol = notBlank(protocol, "The protocol cannot be blank.");
        this.address = notNull(address, "The address cannot be null.");
        this.metadata = notNull(metadata, "The metadata cannot be null.");
        this.dataTypes = ObjectUtils.getIfNull(dataTypes, () -> new Type[0]);
        this.data = notNull(data, "The data cannot be null.");
        this.returnType = returnType;
        this.context = notNull(context, "The request context cannot be null.");
    }

    @Override
    public String protocol() {
        return this.protocol;
    }

    @Override
    public Address address() {
        return this.address;
    }

    @Override
    public RequestMetadata metadata() {
        return this.metadata;
    }

    @Override
    public Type[] dataTypes() {
        return this.dataTypes;
    }

    @Override
    public Object[] data() {
        return this.data;
    }

    @Override
    public Type returnType() {
        return this.returnType;
    }

    @Override
    public RequestContext context() {
        return this.context;
    }

    /**
     * 表示 {@link Request.Builder} 的默认实现。
     */
    public static class Builder implements Request.Builder {
        private String protocol;
        private Address address;
        private RequestMetadata metadata;
        private Type[] dataTypes;
        private Object[] data;
        private Type returnType;
        private RequestContext context;

        /**
         * 使用已知的请求对象初始化 {@link Builder} 类的新实例。
         *
         * @param request 表示已知的请求对象的 {@link Request}。
         */
        public Builder(Request request) {
            if (request != null) {
                this.protocol = request.protocol();
                this.address = request.address();
                this.metadata = request.metadata();
                this.dataTypes = request.dataTypes();
                this.data = request.data();
                this.returnType = request.returnType();
                this.context = request.context();
            }
        }

        @Override
        public Request.Builder protocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        @Override
        public Request.Builder address(Address address) {
            this.address = address;
            return this;
        }

        @Override
        public Request.Builder metadata(RequestMetadata metadata) {
            this.metadata = metadata;
            return this;
        }

        @Override
        public Request.Builder dataTypes(Type[] types) {
            this.dataTypes = types;
            return this;
        }

        @Override
        public Request.Builder data(Object[] data) {
            this.data = data;
            return this;
        }

        @Override
        public Request.Builder returnType(Type type) {
            this.returnType = type;
            return this;
        }

        @Override
        public Request.Builder context(RequestContext context) {
            this.context = context;
            return this;
        }

        @Override
        public Request build() {
            return new DefaultRequest(this.protocol,
                    this.address,
                    this.metadata,
                    this.dataTypes,
                    this.data,
                    this.returnType,
                    this.context);
        }
    }
}
