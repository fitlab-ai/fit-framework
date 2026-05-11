// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.service.entity;

import static org.fitframework.util.ObjectUtils.getIfNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示应用实例信息。
 *
 * @author 季聿阶
 * @since 2023-05-07
 */
public class ApplicationInstance {
    private List<Worker> workers = new ArrayList<>();
    private Application application;
    private List<Integer> formats = new ArrayList<>();

    /**
     * 获取应用实例的所有进程列表。
     *
     * @return 表示应用实例的所有进程列表的 {@link List}{@code <}{@link Worker}{@code >}。
     */
    public List<Worker> getWorkers() {
        return this.workers;
    }

    /**
     * 设置应用实例的所有进程列表。
     *
     * @param workers 表示应用实例的所有进程列表的 {@link List}{@code <}{@link Worker}{@code >}。
     */
    public void setWorkers(List<Worker> workers) {
        this.workers = getIfNull(workers, ArrayList::new);
    }

    /**
     * 获取应用信息。
     *
     * @return 表示应用信息的 {@link Application}。
     */
    public Application getApplication() {
        return this.application;
    }

    /**
     * 设置应用信息。
     *
     * @param application 表示应用信息的 {@link Application}。
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * 获取应用实例支持的所有序列化方式。
     *
     * @return 表示应用实例支持的所有序列化方式的 {@link List}{@code <}{@link Integer}{@code >}。
     */
    public List<Integer> getFormats() {
        return this.formats;
    }

    /**
     * 设置应用实例支持的所有序列化方式。
     *
     * @param formats 表示应用实例支持的所有序列化方式的 {@link List}{@code <}{@link Integer}{@code >}。
     */
    public void setFormats(List<Integer> formats) {
        this.formats = getIfNull(formats, ArrayList::new);
    }
}
