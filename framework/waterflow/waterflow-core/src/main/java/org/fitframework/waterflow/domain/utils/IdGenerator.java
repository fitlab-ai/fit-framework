// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * ID生成器抽象类
 *
 * @author 高诗意
 * @since 1.0
 */
public abstract class IdGenerator implements Identity {
    /**
     * id
     */
    @Getter
    @Setter
    protected String id;

    /**
     * 构造函数
     */
    public IdGenerator() {
        this(UUIDUtil.uuid());
    }

    /**
     * 构造函数
     *
     * @param id ID
     */
    public IdGenerator(String id) {
        this.id = id;
    }
}
