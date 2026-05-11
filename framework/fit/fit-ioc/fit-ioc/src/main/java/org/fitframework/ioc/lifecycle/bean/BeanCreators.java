// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.ioc.lifecycle.bean;

import org.fitframework.ioc.BeanMetadata;
import org.fitframework.ioc.lifecycle.bean.support.BeanMethodBeanCreator;
import org.fitframework.ioc.lifecycle.bean.support.ClassBeanCreator;
import org.fitframework.ioc.lifecycle.bean.support.ConstructorBeanCreator;
import org.fitframework.ioc.lifecycle.bean.support.DirectBeanCreator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 表示 Bean 创建器的工具类集合。
 *
 * @author 梁济时
 * @since 2022-06-01
 */
public class BeanCreators {
    /**
     * 创建一个用以返回直接对象的Bean创建程序。
     *
     * @param bean 表示直接对象的 {@link Object}。
     * @return 表示包装后的Bean创建程序的 {@link BeanCreator}。
     * @throws IllegalArgumentException {@code bean} 为 {@code null}。
     */
    public static BeanCreator direct(Object bean) {
        return new DirectBeanCreator(bean);
    }

    /**
     * 使用指定的Bean容器和Bean定义作为上下文，为指定的构造方法创建Bean创建程序。
     *
     * @param metadata 表示Bean的元数据的 {@link BeanMetadata}。
     * @param constructor 表示Bean的构造方法的 {@link Constructor}。
     * @return 表示用以通过指定构造方法创建Bean实例的Bean创建程序的 {@link BeanCreator}。
     * @throws IllegalArgumentException {@code container} 或 {@code constructor} 为 {@code null}。
     */
    public static BeanCreator byConstructor(BeanMetadata metadata, Constructor<?> constructor) {
        return new ConstructorBeanCreator(metadata, constructor);
    }

    /**
     * 为有多个候选构造方法的Bean类型创建Bean的创建程序。
     *
     * @param metadata 表示待创建的Bean的元数据的 {@link BeanMetadata}。
     * @param beanClass 表示待创建的Bean的类型的 {@link Class}。
     * @return 表示Bean的创建程序的 {@link BeanCreator}。
     */
    public static BeanCreator byClass(BeanMetadata metadata, Class<?> beanClass) {
        return new ClassBeanCreator(metadata, beanClass);
    }

    /**
     * 使用定义在指定Bean的创建方法创建Bean的创建程序。
     *
     * @param definition 表示所属Bean的定义的 {@link BeanMetadata}。
     * @param method 表示Bean中的工厂方法的 {@link Method}。
     * @return 表示Bean的创建程序的 {@link BeanCreator}。
     * @throws IllegalArgumentException {@code container}、{@code definition} 或 {@code null} 为 {@code null}。
     */
    public static BeanCreator byMethod(BeanMetadata definition, Method method) {
        return new BeanMethodBeanCreator(definition, method);
    }
}
