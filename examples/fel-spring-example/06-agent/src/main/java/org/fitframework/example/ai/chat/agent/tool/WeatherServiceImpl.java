// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.ai.chat.agent.tool;

import org.fitframework.fel.tool.annotation.Attribute;
import org.fitframework.fel.tool.annotation.Group;
import org.fitframework.fel.tool.annotation.ToolMethod;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;
import org.fitframework.annotation.Property;

/**
 * 表示 {@link WeatherService} 的默认实现。
 *
 * @author 易文渊
 * @author 杭潇
 * @author 黄可欣
 * @since 2024-09-02
 */
@Component
@Group(name = "example")
public class WeatherServiceImpl implements WeatherService {
    @Override
    @Fitable("default")
    @ToolMethod(name = "get_current_temperature", description = "获取指定城市的当前温度",
            extensions = {
                    @Attribute(key = "tags", value = "FIT"), @Attribute(key = "tags", value = "TEST"),
                    @Attribute(key = "attribute", value = "nothing"),
                    @Attribute(key = "attribute", value = "nothing two")
            })
    @Property(description = "当前温度的结果")
    public String getCurrentTemperature(String location, String unit) {
        return "26";
    }

    @Override
    @Fitable("default")
    @ToolMethod(name = "get_rain_probability", description = "获取指定城市下雨的概率")
    @Property(description = "下雨的概率")
    public String getRainProbability(String location) {
        return "0.06";
    }
}