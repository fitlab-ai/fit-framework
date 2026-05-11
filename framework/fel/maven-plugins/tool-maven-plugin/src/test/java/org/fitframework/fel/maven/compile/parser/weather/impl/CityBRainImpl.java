// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.maven.compile.parser.weather.impl;

import org.fitframework.fel.maven.compile.parser.weather.dto.RainPosition;
import org.fitframework.fel.tool.annotation.Attribute;
import org.fitframework.fel.tool.annotation.Group;
import org.fitframework.fel.tool.annotation.ToolMethod;
import org.fitframework.annotation.Fitable;
import org.fitframework.fel.maven.compile.parser.weather.Rain;

import java.util.Date;

/**
 * 添加测试用的工具的实现。
 *
 * @author 杭潇
 * @author 曹嘉美
 * @since 2024-10-26
 */
@Group(name = "implGroup_weather_rain_city_b")
public class CityBRainImpl implements Rain {
    private static final String FITABLE_ID = "weather_rain_city_b";

    @Fitable(FITABLE_ID)
    @ToolMethod(name = "city_b_rain_today", description = "城市B提供的今日下雨信息", extensions = {
            @Attribute(key = "tags", value = "FIT"), @Attribute(key = "tags", value = "TEST")
    })
    @Override
    public String today(String location, Date date, RainPosition rainPosition, Object info) {
        return null;
    }

    @Fitable(FITABLE_ID)
    @ToolMethod(name = "city_b_rain_tomorrow", description = "城市B提供的明日下雨信息", extensions = {
            @Attribute(key = "tags", value = "FIT"), @Attribute(key = "tags", value = "TEST")
    })
    @Override
    public String tomorrow(String location, Date date) {
        return null;
    }
}
