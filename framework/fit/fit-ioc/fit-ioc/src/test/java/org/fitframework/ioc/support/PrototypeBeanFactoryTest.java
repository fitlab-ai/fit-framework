// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.support;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.fitframework.conf.Config;
import org.fitframework.ioc.BeanApplicableScope;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.lifecycle.bean.BeanCreator;
import org.fitframework.ioc.lifecycle.bean.BeanDecorator;
import org.fitframework.ioc.lifecycle.bean.BeanDestroyer;
import org.fitframework.ioc.lifecycle.bean.BeanInitializer;
import org.fitframework.ioc.lifecycle.bean.BeanInjector;
import org.fitframework.ioc.lifecycle.bean.BeanLifecycle;
import org.fitframework.ioc.lifecycle.bean.support.DefaultBeanLifecycle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表示 {@link PrototypeBeanFactory} 的单元测试。
 *
 * @author 杭潇
 * @since 2023-02-28
 */
@DisplayName("测试 PrototypeBeanFactory 类")
public class PrototypeBeanFactoryTest {
    private PrototypeBeanFactory prototypeBeanFactory;

    @BeforeEach
    void setup() {
        BeanContainer container = mock(BeanContainer.class);
        List<BeanFactory> list = new ArrayList<>();
        list.add(mock(BeanFactory.class));
        when(container.all(List.class)).thenReturn(list);
        Set<String> strings = new HashSet<>();
        strings.add("testSet");
        BeanApplicableScope applicable = BeanApplicableScope.INSENSITIVE;
        AnnotationMetadata annotations = mock(AnnotationMetadata.class);
        Config config = mock(Config.class);
        BeanMetadata metadata =
                new DefaultBeanMetadata(container, "testBeanMetaData", null, String.class.getGenericSuperclass(),
                        "testStereotype", false, false, strings, applicable, annotations, config);
        BeanCreator creator = mock(BeanCreator.class);
        BeanDecorator decorator = mock(BeanDecorator.class);
        BeanInjector injector = mock(BeanInjector.class);
        BeanInitializer initializer = mock(BeanInitializer.class);
        BeanDestroyer destroyer = mock(BeanDestroyer.class);
        BeanLifecycle lifecycle =
                new DefaultBeanLifecycle(metadata, creator, decorator, injector, initializer, destroyer);
        this.prototypeBeanFactory = new PrototypeBeanFactory(lifecycle);
    }

    @Test
    @DisplayName("获取不存在的 Bean 实例，返回值为 null")
    void getNotExistBeanInstanceThenReturnIsNull() {
        Object[] arguments = {"testBeanMetaData"};
        Object prototypeBeanFactory0 = this.prototypeBeanFactory.get0(arguments);
        assertThat(prototypeBeanFactory0).isNull();
    }

    @Test
    @DisplayName("调用 toString() 方法，返回值与给定参数值相等")
    void invokeToStringThenReturnParametersAreEqualsToTheGivenValue() {
        String toString = this.prototypeBeanFactory.toString();
        String split = toString.split(",")[0].split("=")[1];
        assertThat(split).isEqualTo("testBeanMetaData");
    }

    @Test
    @DisplayName("销毁指定 Bean，执行成功")
    void destroyBeanThenExecuteSuccessfully() {
        assertDoesNotThrow(() -> this.prototypeBeanFactory.destroy("testBeanMetaData"));
    }
}
