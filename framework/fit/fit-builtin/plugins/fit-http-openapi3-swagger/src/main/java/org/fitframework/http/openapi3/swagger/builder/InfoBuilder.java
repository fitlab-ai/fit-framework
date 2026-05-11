// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.openapi3.swagger.builder;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.openapi3.swagger.EntityBuilder;
import org.fitframework.http.openapi3.swagger.entity.Info;
import org.fitframework.conf.runtime.ApplicationConfig;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;

/**
 * 表示 {@link Info} 的构建器。
 *
 * @author 季聿阶
 * @since 2023-08-23
 */
public class InfoBuilder implements EntityBuilder<Info> {
    private static final String FIT_VERSION = "3.7.0-SNAPSHOT";
    private static final String SWAGGER_UI_VERSION = "v5.31.0";

    private final ApplicationConfig applicationConfig;

    InfoBuilder(BeanContainer container) {
        this.applicationConfig =
                notNull(container, "The bean container cannot be null.").lookup(ApplicationConfig.class)
                        .map(BeanFactory::get)
                        .map(ApplicationConfig.class::cast)
                        .orElseThrow(() -> new IllegalStateException("The application config not found."));
    }

    @Override
    public Info build() {
        return Info.custom()
                .title("OpenAPI 3.0 for " + this.applicationConfig.name())
                .summary("该文档由 FIT for Java 进行构建")
                .description("- 默认显示的 `OpenAPI` 文档地址为 `/v3/openapi`，如果需要修改，可以在顶端搜索栏自定义修改。\n"
                        + "- 如果需要去除某一个 `API` 的文档显示，可以在对应的方法上增加 `@DocumentIgnored` 注解。")
                .version("FIT:" + FIT_VERSION + " Swagger-UI:" + SWAGGER_UI_VERSION)
                .build();
    }
}
