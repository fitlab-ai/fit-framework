// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.maven.compile.parser.weather;

import org.fitframework.fel.tool.annotation.Group;
import org.fitframework.fel.tool.annotation.ToolMethod;
import org.fitframework.annotation.Genericable;

/**
 * 添加测试用的工具的定义。
 *
 * @author 曹嘉美
 * @author 杭潇
 * @since 2024-10-24
 */
@Group(name = "defGroup_weather_Wind")
public interface Wind {
    /**
     * 获取今日的风量数据。
     *
     * @param location 表示获取风量数据地址的 {@link String}。
     * @return 表示今日风量数据的 {@link String}。
     */
    @ToolMethod(name = "wind_today", description = "获取今日的风量数据")
    @Genericable("weather_rain_today")
    String today(String location);

    /**
     * 获取明日的风量数据。
     *
     * @param location 表示获取风量数据地址的 {@link String}。
     * @return 表示明日风量数据的 {@link String}。
     */
    @ToolMethod(name = "wind_tomorrow", description = "获取明日的风量数据")
    @Genericable("weather_rain_tomorrow")
    String tomorrow(String location);
}
