// SPDX-License-Identifier: MIT
// Copyright (c) 2026 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.test.domain.listener;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.fitframework.conf.Config;
import org.fitframework.event.EventPublisher;
import org.fitframework.globalization.StringResource;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.BeanRegisteredObserver;
import org.fitframework.ioc.BeanRegistry;
import org.fitframework.jvm.scan.PackageScanner;
import org.fitframework.jvm.scan.PackageScanner.Callback;
import org.fitframework.plugin.Plugin;
import org.fitframework.plugin.PluginCollection;
import org.fitframework.plugin.PluginMetadata;
import org.fitframework.plugin.RootPlugin;
import org.fitframework.resource.ResourceResolver;
import org.fitframework.runtime.FitRuntime;
import org.fitframework.test.annotation.EnableMockMvc;
import org.fitframework.test.domain.TestContext;
import org.fitframework.test.domain.mvc.MockMvc;
import org.fitframework.util.DisposedCallback;
import org.fitframework.http.server.HttpClassicServer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * MockMvcListener 测试。
 *
 * @author 季聿阶
 * @since 2026-01-02
 */
class MockMvcListenerTest {
    private static final String TIMEOUT_KEY = "fit.test.mockmvc.startup.timeout";

    @AfterEach
    void tearDown() {
        System.clearProperty(TIMEOUT_KEY);
    }

    @Test
    void shouldTimeoutWhenServerNotStarted() {
        int port = 65530;
        System.setProperty(TIMEOUT_KEY, "1000");
        StubBeanRegistry registry = new StubBeanRegistry();
        HttpClassicServer server = mockHttpServer(false, 0);
        TestContext context = createContext(registry, server);
        MockMvcListener listener = new MockMvcListenerStub(port, () -> false);

        assertThatThrownBy(() -> listener.beforeTestClass(context)).isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Mock MVC server failed to start within")
                .hasMessageContaining("auto-assigned port")
                .hasMessageContaining(TIMEOUT_KEY);
    }

    @Test
    void shouldStartWhenServerAvailable() {
        int port = 65531;
        System.setProperty(TIMEOUT_KEY, "1000");
        StubBeanRegistry registry = new StubBeanRegistry();
        HttpClassicServer server = mockHttpServer(true, port);
        TestContext context = createContext(registry, server);
        MockMvcListener listener = new MockMvcListenerStub(port, new DelayedStart(150));

        listener.beforeTestClass(context);

        assertThat(registry.lastRegistered()).isInstanceOf(MockMvc.class);
    }

    @Test
    void shouldFallbackToDefaultTimeoutWhenInvalidConfig() throws Exception {
        System.setProperty(TIMEOUT_KEY, "invalid");
        assertThat(readStartupTimeout()).isEqualTo(30_000L);
    }

    @Test
    void shouldFallbackToDefaultWhenNegativeTimeout() throws Exception {
        System.setProperty(TIMEOUT_KEY, "-1");
        assertThat(readStartupTimeout()).isEqualTo(30_000L);
    }

    @Test
    void shouldFallbackToDefaultWhenZeroTimeout() throws Exception {
        System.setProperty(TIMEOUT_KEY, "0");
        assertThat(readStartupTimeout()).isEqualTo(30_000L);
    }

    @Test
    void shouldClampWhenTooLargeTimeout() throws Exception {
        System.setProperty(TIMEOUT_KEY, String.valueOf(Long.MAX_VALUE));
        assertThat(readStartupTimeout()).isEqualTo(600_000L);
    }

    private TestContext createContext(StubBeanRegistry registry) {
        StubRootPlugin plugin = new StubRootPlugin(registry, mockHttpServer(false, 0));
        return new TestContext(MockMvcEnabledTest.class, plugin, Collections.emptyList());
    }

    private TestContext createContext(StubBeanRegistry registry, HttpClassicServer server) {
        StubRootPlugin plugin = new StubRootPlugin(registry, server);
        return new TestContext(MockMvcEnabledTest.class, plugin, Collections.emptyList());
    }

    private static HttpClassicServer mockHttpServer(boolean started, int port) {
        HttpClassicServer server = Mockito.mock(HttpClassicServer.class);
        Mockito.when(server.isStarted()).thenReturn(started);
        Mockito.when(server.getActualHttpPort()).thenReturn(port);
        return server;
    }

    private long readStartupTimeout() throws Exception {
        MockMvcListener listener = new MockMvcListener();
        Method method = MockMvcListener.class.getDeclaredMethod("getStartupTimeout");
        method.setAccessible(true);
        return (long) method.invoke(listener);
    }

    private static final class MockMvcListenerStub extends MockMvcListener {
        private final java.util.function.BooleanSupplier startedSupplier;

        private MockMvcListenerStub(int port, java.util.function.BooleanSupplier startedSupplier) {
            super();
            this.startedSupplier = startedSupplier;
        }

        @Override
        protected boolean isStarted(MockMvc mockMvc, int port) {
            return this.startedSupplier.getAsBoolean();
        }
    }

    private static final class DelayedStart implements java.util.function.BooleanSupplier {
        private final long readyAt;

