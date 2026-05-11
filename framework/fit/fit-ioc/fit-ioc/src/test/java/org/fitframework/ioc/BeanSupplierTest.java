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

@DisplayName("测试 BeanSupplier 接口")
class BeanSupplierTest extends AbstractBeanContainerTest {
    @Component
    static class Component1 {}

    static class Component2 {
        final Component1 component1;

        Component2(Component1 component1) {
            this.component1 = component1;
        }
    }

    @Component
    static class Component2Supplier implements BeanSupplier<Component2> {
        private final Component1 component1;

        Component2Supplier(Component1 component1) {
            this.component1 = component1;
        }

        @Override
        public Component2 get() {
            return new Component2(this.component1);
        }
    }

    @Component
    static class Component3 {
        final Component2 component2;

        Component3(Component2 component2) {
            this.component2 = component2;
        }
    }

    @Test
    void should_resolve_bean() {
        BeanContainer container = container();
        container.registry().register(Component2Supplier.class);
        container.registry().register(Component1.class);
        container.registry().register(Component3.class);
        Optional<Component3> c3 = container.factory(Component3.class).map(BeanFactory::get);
        assertTrue(c3.isPresent());
        assertNotNull(c3.get().component2);
        assertNotNull(c3.get().component2.component1);
    }
}
