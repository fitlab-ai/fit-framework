// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.domain.mvc;

import org.fitframework.http.annotation.GetMapping;
import org.fitframework.annotation.Component;

/**
 * 为测试提供模拟的控制器，用于检测服务是否启动成功。
 *
 * @author 季聿阶
 * @since 2024-09-23
 */
@Component(name = "$FIT$TestFramework$MockController")
public class MockController {
    /** 表示测试的路径。 */
    public static final String PATH = "/fit/test/framework/mock";

    /** 表示测试的正确返回值。 */
    public static final String OK = "OK";

    /**
     * 测试接口。
     *
     * @return 表示测试结果的 {@link String}。
     */
    @GetMapping(path = PATH)
    public String test() {
        return OK;
    }
}
