// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.community.model.openai;

import static org.fitframework.fel.community.model.openai.api.OpenAiApi.CHAT_ENDPOINT;
import static org.fitframework.fel.community.model.openai.api.OpenAiApi.EMBEDDING_ENDPOINT;
import static org.fitframework.fel.community.model.openai.api.OpenAiApi.IMAGE_ENDPOINT;
import static org.fitframework.fel.community.model.openai.api.OpenAiApi.RERANK_ENDPOINT;

import org.fitframework.fel.community.model.openai.entity.embed.OpenAiEmbeddingResponse;
import org.fitframework.fel.community.model.openai.entity.image.OpenAiImageResponse;
import org.fitframework.fel.community.model.openai.entity.rerank.OpenAiRerankRequest;
import org.fitframework.fel.community.model.openai.entity.rerank.OpenAiRerankResponse;
import org.fitframework.http.annotation.PostMapping;
import org.fitframework.http.annotation.RequestBody;
import org.fitframework.annotation.Component;
import org.fitframework.flowable.Choir;
import org.fitframework.serialization.ObjectSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 表示测试使用的聊天接口。
 *
 * @author 易文渊
 * @since 2024-09-24
 */
@Component
public class TestModelController {
    private final ObjectSerializer serializer;

    /**
     * 创建 {@link TestModelController} 的实例。
     *
     * @param serializer 表示对象序列化器的 {@link ObjectSerializer}。
     */
    public TestModelController(ObjectSerializer serializer) {
        this.serializer = serializer;
    }

    /**
     * 测试用聊天接口。
     *
     * @return 表示流式返回结果的 {@link Choir}{@code <}{@link String}{@code >}。
     */
    @PostMapping(CHAT_ENDPOINT)
    public Choir<String> chat() {
        return Choir.create(emitter -> {
            for (int i = 1; i <= 3; ++i) {
                emitter.emit(getMockStreamResponseChunk(String.valueOf(i)));
            }
            emitter.emit("[DONE]");
            emitter.complete();
        });
    }

    /**
     * 测试用嵌入接口。
     *
     * @return 表示嵌入响应的 {@link OpenAiEmbeddingResponse}。
     */
    @PostMapping(EMBEDDING_ENDPOINT)
    public OpenAiEmbeddingResponse embed() {
        String json = "{\"object\":\"list\","
                + "\"data\":[{\"index\":0,\"object\":\"embedding\",\"embedding\":[1.0,2.0,3.0]}],"
                + "\"usage\":{\"prompt_tokens\":1,\"total_tokens\":2}}";
        return this.serializer.deserialize(json, OpenAiEmbeddingResponse.class);
    }

    private String getMockStreamResponseChunk(String content) {
        return "{\"id\": \"0\"," + "\"object\": \"chat.completion.chunk\"," + "\"created\": 0,"
                + "\"model\": \"test_model\"," + "\"choices\": [{\"index\": 0,\"delta\": {\"content\": \"" + content
                + "\"}," + "\"finish_reason\": null}]}";
    }

    /**
     * 测试用图片生成接口。
     *
     * @return 表示嵌入响应的 {@link OpenAiImageResponse}。
     */
    @PostMapping(IMAGE_ENDPOINT)
    public OpenAiImageResponse image() {
        String json = "{\"object\":\"list\","
                + "\"data\":[{\"b64_json\":\"123\"}, {\"b64_json\":\"456\"}, {\"b64_json\":\"789\"}]}";
        return this.serializer.deserialize(json, OpenAiImageResponse.class);
    }

    /**
     * 测试用重排接口。
     *
     * @return 表示重排响应的 {@link OpenAiRerankResponse}。
     */
    @PostMapping(RERANK_ENDPOINT)
    public OpenAiRerankResponse rerank(@RequestBody OpenAiRerankRequest request) {
        int topN = request.getTopN();
        List<String> docs = request.getDocuments();
        // 模拟生成结果：按 index 顺序生成 relevance_score，最多返回 topN 个
        List<OpenAiRerankResponse.RerankOrder> results = new ArrayList<>();
        double[] mockScores = {0.32713068, 0.4, 0.999071, 0.7867867, 0.6}; // 对应 index 0~4
        List<OpenAiRerankResponse.RerankOrder> allResults = new ArrayList<>();
        for (int i = 0; i < mockScores.length && i < docs.size(); i++) {
            allResults.add(new OpenAiRerankResponse.RerankOrder(i, mockScores[i]));
        }
        allResults.sort((a, b) -> Double.compare(b.relevanceScore(), a.relevanceScore()));
        List<OpenAiRerankResponse.RerankOrder> limitedResults = allResults.stream()
                .limit(topN)
                .collect(Collectors.toList());
        return new OpenAiRerankResponse(limitedResults);
    }
}