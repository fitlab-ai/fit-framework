// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.validation.data;

import org.fitframework.http.annotation.PostMapping;
import org.fitframework.http.annotation.RequestBody;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.annotation.Component;
import org.fitframework.validation.LocaleContextMessageInterpolator;
import org.fitframework.validation.Validated;
import org.fitframework.validation.ValidationHandler;

import javax.validation.Valid;

/**
 * 用于测试 {@link ValidationHandler} 与 {@link LocaleContextMessageInterpolator} 的集成地区验证控制器。
 *
 * @author 阮睿
 * @since 2025-08-01
 */
@Component
@RequestMapping(path = "/validation/locale", group = "地区验证测试接口")
@Validated
public class LocaleValidationController {
    /**
     * 使用简单参数测试验证消息的地区化。
     *
     * @param company 表示注解验证的测试实体类 {@link Company}。
     */
    @PostMapping(path = "/simple", description = "测试简单参数的地区化验证消息")
    public void validateSimpleParam(@RequestBody @Valid Company company) {}
}