// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.server.handler;

import static org.fitframework.util.ObjectUtils.getIfNull;
import static org.fitframework.util.ObjectUtils.nullIf;

import org.fitframework.http.annotation.DocumentIgnored;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.RequestQuery;
import org.fitframework.http.entity.FileEntity;
import org.fitframework.http.server.HttpClassicServerRequest;
import org.fitframework.http.server.HttpClassicServerResponse;
import org.fitframework.http.server.ResourceNotFoundException;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Value;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginStartedObserver;
import org.fitframework.plugin.PluginStoppingObserver;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;
import org.fitframework.util.TypeUtils;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 资源类处理器的配置类。
 *
 * @author 邬涨财
 * @author 季聿阶
 * @since 2023-08-07
 */
@Component
public class StaticResourceHttpHandler implements PluginStartedObserver, PluginStoppingObserver {
    private final String contextPath;
    private final List<String> customFileLocations;
    private final Map<ClassLoader, List<String>> staticLocationsMapping = new HashMap<>();
    private final FileHttpResolver fileHttpResolver = new FileHttpResolver();
    private final ResourceHttpResolver resourceHttpResolver = new ResourceHttpResolver();
    private final BeanContainer beanContainer;

    public StaticResourceHttpHandler(@Value("${server.http.context-path}") String contextPath,
            @Value("${server.http.file-locations}") List<String> customFileLocations, BeanContainer beanContainer) {
        this.contextPath = nullIf(contextPath, StringUtils.EMPTY);
        this.beanContainer = beanContainer;
        this.customFileLocations = getIfNull(customFileLocations, Collections::emptyList);
    }

    @Override
    public void onPluginStarted(Plugin plugin) {
        ClassLoader pluginClassLoader = plugin.pluginClassLoader();
        List<String> locations = ObjectUtils.cast(plugin.config()
                .get("server.http.resources.file-locations",
                        TypeUtils.parameterized(List.class, new Type[] {String.class})));
        if (CollectionUtils.isNotEmpty(locations)) {
            this.staticLocationsMapping.put(pluginClassLoader, locations);
        }
    }

    @Override
    public void onPluginStopping(Plugin plugin) {
        this.staticLocationsMapping.remove(plugin.pluginClassLoader());
    }

    /**
     * 表示资源访问的处理。
     *
     * @param positionName 表示文件消息体数据的显示位置名的 {@link String}。
     * @param request 表示服务端的 Http 请求的 {@link HttpClassicServerRequest}。
     * @param response 表示服务端的 Http 响应的 {@link HttpClassicServerResponse}。
     * @return 表示需要访问的资源的消息体数据的 {@link FileEntity}。
     */
    @DocumentIgnored
    @GetMapping(path = "/**/*.*")
    public FileEntity handle(
            @RequestQuery(name = "position", required = false, defaultValue = "inline") String positionName,
            HttpClassicServerRequest request, HttpClassicServerResponse response) {
        List<BeanFactory> beanFactories = this.beanContainer.all(CustomResourceHandler.class);
        for (BeanFactory beanFactory : beanFactories) {
            CustomResourceHandler handler = beanFactory.get();
            if (!handler.canHandle(positionName, request)) {
                continue;
            }
            return handler.handle(positionName, request, response);
        }
        FileEntity.Position position = FileEntity.Position.from(positionName);
        String path = this.getResourcePath(request);
        return this.getFromClassLoaders(path, response, position)
                .orElseThrow(() -> new ResourceNotFoundException(StringUtils.format("Resource not found. [path={0}]",
                        path)));
    }

    private String getResourcePath(HttpClassicServerRequest request) {
        if (StringUtils.startsWithIgnoreCase(request.path(), this.contextPath)) {
            return request.path().substring(this.contextPath.length());
        } else {
            return request.path();
        }
    }

    private Optional<FileEntity> getFromClassLoaders(String path, HttpClassicServerResponse response,
            FileEntity.Position position) {
        Optional<FileEntity> fileEntityOptional =
                this.fileHttpResolver.getFileEntity(path, response, position, this.customFileLocations, null);
        if (fileEntityOptional.isPresent()) {
            return fileEntityOptional;
        }
        return this.staticLocationsMapping.entrySet()
                .stream()
                .map(entry -> this.resourceHttpResolver.getFileEntity(path,
                        response,
                        position,
                        entry.getValue(),
                        entry.getKey()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
