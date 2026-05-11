// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.json.jackson;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import org.fitframework.serialization.MessageSerializerTest;
import org.fitframework.conf.Config;
import org.fitframework.conf.support.PropertiesConfig;
import org.fitframework.test.genericable.GenericableTestUtils;
import org.fitframework.util.ReflectionUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Method;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * {@link org.fitframework.serialization.MessageSerializer} 的 {@link JacksonMessageSerializer} 实现的基本测试用例。
 *
 * @author 季聿阶
 * @since 2022-09-10
 */
@DisplayName("测试 MessageSerializer 的基本测试用例")
public class MessageSerializerGenericableTest {
    private final Properties properties = new Properties();
    private final Config config = new PropertiesConfig("test", properties);

    private final MessageSerializerTest test =
            new MessageSerializerTest(() -> new JacksonMessageSerializer(new JacksonObjectSerializer(null,
                    null,
                    null,
                    true), this.config));

    @BeforeEach
    void setup() {
        GenericableTestUtils.getBeforeEachMethod(MessageSerializerTest.class)
                .ifPresent(method -> ReflectionUtils.invoke(this.test, method));
    }

    @AfterEach
    void teardown() {
        GenericableTestUtils.getAfterEachMethod(MessageSerializerTest.class)
                .ifPresent(method -> ReflectionUtils.invoke(this.test, method));
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("实现为 JacksonMessageSerializer")
    @MethodSource("source")
    void test(String displayName, Method method) {
        assertThat(displayName).isNotBlank();
        ReflectionUtils.invoke(this.test, method);
    }

    private static Stream<Arguments> source() {
        return GenericableTestUtils.getTestMethods(MessageSerializerTest.class)
                .map(testMethod -> arguments(testMethod.displayName(), testMethod.method()));
    }
}
