// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.validation.data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.fitframework.annotation.Component;
import org.fitframework.log.Logger;
import org.fitframework.validation.Validated;

/**
 * 表示测试用分组校验服务。
 *
 * @author 阮睿
 * @since 2025-07-18
 */
@Component
public class GroupValidateService {
    private static final Logger LOG = Logger.get(GroupValidateService.class);

    @Component
    @Validated(ValidationTestData.StudentGroup.class)
    public static class StudentValidateService {
        /**
         * 验证学生年龄。
         *
         * @param age 表示年龄的 {@code int}。
         */
        public void validateStudentAge(
                @Min(value = 7, message = "范围要在7~20之内", groups = ValidationTestData.StudentGroup.class)
                @Max(value = 20, message = "范围要在7~20之内", groups = ValidationTestData.StudentGroup.class)
                int age) {
            LOG.debug("Validating student age: {}", age);
        }
    }

    @Component
    @Validated(ValidationTestData.TeacherGroup.class)
    public static class TeacherValidateService {
        /**
         * 验证教师年龄。
         *
         * @param age 表示年龄的 {@code int}。
         */
        public void validateTeacherAge(
                @Min(value = 22, message = "范围要在22~65之内", groups = ValidationTestData.TeacherGroup.class)
                @Max(value = 65, message = "范围要在22~65之内", groups = ValidationTestData.TeacherGroup.class)
                int age) {
            LOG.debug("Validating teacher age: {}", age);
        }
    }

    // 高级分组验证服务。
    @Component
    @Validated(ValidationTestData.AdvancedGroup.class)
    public static class AdvancedValidateService {
        /**
         * 验证高级分组数据。
         *
         * @param data 用于测试复杂对象验证的数据 {@link ValidationTestData}。
         */
        public void validateAdvancedGroup(@Valid ValidationTestData data) {
            LOG.debug("Validating advanced group data: {}", data);
        }
    }
}