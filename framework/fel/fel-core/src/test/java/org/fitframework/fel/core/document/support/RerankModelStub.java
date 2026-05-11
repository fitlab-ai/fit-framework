// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.core.document.support;

import org.fitframework.fel.core.document.MeasurableDocument;
import org.fitframework.fel.core.rerank.RerankModel;
import org.fitframework.fel.core.rerank.RerankOption;

import java.util.List;

/**
 * 重排模型服务的打桩实现。
 *
 * @since 2025-07-28
 */
class RerankModelStub implements RerankModel {
    @Override
    public List<MeasurableDocument> generate(List<MeasurableDocument> documents, RerankOption rerankOption) {
        return documents;
    }
}
