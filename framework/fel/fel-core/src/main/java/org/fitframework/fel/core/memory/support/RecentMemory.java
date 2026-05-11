// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.memory.support;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.memory.Memory;
import org.fitframework.fel.core.template.BulkStringTemplate;
import org.fitframework.fel.core.template.support.DefaultBulkStringTemplate;
import org.fitframework.inspection.Validation;
import org.fitframework.util.MapBuilder;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.fitframework.inspection.Validation.notNull;

/**
 * 表示使用最近一定次数历史记录的实现。
 *
 * @author 宋永坦
 * @since 2025-07-04
 */
public class RecentMemory implements Memory {
    private final Queue<ChatMessage> records;
    private final BulkStringTemplate bulkTemplate;
    private final Function<ChatMessage, Map<String, String>> extractor;

    /**
     * 指定最大保留历史记录数量的构造方法。
     *
     * @param maxCount 表示最大保留历史记录数量的 {@code int}。
     * @throws IllegalArgumentException 当 {@code maxCount < 0} 时。
     */
    public RecentMemory(int maxCount) {
        this(maxCount,
                new DefaultBulkStringTemplate("{{type}}:{{text}}", "\n"),
                message -> MapBuilder.<String, String>get()
                        .put("type", message.type().getRole())
                        .put("text", message.text())
                        .build());
    }

    /**
     * 指定最大保留历史记录数量、渲染模板、抽取方法的构造方法。
     *
     * @param maxCount 表示最大保留历史记录数量的 {@code int}。
     * @param bulkTemplate 表示批量字符串模板的 {@link BulkStringTemplate}。
     * @param extractor 表示将 {@link ChatMessage} 转换成
     * {@link Map}{@code <}{@link String}, {@link String}{@code >} 的处理函数。
     * @throws IllegalArgumentException 当 {@code maxCount < 0}、{@code bulkTemplate}、{@code extractor} 为 {@code null} 时。
     */
    public RecentMemory(int maxCount, BulkStringTemplate bulkTemplate,
            Function<ChatMessage, Map<String, String>> extractor) {
        Validation.greaterThanOrEquals(maxCount, 0, "The max count should >= 0.");
        this.records = new ArrayBlockingQueue<>(maxCount);
        this.bulkTemplate = notNull(bulkTemplate, "The bulkTemplate cannot be null.");
        this.extractor = notNull(extractor, "The extractor cannot be null.");
    }

    @Override
    public void add(ChatMessage message) {
        notNull(message, "The message cannot be null.");
        if (!this.records.offer(message)) {
            this.records.poll();
            this.records.offer(message);
        }
    }

    @Override
    public void set(List<ChatMessage> messages) {
        notNull(messages, "The messages cannot be null.");
        messages.forEach(this::add);
    }

    @Override
    public void clear() {
        this.records.clear();
    }

    @Override
    public List<ChatMessage> messages() {
        return this.records.stream().toList();
    }

    @Override
    public String text() {
        return this.records.stream()
                .map(this.extractor)
                .collect(Collectors.collectingAndThen(Collectors.toList(), this.bulkTemplate::render));
    }
}