        private DelayedStart(long delayMillis) {
            this.readyAt = System.currentTimeMillis() + delayMillis;
        }

        @Override
        public boolean getAsBoolean() {
            return System.currentTimeMillis() >= this.readyAt;
        }
    }

    private static final class StubBeanRegistry implements BeanRegistry {
        private Object lastRegistered;

        private Object lastRegistered() {
            return this.lastRegistered;
        }

        @Override
        public List<BeanMetadata> register(Class<?> beanClass) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public List<BeanMetadata> register(Object bean) {
            this.lastRegistered = bean;
            return Collections.emptyList();
        }

        @Override
        public List<BeanMetadata> register(Object bean, String name) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public List<BeanMetadata> register(Object bean, Type type) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public List<BeanMetadata> register(org.fitframework.ioc.BeanDefinition definition) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public void subscribe(BeanRegisteredObserver observer) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public void unsubscribe(BeanRegisteredObserver observer) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }
    }

    private static final class StubBeanContainer implements BeanContainer {
        private final StubRootPlugin plugin;
        private final StubBeanRegistry registry;
        private final HttpClassicServer server;

        private StubBeanContainer(StubRootPlugin plugin, StubBeanRegistry registry, HttpClassicServer server) {
            this.plugin = plugin;
            this.registry = registry;
            this.server = server;
        }

        @Override
        public String name() {
            return "mock-mvc-listener-test";
        }

        @Override
        public Plugin plugin() {
            return this.plugin;
        }

        @Override
        public BeanRegistry registry() {
            return this.registry;
        }

        @Override
        public Optional<BeanFactory> factory(String name) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Optional<BeanFactory> factory(Type type) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public List<BeanFactory> factories(Type type) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public List<BeanFactory> factories() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Optional<BeanFactory> lookup(String name) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Optional<BeanFactory> lookup(Type type) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public List<BeanFactory> all(Type type) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public List<BeanFactory> all() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public void start() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public void stop() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Beans beans() {
            return new StubBeans(this.server);
        }

        @Override
        public void destroySingleton(String beanName) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public void removeBean(String beanName) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public void dispose() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public boolean disposed() {
            return false;
        }

        @Override
        public void subscribe(DisposedCallback callback) {}

        @Override
        public void unsubscribe(DisposedCallback callback) {}
    }

    private static final class StubRootPlugin implements RootPlugin {
        private final StubBeanContainer container;

        private StubRootPlugin(StubBeanRegistry registry, HttpClassicServer server) {
            this.container = new StubBeanContainer(this, registry, server);
        }

        @Override
        public BeanContainer container() {
            return this.container;
        }

        @Override
        public Plugin loadPlugin(URL plugin) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Plugin unloadPlugin(URL plugin) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public PluginMetadata metadata() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Config config() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public ClassLoader pluginClassLoader() {
            return StubRootPlugin.class.getClassLoader();
        }

        @Override
        public PackageScanner scanner(Callback callback) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public FitRuntime runtime() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Plugin parent() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public PluginCollection children() {
            return new StubPluginCollection();
        }

        @Override
        public ResourceResolver resolverOfResources() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public EventPublisher publisherOfEvents() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public StringResource sr() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public boolean initialized() {
            return false;
        }

        @Override
        public void initialize() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public boolean started() {
            return false;
        }

        @Override
        public void start() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public boolean stopped() {
            return false;
        }

        @Override
        public void stop() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public void dispose() {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public boolean disposed() {
            return false;
        }

        @Override
        public void subscribe(DisposedCallback callback) {}

        @Override
        public void unsubscribe(DisposedCallback callback) {}
    }

    private static final class StubBeans implements BeanContainer.Beans {
        private final HttpClassicServer server;

        private StubBeans(HttpClassicServer server) {
            this.server = server;
        }

        @Override
        public <T> T get(Class<T> beanClass, Object... initialArguments) {
            if (beanClass.isInstance(this.server)) {
                return beanClass.cast(this.server);
            }
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> T get(Type beanType, Object... initialArguments) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> T get(String beanName, Object... initialArguments) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> T lookup(Class<T> beanClass, Object... initialArguments) {
            return get(beanClass, initialArguments);
        }

        @Override
        public <T> T lookup(Type beanType, Object... initialArguments) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> T lookup(String beanName, Object... initialArguments) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> java.util.Map<String, T> list(Class<T> beanClass) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> java.util.Map<String, T> list(Type beanType) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> java.util.Map<String, T> all(Class<T> beanClass) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public <T> java.util.Map<String, T> all(Type beanType) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }
    }

    private static final class StubPluginCollection implements PluginCollection {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public Plugin add(URL location) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Plugin remove(URL location) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Plugin get(int index) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public Plugin get(URL location) {
            throw new UnsupportedOperationException("Not needed for MockMvcListenerTest.");
        }

        @Override
        public boolean contains(URL location) {
            return false;
        }

        @Override
        public java.util.stream.Stream<Plugin> stream() {
            return java.util.stream.Stream.empty();
        }

        @Override
        public java.util.Iterator<Plugin> iterator() {
            return Collections.emptyIterator();
        }
    }

    @EnableMockMvc
    private static class MockMvcEnabledTest {}
}
