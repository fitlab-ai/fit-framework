// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.template;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.fel.core.chat.ChatMessage;
import org.fitframework.fel.core.chat.MessageType;
import org.fitframework.fel.core.document.Content;
import org.fitframework.fel.core.template.support.DefaultStringTemplate;
import org.fitframework.fel.core.template.support.HumanMessageTemplate;
import org.fitframework.fel.core.template.support.SystemMessageTemplate;
import org.fitframework.fel.core.util.Tip;
import org.fitframework.resource.web.Media;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * 表示 {@link MessageTemplate} 的单元测试。
 *
 * @author 易文渊
 * @since 2024-04-25
 */
@DisplayName("测试 MessageTemplate")
public class MessageTemplateTest {
    @Nested
    @DisplayName("测试 SystemMessageTemplate")
    class System {
        @Test
        @DisplayName("渲染系统消息，返回正确结果")
        void shouldReturnOk() {
            MessageTemplate template = new SystemMessageTemplate("You are a helpful {{character}}.");
            ChatMessage message = template.render(Tip.from("character", "assistant").freeze());
            assertThat(message.type()).isEqualTo(MessageType.SYSTEM);
            assertThat(message.text()).isEqualTo("You are a helpful assistant.");
        }

        @Test
        @DisplayName("渲染系统消息并携带媒体数据，返回正确结果")
        void shouldReturnOkWithMedia() {
            MessageTemplate template = new SystemMessageTemplate("You are a helpful {{character}}.");
            Content contents = Content.from("assistant", new Media("image/png", "robot.png"));
            ChatMessage message = template.render(Tip.from("character", contents).freeze());
            assertThat(message.type()).isEqualTo(MessageType.SYSTEM);
            assertThat(message.medias()).isEmpty();
            assertThat(message.text()).isEqualTo("You are a helpful assistant.");
        }
    }

    @Nested
    @DisplayName("测试 HumanMessageTemplate")
    class Human {
        @Test
        @DisplayName("渲染人类消息，返回正确结果")
        void shouldReturnOk() {
            MessageTemplate template = new HumanMessageTemplate("I'm a good {{0}}.");
            ChatMessage message = template.render(Tip.fromArray("boy").freeze());
            assertThat(message.type()).isEqualTo(MessageType.HUMAN);
            assertThat(message.text()).isEqualTo("I'm a good boy.");
        }

        @Test
        @DisplayName("传入部分字符串模板创建消息模板，返回正确结果")
        void shouldReturnOkWithPartialTemplate() {
            StringTemplate stringTemplate = new DefaultStringTemplate("I'm a good {{0}}.").partial("0", "girl");
            MessageTemplate messageTemplate = new HumanMessageTemplate(stringTemplate);
            assertThat(messageTemplate.placeholder()).doesNotContain("0");

            ChatMessage message = messageTemplate.render(null);
            assertThat(message.type()).isEqualTo(MessageType.HUMAN);
            assertThat(message.text()).isEqualTo("I'm a good girl.");

            message = messageTemplate.render(Tip.fromArray("boy").freeze());
            assertThat(message.text()).isEqualTo("I'm a good girl.");
        }

        @Test
        @DisplayName("渲染人类消息并携带媒体数据，返回正确结果")
        void shouldReturnOkWithMedia() {
            MessageTemplate template = new HumanMessageTemplate("I'm a good {{sex}}, I like play {{something}}.");
            Tip tip = new Tip().add("sex", Content.from("man", new Media("image/png", "man.png")))
                    .add("something", Content.from("basketball", new Media("image/png", "basketball.png")));
            ChatMessage message = template.render(tip.freeze());
            assertThat(message.type()).isEqualTo(MessageType.HUMAN);
            assertThat(message.medias()).hasSize(2);
            assertThat(message.text()).isEqualTo("I'm a good man, I like play basketball.");
        }
    }
}