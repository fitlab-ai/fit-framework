// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.tool.model.transfer;

import java.util.List;

/**
 * 表示定义组的基本内容。
 *
 * @author 王攀博
 * @since 2024-10-25
 */
public class DefinitionGroupData extends GroupData {
    private List<DefinitionData> definitions;

    /**
     * 获取定义列表。
     *
     * @return 表示定义列表的 {@link List}{@code <}{@link DefinitionData}{@code >}。
     */
    public List<DefinitionData> getDefinitions() {
        return this.definitions;
    }

    /**
     * 设置定义列表。
     *
     * @param definitions 表示要设置的定义列表的 {@link List}{@code <}{@link DefinitionData}{@code >}。
     */
    public void setDefinitions(List<DefinitionData> definitions) {
        this.definitions = definitions;
    }
}
