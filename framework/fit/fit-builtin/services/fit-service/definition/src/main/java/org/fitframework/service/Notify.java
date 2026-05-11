// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service;

import org.fitframework.service.entity.FitableAddressInstance;
import org.fitframework.annotation.Genericable;

import java.util.List;

/**
 * Represents notification service for updating Fitables instance information.
 *
 * @author 董智豪
 * @since 2025-06-20
 */
public interface Notify {
    /**
     * Notify to update Fitables instances.
     *
     * @param fitableInstances A {@link List}{@code <}{@link FitableAddressInstance}{@code >} representing all instance
     * information for specified service implementations.
     */
    @Genericable(id = "org.fitframework.service.registry-listener.notify-fitables")
    void notifyFitables(List<FitableAddressInstance> fitableInstances);
}