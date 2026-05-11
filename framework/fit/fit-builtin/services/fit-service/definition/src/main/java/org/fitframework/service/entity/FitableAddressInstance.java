// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service.entity;

import static org.fitframework.util.ObjectUtils.getIfNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示服务实现的实例信息。
 *
 * @author 季聿阶
 * @since 2023-05-07
 */
public class FitableAddressInstance {
    private FitableInfo fitable;
    private List<ApplicationInstance> applicationInstances = new ArrayList<>();

    /**
     * 获取服务实现实例所属的服务实现信息。
     *
     * @return 表示服务实现实例所属的服务实现信息的 {@link FitableInfo}。
     */
    public FitableInfo getFitable() {
        return this.fitable;
    }

    /**
     * 设置服务实现实例所属的服务实现信息。
     *
     * @param fitable 表示服务实现实例所属的服务实现信息的 {@link FitableInfo}。
     */
    public void setFitable(FitableInfo fitable) {
        this.fitable = fitable;
    }

    /**
     * 获取服务实现实例所属的应用实例的列表。
     *
     * @return 表示服务实现实例所属的应用实例的列表的 {@link List}{@code <}{@link ApplicationInstance}{@code >}。
     */
    public List<ApplicationInstance> getApplicationInstances() {
        return this.applicationInstances;
    }

    /**
     * 设置服务实现实例所属的应用实例的列表。
     *
     * @param applicationInstances 表示服务实现实例所属的应用实例的列表的 {@link List}{@code <}{@link
     * ApplicationInstance}{@code >}。
     */
    public void setApplicationInstances(List<ApplicationInstance> applicationInstances) {
        this.applicationInstances = getIfNull(applicationInstances, ArrayList::new);
    }
}
