// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.http;

import org.fitframework.serialization.tlv.Tags;

/**
 * 表示 Http 通道中 {@link org.fitframework.serialization.TagLengthValues} 的标签常量值。
 *
 * @author 季聿阶
 * @since 2024-05-09
 */
public class HttpTags extends Tags {
    /** 表示异步任务唯一标识的标签值。 */
    private static final int ASYNC_TASK_ID_TAG = 0x40;

    static {
        // 校验标签值，确保所有标签值不冲突。
        validate(HttpTags.class);
    }

    /**
     * 获取异步任务唯一标识的标签值。
     *
     * @return 表示异步任务唯一标识的标签值的 {@code int}。
     */
    public static int getAsyncTaskIdTag() {
        return ASYNC_TASK_ID_TAG;
    }
}
