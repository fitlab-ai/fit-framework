// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.validation.data;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 公司实体类。
 *
 * @author 易文渊
 * @author 阮睿
 * @since 2024-09-27
 */
public class Company {
    @NotNull
    @Valid
    private List<Employee> employees;

    /**
     * 默认构造函数。
     */
    public Company() {}

    /**
     * 构造函数。
     *
     * @param employees 表示雇员列表的 {@link List}{@code <}{@link Employee}{@code >}。
     */
    public Company(List<Employee> employees) {
        this.employees = employees;
    }
}