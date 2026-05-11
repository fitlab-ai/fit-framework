// SPDX-License-Identifier: MIT
// Copyright (c) 2024-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import static org.fitframework.inspection.Validation.isTrue;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.broker.ConfigurableGenericable;
import org.fitframework.broker.Fitable;
import org.fitframework.broker.FitableFactory;
import org.fitframework.broker.Genericable;
import org.fitframework.broker.GenericableFactory;
import org.fitframework.broker.GenericableRepository;
import org.fitframework.broker.LocalGenericableRepository;
import org.fitframework.broker.UniqueGenericableId;
import org.fitframework.inspection.Validation;
import org.fitframework.log.Logger;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.LockUtils;
import org.fitframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 表示 {@link GenericableRepository} 的本地仓库。
 *
 * @author 季聿阶
 * @since 2023-03-25
 */
public class DefaultLocalGenericableRepository implements LocalGenericableRepository {
    private static final Logger log = Logger.get(DefaultLocalGenericableRepository.class);

    private final GenericableFactory genericableFactory;
    private final FitableFactory fitableFactory;

    private final GenericableRepository rootRepository;
    private final List<GenericableRepository> pluginRepositories = new ArrayList<>();
    private final ReadWriteLock lock;

    /**
     * 使用指定的泛服务工厂、实现工厂和泛服务仓库初始化 {@link DefaultLocalGenericableRepository} 的新实例。
     *
     * @param genericableFactory 表示泛服务工厂的 {@link GenericableFactory}。
     * @param fitableFactory 表示实现工厂的 {@link FitableFactory}。
     * @param rootRepository 表示泛服务仓库的 {@link GenericableRepository}。
     * @throws IllegalArgumentException 当 {@code genericableFactory} 或 {@code fitableFactory} 为 {@code null} 时。
     */
    public DefaultLocalGenericableRepository(GenericableFactory genericableFactory, FitableFactory fitableFactory,
            GenericableRepository rootRepository) {
        this.genericableFactory = notNull(genericableFactory, "The genericable factory cannot be null.");
        this.fitableFactory = notNull(fitableFactory, "The fitable factory cannot be null.");
        this.rootRepository = rootRepository;
        this.lock = LockUtils.newReentrantReadWriteLock();
    }

    @Override
    public String name() {
        return "local";
    }

    @Override
    public Optional<Genericable> get(String id, String version) {
        List<GenericableRepository> allRepositories = new ArrayList<>(this.pluginRepositories);
        allRepositories.add(this.rootRepository);
        return allRepositories.stream()
                .map(repository -> repository.get(id, version))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .reduce((g1, g2) -> this.mergeGenericables(id, version, g1, g2));
    }

    @Override
    public Map<UniqueGenericableId, Genericable> getAll() {
        Map<UniqueGenericableId, Genericable> genericables = new TreeMap<>(this.rootRepository.getAll());
        for (GenericableRepository genericableRepository : this.pluginRepositories) {
            genericables.putAll(genericableRepository.getAll());
        }
        return genericables;
    }

    private ConfigurableGenericable mergeGenericables(String id, String version, Genericable g1, Genericable g2) {
        this.validate(g1, g2);
        ConfigurableGenericable genericable = this.genericableFactory.create(id, version)
                .name(g1.name())
                .type(g1.type())
                .method(g1.method().method())
                .route(g1.route().defaultFitable())
                .tags(CollectionUtils.union(g1.tags().all(), g2.tags().all()));
        List<Fitable> fitables = CollectionUtils.connect(g1.fitables(), g2.fitables())
                .stream()
                .map(fitable -> this.fitableFactory.create(fitable.id(), fitable.version())
                        .degradationFitableId(fitable.degradationFitableId())
                        .aliases(fitable.aliases().all())
                        .tags(fitable.tags().all())
                        .genericable(genericable))
                .map(Fitable.class::cast)
                .collect(Collectors.toList());
        genericable.fitables(fitables);
        return genericable;
    }

    private void validate(Genericable g1, Genericable g2) {
        isTrue(g1.type() == g2.type(),
                "The genericable type is not match. [type1={0}, type2={1}]",
                g1.type(),
                g2.type());
        Validation.equals(g1.route().defaultFitable(),
                g2.route().defaultFitable(),
                "The default routing fitable of genericable is not match. [defaultFitable1={0}, defaultFitable2={1}]",
                g1.route().defaultFitable(),
                g2.route().defaultFitable());
    }

    /**
     * 安装一个插件的仓库。
     *
     * @param repository 表示待安装的插件仓库的 {@link GenericableRepository}。
     */
    public void install(GenericableRepository repository) {
        notNull(repository, "The genericable repository to install cannot be null.");
        LockUtils.synchronize(this.lock.writeLock(), () -> {
            this.getChild(repository.name()).ifPresent(this::uninstall);
            log.debug("Install genericable repository. [name={}]", repository.name());
            this.pluginRepositories.add(repository);
        });
    }

    /**
     * 卸载一个插件的仓库。
     *
     * @param repository 表示待卸载的插件仓库的 {@link GenericableRepository}。
     */
    public void uninstall(GenericableRepository repository) {
        notNull(repository, "The genericable repository to uninstall cannot be null.");
        LockUtils.synchronize(this.lock.writeLock(), () -> {
            OptionalInt first = IntStream.range(0, this.pluginRepositories.size())
                    .filter(i -> Objects.equals(this.pluginRepositories.get(i).name(), repository.name()))
                    .findFirst();
            if (first.isPresent()) {
                log.debug("Uninstall genericable repository. [name={}]", repository.name());
                this.pluginRepositories.remove(first.getAsInt());
            } else {
                log.debug("The genericable repository to uninstall not exists. [name={}]", repository.name());
            }
        });
    }

    /**
     * 获取指定名字的服务仓库。
     *
     * @param name 表示服务仓库名字的 {@link String}。
     * @return 表示指定服务仓库的 {@link Optional}{@code <}{@link GenericableRepository}{@code >}。
     */
    public Optional<GenericableRepository> getChild(String name) {
        return LockUtils.synchronize(this.lock.readLock(),
                () -> this.pluginRepositories.stream()
                        .filter(repository -> StringUtils.equals(repository.name(), name))
                        .findAny());
    }
}
