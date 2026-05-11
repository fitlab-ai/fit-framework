// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.info.entity;

/**
 * 表示方法返回的参数的属性类。
 *
 * @author 曹嘉美
 * @author 李金绪
 * @since 2024-10-26
 */
public class ReturnPropertyEntity extends PropertyEntity {
    private String convertor;

    /**
     * 获取转换器的名称。
     *
     * @return 表示转换器的名称的 {@link String}。
     */
    public String getConvertor() {
        return this.convertor;
    }

    /**
     * 设置转换器的名称。
     *
     * @param convertor 表示转换器的名称的 {@link String}。
     */
    public void setConvertor(String convertor) {
        this.convertor = convertor;
    }
}
