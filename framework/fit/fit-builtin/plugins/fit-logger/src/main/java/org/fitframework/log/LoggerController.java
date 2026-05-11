// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.log;

import org.fitframework.http.annotation.DocumentIgnored;
import org.fitframework.http.annotation.PutMapping;
import org.fitframework.http.annotation.RequestForm;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.http.annotation.RequestQuery;
import org.fitframework.http.annotation.ResponseStatus;
import org.fitframework.http.protocol.HttpResponseStatus;
import org.fitframework.log.support.FitChanger;
import org.fitframework.log.support.Log4j2Changer;
import org.fitframework.annotation.Component;
import org.fitframework.log.Logger;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.util.MapBuilder;

import java.util.Map;

/**
 * 表示日志级别的动态设置器。
 *
 * @author 季聿阶
 * @since 2023-12-22
 */
@DocumentIgnored
@RequestMapping(path = "/loggers")
@Component
public class LoggerController {
    private static final Logger log = Logger.get(LoggerController.class);

    private final Map<LoggerType, LoggerLevelChanger> changers;

    public LoggerController(FitRuntime runtime) {
        this.changers = MapBuilder.<LoggerType, LoggerLevelChanger>get()
                .put(LoggerType.FIT, FitChanger.INSTANCE)
                .put(LoggerType.LOG4J2, new Log4j2Changer(runtime))
                .build();
    }

    /**
     * 将指定日志系统的日志级别调整为指定值。
     *
     * @param type 表示待调整日志系统的 {@link String}。
     * @param pluginName 表示待调整的日志记录器所在的插件的 {@link String}。
     * @param packageName 表示待调整日志记录器的包路径的 {@link String}。
     * @param name 表示待调整日志记录器的名字的 {@link String}。
     * @param level 表示调整到的日志级别的 {@link String}。
     */
    @PutMapping(path = "/levels")
    @ResponseStatus(code = HttpResponseStatus.NO_CONTENT)
    public void changeLevel(@RequestQuery(name = "type", defaultValue = "fit") String type,
            @RequestQuery(name = "plugin") String pluginName,
            @RequestQuery(name = "package", defaultValue = "") String packageName,
            @RequestQuery(name = "name", defaultValue = "") String name, @RequestForm(name = "level") String level) {
        LoggerType loggerType = LoggerType.from(type);
        this.changers.get(loggerType).changeLevel(pluginName, packageName, name, level);
        log.info("Change logger level successfully. [type={}, plugin={}, package={}, name={}, level={}]",
                type,
                pluginName,
                packageName,
                name,
                level);
    }
}
