// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.datasource.support;

import static org.fitframework.inspection.Validation.notBlank;

import org.fitframework.conf.Config;
import org.fitframework.datasource.AccessMode;
import org.fitframework.util.EnumUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import java.util.function.Predicate;

/**
 * 数据源配置类。
 *
 * @author 易文渊
 * @author 李金绪
 * @since 2024-07-27
 */
public class FitDataSourceConfig {
    /**
     * 表示数据源实例的前缀。
     */
    public static final String INSTANCE_PREFIX = "fit.datasource.instances.";

    /**
     * 表示数据源的模式。
     */
    public static final String PRIMARY_MODE = "mode";

    /**
     * 表示分隔符。
     */
    public static final String SEPARATOR = ".";
    private static final String PRIMARY_PREFIX = "fit.datasource.primary.";

    private String name;
    private AccessMode mode;

    /**
     * 创建数据源配置对象。
     *
     * @param config 表示配置的 {@link Config}。
     * @return 表示数据源配置的 {@link FitDataSourceConfig}。
     */
    public static FitDataSourceConfig create(Config config) {
        String primaryName = config.get(PRIMARY_PREFIX, String.class);
        notBlank(primaryName, "The primary data source is not configured.");
        String primaryMode =
                config.get(INSTANCE_PREFIX + primaryName + SEPARATOR + PRIMARY_MODE + SEPARATOR, String.class);
        notBlank(primaryMode, "The primary data source mode is not configured.");
        FitDataSourceConfig fitConfig = new FitDataSourceConfig();
        fitConfig.setName(primaryName);
        fitConfig.setMode(ObjectUtils.cast(toEnum(AccessMode.class, primaryMode)));
        return fitConfig;
    }

    /**
     * 获取数据源名称。
     *
     * @return 表示数据源名称的 {@link String}。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置数据源名称。
     *
     * @param name 表示数据源名称的 {@link String}。
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取数据源访问模式。
     *
     * @return 表示数据源访问模式的 {@link AccessMode}。
     */
    public AccessMode getMode() {
        return this.mode;
    }

    /**
     * 设置数据源访问模式。
     *
     * @param mode 表示数据源访问模式的 {@link AccessMode}。
     */
    public void setMode(AccessMode mode) {
        this.mode = mode;
    }

    private static Object toEnum(Class<?> enumClass, String value) {
        Class<? extends Enum<?>> actualClass = ObjectUtils.cast(enumClass);
        Predicate<Enum> predicate = enumConstant -> StringUtils.equalsIgnoreCase(enumConstant.toString(), value);
        return EnumUtils.firstOrDefault(ObjectUtils.cast(actualClass), predicate);
    }
}
