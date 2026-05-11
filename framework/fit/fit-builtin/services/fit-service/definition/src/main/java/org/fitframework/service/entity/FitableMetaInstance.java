// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service.entity;

import static org.fitframework.util.ObjectUtils.getIfNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示服务实现元数据的实例。
 *
 * @author 季聿阶
 * @since 2023-05-17
 */
public class FitableMetaInstance {
    private FitableMeta meta;
    private List<String> environments = new ArrayList<>();

    /**
     * 获取服务实现元数据信息。
     *
     * @return 表示服务实现元数据信息的 {@link FitableMeta}。
     */
    public FitableMeta getMeta() {
        return this.meta;
    }

    /**
     * 设置服务实现元数据信息。
     *
     * @param meta 表示待设置的服务实现元数据信息的 {@link FitableMeta}。
     */
    public void setMeta(FitableMeta meta) {
        this.meta = meta;
    }

    /**
     * 获取服务实现运行的环境列表。
     *
     * @return 表示服务实现运行的环境列表 {@link List}{@code <}{@link String}{@code >}。
     */
    public List<String> getEnvironments() {
        return this.environments;
    }

    /**
     * 设置服务实现运行的环境列表。
     *
     * @param environments 表示服务实现运行的环境列表 {@link List}{@code <}{@link String}{@code >}。
     */
    public void setEnvironments(List<String> environments) {
        this.environments = getIfNull(environments, ArrayList::new);
    }
}
