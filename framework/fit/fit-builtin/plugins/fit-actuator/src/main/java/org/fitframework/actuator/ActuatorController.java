// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2026 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.actuator;

import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.actuator.entity.AddressVo;
import org.fitframework.actuator.entity.EndpointVo;
import org.fitframework.actuator.entity.FitableVo;
import org.fitframework.actuator.entity.FormatVo;
import org.fitframework.actuator.entity.PluginVo;
import org.fitframework.http.annotation.DocumentIgnored;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.http.annotation.RequestQuery;
import org.fitframework.annotation.Component;
import org.fitframework.broker.Endpoint;
import org.fitframework.broker.Fitable;
import org.fitframework.broker.Format;
import org.fitframework.broker.Genericable;
import org.fitframework.broker.Target;
import org.fitframework.broker.client.BrokerClient;
import org.fitframework.broker.client.filter.route.FitableIdFilter;
import org.fitframework.conf.Config;
import org.fitframework.conf.ConfigValueSupplier;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginComparators;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示监控的控制器。
 *
 * @author 季聿阶
 * @since 2024-07-04
 */
@DocumentIgnored
@Component
@RequestMapping(path = "/actuator")
public class ActuatorController {
    private final FitRuntime fitRuntime;
    private final BrokerClient brokerClient;

    ActuatorController(FitRuntime fitRuntime, BrokerClient brokerClient) {
        this.fitRuntime = notNull(fitRuntime, "The FIT runtime cannot be null.");
        this.brokerClient = notNull(brokerClient, "The broker client cannot be null.");
    }

    /**
     * 健康检查端点。
     *
     * @return 表示健康状态的 {@link String}。
     */
    @GetMapping(path = "/health")
    public String health() {
        return "OK";
    }

    /**
     * 获取当前进程的所有的插件列表。
     *
     * @return 表示当前进程的所有插件列表的 {@link List}{@code <}{@link PluginVo}{@code >}。
     */
    @GetMapping(path = "/plugins")
    public List<PluginVo> getPlugins() {
        return this.fitRuntime.plugins()
                .stream()
                .sorted(PluginComparators.STARTUP)
                .map(this::convert)
                .collect(Collectors.toList());
    }

    /**
     * 获取指定插件的指定配置键的值。
     * <p>当指定键为空白字符串时，返回所有的键。</p>
     *
     * @param pluginName 表示指定插件名的 {@link String}，当插件名为空白字符串时，则返回整个运行时的配置值。
     * @param key 表示指定配置键的 {@link String}。
     * @return 表示指定配置键的值的 {@link Object}。
     */
    @GetMapping(path = "/configs")
    public Object getConfigs(@RequestQuery(name = "plugin", required = false) String pluginName,
            @RequestQuery(name = "key", required = false) String key) {
        Config config;
        if (StringUtils.isBlank(pluginName)) {
            config = this.fitRuntime.config();
        } else {
            config = this.fitRuntime.plugin(pluginName).orElse(this.fitRuntime.root()).config();
        }
        if (StringUtils.isBlank(key)) {
            return config.keys();
        }
        if (config instanceof ConfigValueSupplier) {
            return ((ConfigValueSupplier) config).get(key);
        }
        return config.get(key, Object.class);
    }

    /**
     * 获取指定服务的所有服务实现列表。
     *
     * @param genericableId 表示指定服务的唯一标识的 {@link String}。
     * @return 表示指定服务的所有服务实现列表的 {@link List}{@code <}{@link FitableVo}{@code >}。
     */
    @GetMapping(path = "/fitables")
    public List<FitableVo> getFitables(@RequestQuery(name = "genericableId") String genericableId) {
        notBlank(genericableId, "The genericable id cannot be blank.");
        Genericable genericable = this.brokerClient.getGenericable(genericableId);
        return genericable.fitables().stream().map(this::convert).collect(Collectors.toList());
    }

    /**
     * 获取指定服务实现的地址列表。
     *
     * @param genericableId 表示指定服务的唯一标识的 {@link String}。
     * @param fitableId 表示指定服务实现的唯一标识的 {@link String}。
     * @return 表示指定服务实现的地址列表的 {@link List}{@code <}{@link AddressVo}{@code >}。
     */
    @GetMapping(path = "/addresses")
    public List<AddressVo> getAddresses(@RequestQuery(name = "genericableId") String genericableId,
            @RequestQuery(name = "fitableId") String fitableId) {
        notBlank(genericableId, "The genericable id cannot be blank.");
        notBlank(fitableId, "The fitable id cannot be blank.");
        Genericable genericable =
                this.brokerClient.getRouter(genericableId).route(new FitableIdFilter(fitableId)).getGenericable();
        return genericable.fitable(fitableId, Fitable.DEFAULT_VERSION)
                .targets()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    private PluginVo convert(Plugin plugin) {
        PluginVo vo = new PluginVo();
        vo.setGroup(plugin.metadata().group());
        vo.setName(plugin.metadata().name());
        vo.setVersion(plugin.metadata().version());
        vo.setCategory(plugin.metadata().category().getCode());
        vo.setLevel(plugin.metadata().level());
        return vo;
    }

    private FitableVo convert(Fitable fitable) {
        FitableVo vo = new FitableVo();
        vo.setId(fitable.id());
        vo.setVersion(fitable.version());
        vo.setAliases(fitable.aliases().all());
        vo.setTags(fitable.tags().all());
        vo.setDegradation(fitable.degradationFitableId());
        return vo;
    }

    private AddressVo convert(Target target) {
        AddressVo vo = new AddressVo();
        vo.setWorkerId(target.workerId());
        vo.setHost(target.host());
        vo.setEnvironment(target.environment());
        vo.setFormats(target.formats().stream().map(this::convert).collect(Collectors.toList()));
        vo.setEndpoints(target.endpoints().stream().map(this::convert).collect(Collectors.toList()));
        vo.setExtensions(target.extensions());
        return vo;
    }

    private FormatVo convert(Format format) {
        FormatVo vo = new FormatVo();
        vo.setName(format.name());
        vo.setCode(format.code());
        return vo;
    }

    private EndpointVo convert(Endpoint endpoint) {
        EndpointVo vo = new EndpointVo();
        vo.setProtocol(endpoint.protocol());
        vo.setCode(endpoint.protocolCode());
        vo.setPort(endpoint.port());
        return vo;
    }
}
