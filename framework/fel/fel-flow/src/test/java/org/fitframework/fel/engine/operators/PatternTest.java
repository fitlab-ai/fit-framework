// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators;

import static org.fitframework.fel.engine.operators.patterns.SyncTipper.fewShot;
import static org.fitframework.fel.engine.operators.patterns.SyncTipper.history;
import static org.fitframework.fel.engine.operators.patterns.SyncTipper.passThrough;
import static org.fitframework.fel.engine.operators.patterns.SyncTipper.question;
import static org.fitframework.fel.engine.operators.patterns.SyncTipper.value;
import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.Prompt;
import org.fitframework.fel.core.chat.support.ChatMessages;
import org.fitframework.fel.core.document.Content;
import org.fitframework.fel.core.document.Document;
import org.fitframework.fel.core.document.MeasurableDocument;
import org.fitframework.fel.core.fewshot.Example;
import org.fitframework.fel.core.fewshot.ExampleSelector;
import org.fitframework.fel.core.fewshot.support.DefaultExample;
import org.fitframework.fel.core.memory.Memory;
import org.fitframework.fel.core.memory.support.CacheMemory;
import org.fitframework.fel.core.pattern.Retriever;
import org.fitframework.fel.core.util.Tip;
import org.fitframework.fel.engine.flows.AiFlows;
import org.fitframework.fel.engine.flows.AiProcessFlow;
import org.fitframework.fel.engine.flows.Conversation;
import org.fitframework.fel.engine.flows.ConverseLatch;
import org.fitframework.fel.engine.operators.patterns.SimplePattern;
import org.fitframework.fel.engine.operators.prompts.Prompts;
import org.fitframework.fel.engine.util.AiFlowSession;
import org.fitframework.waterflow.domain.context.FlowSession;
import org.fitframework.waterflow.domain.context.Window;
import org.fitframework.waterflow.domain.utils.SleepUtil;
import org.fitframework.resource.web.Media;
import org.fitframework.util.CollectionUtils;
import org.fitframework.util.ObjectUtils;
import org.fitframework.util.StringUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * 委托单元测试。
 *
 * @author 刘信宏
 * @since 2024-05-08
 */
@DisplayName("测试委托单元")
public class PatternTest {
    @Test
    @DisplayName("测试 RunnableParallel")
    void shouldOkWhenAiFlowWithNormalRunnableParallel() {
        Memory memory = getMockMemory();
        final StringBuilder answer = new StringBuilder();
        Conversation<String, Prompt> converse = AiFlows.<String>create()
                .runnableParallel(question(),
                        history("history"),
                        value("context", (arg -> Content.from("context"))),
                        value("key", "val"))
                .prompt(Prompts.human("answer {{question}} from {{context}} with {{history}}"))
                .close()
                .converse()
                .bind(memory);

        converse.doOnConsume(r -> answer.append(r.text())).offer("question").await();
        assertThat(answer.toString()).isEqualTo("answer question from context with my history");

        // 验证 runnableParallel 中 join 初始值重新获取，不影响后续的请求。
        StringBuilder answer1 = new StringBuilder();
        converse.doOnConsume(r -> answer1.append(r.text())).offer("question1").await();
        assertThat(answer1.toString()).isEqualTo("answer question1 from context with my history");
    }

    @Test
    @DisplayName("测试 ExampleSelector")
    void shouldOkWhenAiFlowWithExampleSelector() {
        Example[] examples = {new DefaultExample("2+2", "4"), new DefaultExample("2+3", "5")};
        Conversation<String, Prompt> converse = AiFlows.<String>create()
                .runnableParallel(question(),
                        fewShot(ExampleSelector.builder()
                                .template("{{q}}={{a}}", "q", "a")
                                .delimiter("\n")
                                .example(examples)
                                .build()))
                .prompt(Prompts.human("{{examples}}\n{{question}}="))
                .close()
                .converse();
        assertThat(converse.offer("1+2").await().text()).isEqualTo("2+2=4\n2+3=5\n1+2=");
    }

    @RepeatedTest(1000)
    @DisplayName("测试 RunnableParallel 并发稳定性")
    void shouldStableWhenRunnableParallelUnderConcurrency() {
        Example[] examples = {new DefaultExample("2+2", "4"), new DefaultExample("2+3", "5")};
        Conversation<String, Prompt> converse = AiFlows.<String>create()
                .runnableParallel(question(),
                        fewShot(ExampleSelector.builder()
                                .template("{{q}}={{a}}", "q", "a")
                                .delimiter("\n")
                                .example(examples)
                                .build()))
                .prompt(Prompts.human("{{examples}}\n{{question}}="))
                .close()
                .converse();
        assertThat(converse.offer("1+2").await().text()).isEqualTo("2+2=4\n2+3=5\n1+2=");
    }

    @Test
    @DisplayName("测试 Retriever")
    void shouldOkWhenAiFlowWithRetriever() {
        Memory memory = getMockMemory();
        Retriever<Prompt, MeasurableDocument> retriever =
                input -> Collections.singletonList(new MeasurableDocument(Document.custom()
                        .text("[context: " + input.text() + "]")
                        .medias(Collections.singletonList(new Media("image/png", "url")))
                        .metadata(Collections.emptyMap())
                        .build(), 0.6f));
        final StringBuilder answer = new StringBuilder();
        AiProcessFlow<Tip, Content> ragFlow = AiFlows.<Tip>create()
                .runnableParallel(history(), passThrough())
                .prompt(Prompts.human("enhance {{q1}} with {{history}}"))
                .retrieve(retriever)
                .enhance(d -> d)
                .synthesize(d -> d.get(0))
                .close(r -> answer.append(r.text()));

        ChatMessages messages = new ChatMessages();
        AiFlows.<Tip>create()
                .runnableParallel(value("context", ragFlow), history("history"), passThrough())
                .prompt(Prompts.human("answer {{q1}} and {{q2}} from {{context}} with {{history}}"))
                .close(r -> messages.addAll(r.messages()))
                .converse()
                .bind(memory)
                .offer(Tip.from("q1", "my question1").add("q2", "my question2"))
                .await();

        assertThat(answer.toString()).isEqualTo("[context: enhance my question1 with my history]");
        assertThat(messages.text()).isEqualTo(String.format(
                "answer my question1 and my question2 from %s with my history",
                answer));
        assertThat(messages.messages()
                .stream()
                .map(ChatMessage::medias)
                .filter(CollectionUtils::isNotEmpty)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())).hasSize(1);
    }

    @Test
    @DisplayName("测试 SimplePattern")
    void shouldOkWhenDelegateSimplePattern() {
        FlowSession session = new FlowSession();
        String key = "key";
        String value = "value";
        session.setState(key, value);
        SimplePattern<Prompt, String> pattern = new SimplePattern<>(prompt -> {
            String inputContextValue = AiFlowSession.get()
                    .map(target -> ObjectUtils.<String>cast(target.getState(key)))
                    .orElse(StringUtils.EMPTY);
            return prompt.text() + inputContextValue;
        });
        Window token = session.begin();
        ConverseLatch<String> offer = AiFlows.<Tip>create()
                .prompt(Prompts.human("{{0}}"))
                .delegate(pattern)
                .close()
                .converse(session)
                .offer(Tip.fromArray("human msg."));
        token.complete();
        String result = offer.await();

        assertThat(result).isEqualTo("human msg." + value);

    }

    private static Memory getMockMemory() {
        return new CacheMemory() {
            @Override
            public String text() {
                return "my history";
            }
        };
    }
}
