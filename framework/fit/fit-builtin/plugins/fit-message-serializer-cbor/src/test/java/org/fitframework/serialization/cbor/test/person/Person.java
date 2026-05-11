// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.cbor.test.person;

import lombok.Data;

import java.util.List;

/**
 * 为单元测试提供人的信息定义。
 *
 * @author 梁济时
 * @since 2020-11-23
 */
@Data
public class Person {
    private PersonName name;
    private List<String> inventions;
}
