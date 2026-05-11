// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.fitframework.annotation.Component;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@DisplayName("测试接口注入")
class InterfaceInjectionTest extends AbstractBeanContainerTest {
    interface MyBean {}

    @Component
    static class MyBeanImpl implements MyBean {}

    @Component
    static class Client {
        private final MyBean bean;

        Client(MyBean bean) {
            this.bean = bean;
        }
    }

    @Test
    @DisplayName("通过Bean所实现的接口进行依赖注入，注入结果符合预期")
    void should_inject_by_interface() {
        BeanContainer container = container();
        container.registry().register(MyBeanImpl.class);
        container.registry().register(Client.class);

        Optional<Client> client = container.factory(Client.class).map(BeanFactory::get);
        assertTrue(client.isPresent());
        assertNotNull(client.get().bean);
    }
}
