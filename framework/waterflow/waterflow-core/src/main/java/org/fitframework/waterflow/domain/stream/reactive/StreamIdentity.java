// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.stream.reactive;

import org.fitframework.waterflow.domain.utils.Identity;

/**
 * StreamIdentity
 *
 * @since 1.0
 */
public interface StreamIdentity extends Identity {
    /**
     * getStreamId
     *
     * @return String
     */
    String getStreamId();
}
