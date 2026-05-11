// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.serialization.test.person;

import lombok.Data;
import org.fitframework.util.StringUtils;

/**
 * 为单元测试提供人名的信息定义。
 *
 * @author 梁济时
 * @since 2020-11-23
 */
@Data
public class PersonName {
    private String first;
    private String middle;
    private String last;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (StringUtils.isNotBlank(this.getFirst())) {
            builder.append(this.getFirst());
        }
        if (StringUtils.isNotBlank(this.getMiddle())) {
            builder.append(' ').append(this.getMiddle());
        }
        if (StringUtils.isNotBlank(this.getLast())) {
            builder.append(' ').append(this.getLast());
        }
        return StringUtils.trim(builder.toString());
    }
}
