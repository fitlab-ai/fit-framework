// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.broker.support;

import static org.fitframework.inspection.Validation.notBlank;
import static org.fitframework.inspection.Validation.notNull;

import org.fitframework.serialization.MessageSerializer;
import org.fitframework.broker.Aliases;
import org.fitframework.broker.ConfigurableFitable;
import org.fitframework.broker.Fitable;
import org.fitframework.broker.FitableExecutor;
import org.fitframework.broker.Genericable;
import org.fitframework.broker.InvocationContext;
import org.fitframework.broker.LoadBalancer;
import org.fitframework.broker.LocalExecutor;
import org.fitframework.broker.LocalExecutorFactory;
import org.fitframework.broker.Tags;
import org.fitframework.broker.Target;
import org.fitframework.broker.TargetLocator;
import org.fitframework.broker.UniqueFitableId;
import org.fitframework.broker.client.ClientLocalExecutorNotFoundException;
import org.fitframework.broker.client.Invoker;
import org.fitframework.conf.runtime.SerializationFormat;
import org.fitframework.exception.FitException;
import org.fitframework.ioc.BeanContainer;
import org.fitframework.ioc.BeanFactory;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 表示 {@link Fitable} 的默认实现。
 *
 * @author 季聿阶
 * @since 2023-03-10
 */
public class DefaultFitable implements ConfigurableFitable {
    private final BeanContainer container;
    private final LoadBalancer loadBalancer;
    private final TargetLocator targetLocator;
    private final LazyLoader<LocalExecutorFactory> localExecutorFactoryLoader;
    private final FitableExecutor remoteExecutor;
    private final FitableExecutor multicastExecutor;
    private final FitableExecutor genericRemoteExecutor;

    private final String id;
    private final String version;
    private final ConfigurableAliases aliases = new ConfigurableAliases();
    private final ConfigurableTags tags = new ConfigurableTags();
    private String degradationFitableId;
    private Genericable genericable;
    private final LazyLoader<UniqueFitableId> uniqueIdLoader;

