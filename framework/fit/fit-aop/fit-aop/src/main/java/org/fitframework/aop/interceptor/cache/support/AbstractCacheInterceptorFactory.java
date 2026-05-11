// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.aop.interceptor.cache.support;

import static org.fitframework.inspection.Validation.isTrue;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.aop.interceptor.MethodInterceptor;
import org.fitframework.aop.interceptor.MethodMatcher;
import org.fitframework.aop.interceptor.MethodMatcherCollection;
import org.fitframework.aop.interceptor.cache.CacheInterceptorFactory;
import org.fitframework.aop.interceptor.cache.KeyGenerator;
import org.fitframework.aop.interceptor.support.AbstractAnnotatedInterceptorFactory;
import org.fitframework.inspection.Nonnull;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.ioc.annotation.AnnotationMetadata;
import org.fitframework.ioc.annotation.AnnotationMetadataResolver;
import org.fitframework.ioc.annotation.AnnotationMetadataResolvers;
import org.fitframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 表示 {@link CacheInterceptorFactory} 的抽象实现类。
 *
 * @author 季聿阶
 * @since 2022-12-13
 */
public abstract class AbstractCacheInterceptorFactory<T extends Annotation> extends AbstractAnnotatedInterceptorFactory
        implements CacheInterceptorFactory {
    private final BeanContainer container;
    private final Class<T> annotationClass;
    private final AnnotationMetadataResolver annotationResolver;

    /**
     * 创建缓存方法拦截器工厂类。
     *
     * @param container 表示 Bean 容器的 {@link BeanContainer}。
     * @param annotationClass 表示缓存注解的类型 {@link Class}{@code <T>}。
     */
    public AbstractCacheInterceptorFactory(BeanContainer container, Class<T> annotationClass) {
        super(annotationClass);
        this.container = notNull(container, "The bean container cannot be null.");
        this.annotationClass = notNull(annotationClass, "The annotation class cannot be null.");
        this.annotationResolver = AnnotationMetadataResolvers.create();
    }

    @Override
    public MethodInterceptor create(BeanFactory factory, @Nonnull Method interceptMethod) {
        isTrue(this.isInterceptMethod(interceptMethod), "The method is not an intercept method.");
        AnnotationMetadata annotations = this.annotationResolver.resolve(interceptMethod);
        MethodInterceptor cacheInterceptor = this.create(this.container,
                this.getKeyGenerator(interceptMethod, annotations),
                this.getCacheInstances(annotations));
        MethodMatcherCollection matchers = cacheInterceptor.getPointCut().matchers();
        matchers.add(MethodMatcher.annotation(this.annotationClass));
        matchers.add(MethodMatcher.specified(interceptMethod));
        return cacheInterceptor;
    }

    private KeyGenerator getKeyGenerator(Method interceptMethod, AnnotationMetadata annotations) {
        int parameterCount = interceptMethod.getParameterCount();
        if (parameterCount <= 0) {
            return KeyGenerator.empty();
        } else if (parameterCount == 1) {
            return KeyGenerator.specified(0);
        } else {
            T annotation = annotations.getAnnotation(this.annotationClass);
            String keyPattern = this.cacheKeyPattern(annotation);
            if (StringUtils.isBlank(keyPattern)) {
                return KeyGenerator.params();
            } else if (keyPattern.startsWith(SpecifiedParamKeyGenerator.KEY_PREFIX)) {
                return KeyGenerator.specified(keyPattern, interceptMethod);
            } else {
                return KeyGenerator.constant(keyPattern);
            }
        }
    }

    private List<String> getCacheInstances(AnnotationMetadata annotations) {
        T annotation = annotations.getAnnotation(this.annotationClass);
        return this.cacheInstanceNames(annotation);
    }

    /**
     * 从指定的注解上获取缓存实例的名字列表。
     *
     * @param annotation 表示指定的注解的 {@link T}。
     * @return 表示从指定的注解上获取缓存实例的名字列表的 {@link List}{@code <}{@link String}{@code >}。
     */
    protected abstract List<String> cacheInstanceNames(@Nonnull T annotation);

    /**
     * 从指定的注解上获取缓存键的样式。
     *
     * @param annotation 表示指定的注解的 {@link T}。
     * @return 表示从指定的注解上获取缓存键的样式的 {@link String}。
     */
    protected abstract String cacheKeyPattern(@Nonnull T annotation);

    /**
     * 根据指定的键生成器和缓存实例，创建一个缓存方法拦截器。
     *
     * @param container 表示当前 Bean 容器的 {@link BeanContainer}。
     * @param keyGenerator 表示指定的键生成器的 {@link KeyGenerator}。
     * @param cacheNames 表示缓存实例名字列表的 {@link List}{@code <}{@link String}{@code >}。
     * @return 表示创建的缓存方法拦截器的 {@link MethodInterceptor}。
     * @throws IllegalArgumentException 当 {@code cacheInstances} 为空列表时。
     */
    protected abstract MethodInterceptor create(BeanContainer container, KeyGenerator keyGenerator,
            List<String> cacheNames);
}
