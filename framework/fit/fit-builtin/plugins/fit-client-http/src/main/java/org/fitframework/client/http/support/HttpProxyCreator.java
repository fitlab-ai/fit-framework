// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.client.http.support;

import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.http.annotation.HttpProxy;
import org.fitframework.http.client.HttpClassicClientFactory;
import org.fitframework.http.client.proxy.scanner.AnnotationParser;
import org.fitframework.http.client.proxy.scanner.HttpInvocationHandler;
import org.fitframework.http.client.proxy.scanner.entity.HttpInfo;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Order;
import org.fitframework.conf.Config;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.lifecycle.container.BeanContainerInitializedObserver;
import org.fitframework.jvm.scan.PackageScanner;
import org.fitframework.plugin.Plugin;
import org.fitframework.util.StringUtils;
import org.fitframework.value.ValueFetcher;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Creates HTTP proxy objects for interfaces annotated with {@link HttpProxy}.
 * This class implements the {@link BeanContainerInitializedObserver} interface and is responsible for
 * scanning packages for interfaces annotated with {@link HttpProxy}, parsing their annotations,
 * and creating proxy objects that can be used to make HTTP requests.
 *
 * @author 王攀博
 * @author 季聿阶
 * @since 2025-05-31
 */
@Component
@Order(Order.NEARLY_HIGH)
public class HttpProxyCreator implements BeanContainerInitializedObserver {
    private static final String CONFIG_PREFIX = "http.client.interface.package";

    private final HttpClassicClientFactory factory;
    private final ValueFetcher valueFetcher;

    /**
     * Constructs an HttpProxyCreator with the specified HTTP client factory and value fetcher.
     *
     * @param factory The HTTP client factory used to create HTTP clients.
     * @param valueFetcher The value fetcher used to fetch values for property setters.
     */
    public HttpProxyCreator(HttpClassicClientFactory factory, ValueFetcher valueFetcher) {
        this.factory = notNull(factory, "The http classic client factory cannot be null.");
        this.valueFetcher = notNull(valueFetcher, "The value fetcher cannot be null.");
    }

    @Override
    public void onBeanContainerInitialized(BeanContainer container) {
        Config config = container.beans().get(Config.class);
        List<String> packages = this.packages(config);
        if (packages.isEmpty()) {
            return;
        }
        List<Class<?>> classes = this.scan(container, packages);
        for (Class<?> clazz : classes) {
            AnnotationParser annotationParser = new AnnotationParser(this.valueFetcher, container);
            Map<Method, HttpInfo> httpInfoMap = annotationParser.parseInterface(clazz);
            // Scan all interfaces, create proxy objects for each, and register them in the container.
            container.registry()
                    .register(Proxy.newProxyInstance(clazz.getClassLoader(),
                            new Class[] {clazz},
                            new HttpInvocationHandler(httpInfoMap, container, this.factory)));
        }
    }

    private List<Class<?>> scan(BeanContainer container, List<String> packages) {
        List<Class<?>> interfaceClasses = new ArrayList<>();
        if (packages != null) {
            Plugin plugin = container.beans().get(Plugin.class);
            PackageScanner.forClassLoader(plugin.pluginClassLoader(), (scanner, clazz) -> {
                if (clazz.isInterface() && clazz.isAnnotationPresent(HttpProxy.class)) {
                    interfaceClasses.add(clazz);
                }
            }).scan(packages);
        }
        return interfaceClasses;
    }

    private List<String> packages(Config config) {
        String value = config.get(CONFIG_PREFIX, String.class);
        if (StringUtils.isNotBlank(value)) {
            return StringUtils.splitToList(value, ",");
        }
        return new ArrayList<>();
    }
}