    DefaultFitable(BeanContainer container, LoadBalancer loadBalancer, TargetLocator targetLocator, String id,
            String version) {
        this.container = container;
        this.loadBalancer = loadBalancer;
        this.targetLocator = targetLocator;
        this.localExecutorFactoryLoader = new LazyLoader<>(() -> this.container.factory(LocalExecutorFactory.class)
                .map(BeanFactory::<LocalExecutorFactory>get)
                .orElseThrow(() -> new IllegalStateException("No LocalExecutorFactory.")));
        this.remoteExecutor = new RemoteFitableExecutor(container);
        this.multicastExecutor = new MulticastFitableExecutor(this.container, this.remoteExecutor);
        this.genericRemoteExecutor = new GenericRemoteFitableExecutor(container);

        this.id = notBlank(id, "The fitable id cannot be blank.");
        this.version = notBlank(version, "The fitable version cannot be blank.");
        this.uniqueIdLoader = new LazyLoader<>(() -> UniqueFitableId.create(this.genericable.id(),
                this.genericable.version(),
                this.id,
                this.version));
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String version() {
        return this.version;
    }

    @Override
    public Aliases aliases() {
        return this.aliases;
    }

    @Override
    public Tags tags() {
        return this.tags;
    }

    @Override
    public String degradationFitableId() {
        return this.degradationFitableId;
    }

    @Override
    public Genericable genericable() {
        return this.genericable;
    }

    @Override
    public List<Target> targets() {
        return this.targetLocator.lookup(this.toUniqueId());
    }

    @Override
    public UniqueFitableId toUniqueId() {
        notNull(this.genericable, "The genericable cannot be null.");
        return this.uniqueIdLoader.get();
    }

    @Override
    public Object execute(InvocationContext context, Object[] args) {
        List<Target> balancedTargets = this.loadBalancer.balance(this, context, args);
        if (context.isMulticast()) {
            return execute(this.multicastExecutor, this, balancedTargets, context, args);
        }
        Optional<Target> localTarget = this.findLocalTarget(balancedTargets, context);
        if (localTarget.isPresent()) {
            LocalExecutor localExecutor = this.localExecutorFactoryLoader.get()
                    .get(this.toUniqueId())
                    .orElseThrow(() -> new ClientLocalExecutorNotFoundException(StringUtils.format(
                            "No local fitable executor. [id={0}]",
                            this.toUniqueId())));
            if (context.genericableMethod() != null) {
                return localExecutor.execute(args);
            }
            Optional<MessageSerializer> jsonSerializer = this.container.all(MessageSerializer.class)
                    .stream()
                    .map(BeanFactory::<MessageSerializer>get)
                    .filter(serializer -> serializer.getFormat() == SerializationFormat.JSON.code())
                    .findFirst();
            if (jsonSerializer.isPresent()) {
                Type[] types = localExecutor.method().getGenericParameterTypes();
                MessageSerializer serializer = jsonSerializer.get();
                byte[] bytes = serializer.serializeRequest(types, args);
                Object[] actualArgs = serializer.deserializeRequest(types, bytes);
                return localExecutor.execute(actualArgs);
            }
        }
        Invoker.Filter roundRobinFilter = Invoker.Filter.roundRobin();
        List<Target> actualTargets =
                roundRobinFilter.filter(this, context.localWorkerId(), balancedTargets, context.filterExtensions());
        if (context.genericableMethod() != null) {
            return execute(this.remoteExecutor, this, actualTargets, context, args);
        } else {
            return execute(this.genericRemoteExecutor, this, actualTargets, context, args);
        }
    }

    private static Object execute(FitableExecutor executor, Fitable fitable, List<Target> targets,
            InvocationContext context, Object[] args) {
        try {
            return executor.execute(fitable, targets, context, args);
        } catch (Throwable e) {
            throw FitException.wrap(e,
                    fitable.genericable().id(),
                    fitable.id(),
                    StringUtils.format("Failed to execute fitable. [genericableId={0}, fitableId={1}, targets={2}]",
                            fitable.genericable().id(),
                            fitable.id(),
                            targets));
        }
    }

    private Optional<Target> findLocalTarget(List<Target> candidates, InvocationContext context) {
        return candidates.stream()
                .filter(target -> StringUtils.equals(target.workerId(), context.localWorkerId()))
                .findFirst();
    }

    @Override
    public ConfigurableFitable aliases(Set<String> aliases) {
        this.aliases.clear();
        if (aliases != null) {
            this.aliases.set(aliases);
        }
        return this;
    }

    @Override
    public ConfigurableFitable appendAlias(String alias) {
        if (StringUtils.isNotBlank(alias)) {
            this.aliases.append(alias);
        }
        return this;
    }

    @Override
    public ConfigurableFitable removeAlias(String alias) {
        if (StringUtils.isNotBlank(alias)) {
            this.aliases.remove(alias);
        }
        return this;
    }

    @Override
    public ConfigurableFitable clearAliases() {
        this.aliases.clear();
        return this;
    }

    @Override
    public ConfigurableFitable tags(Set<String> tags) {
        this.tags.clear();
        if (tags != null) {
            this.tags.set(tags);
        }
        return this;
    }

    @Override
    public ConfigurableFitable appendTag(String tag) {
        if (StringUtils.isNotBlank(tag)) {
            this.tags.append(tag);
        }
        return this;
    }

    @Override
    public ConfigurableFitable removeTag(String tag) {
        if (StringUtils.isNotBlank(tag)) {
            this.tags.remove(tag);
        }
        return this;
    }

    @Override
    public ConfigurableFitable clearTags() {
        this.tags.clear();
        return this;
    }

    @Override
    public ConfigurableFitable degradationFitableId(String degradationFitableId) {
        this.degradationFitableId = degradationFitableId;
        return this;
    }

    @Override
    public ConfigurableFitable genericable(Genericable genericable) {
        this.genericable = genericable;
        return this;
    }

    @Override
    public String toString() {
        return "{\"id\": \"" + this.id + '\"' + ", \"version\": \"" + this.version + '\"' + ", \"aliases\": "
                + this.aliases + '}';
    }

    @Override
    public boolean equals(Object another) {
        if (this == another) {
            return true;
        }
        if (another == null || this.getClass() != another.getClass()) {
            return false;
        }
        DefaultFitable that = (DefaultFitable) another;
        return Objects.equals(this.id, that.id) && Objects.equals(this.version, that.version)
                && Objects.equals(this.aliases, that.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.version, this.aliases);
    }
}
