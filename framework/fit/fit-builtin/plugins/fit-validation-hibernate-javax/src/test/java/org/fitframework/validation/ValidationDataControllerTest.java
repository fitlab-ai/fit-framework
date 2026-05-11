// SPDX-License-Identifier: MIT
// Copyright (c) 2025-2026 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.fitframework.http.client.HttpClassicClientResponse;
import org.fitframework.annotation.Fit;
import org.fitframework.test.annotation.MvcTest;
import org.fitframework.test.domain.mvc.MockMvc;
import org.fitframework.test.domain.mvc.request.MockMvcRequestBuilders;
import org.fitframework.test.domain.mvc.request.MockRequestBuilder;
import org.fitframework.validation.data.Company;
import org.fitframework.validation.data.Employee;
import org.fitframework.validation.data.ValidationDataController;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;

/**
 * {@link ValidationDataController} 的测试集。
 *
 * @author 阮睿
 * @since 2025-07-18
 */
@MvcTest(classes = {ValidationDataController.class})
@DisplayName("测试 EvalDataController")
public class ValidationDataControllerTest {
    @Fit
    private MockMvc mockMvc;

    private HttpClassicClientResponse<?> response;

    @AfterEach
    void teardown() throws IOException {
        if (this.response != null) {
            this.response.close();
        }
    }

    @Test
    @DisplayName("合法 Company 对象校验")
    void shouldOKWhenCreateValidCompany() {
        Employee validEmployee = new Employee("John", 25);
        Company validCompany = new Company(Collections.singletonList(validEmployee));
        MockRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/validation/company/default")
                .jsonEntity(validCompany)
                .responseType(Void.class);
        this.response = this.mockMvc.perform(requestBuilder);
        assertThat(this.response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("不合法 Company 对象校验")
    void shouldFailedWhenCreateInvalidCompany() {
        Company invalidCompany = new Company(null);
        MockRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/validation/company/default")
                .jsonEntity(invalidCompany)
                .responseType(Void.class);
        this.response = this.mockMvc.perform(requestBuilder);
        assertThat(this.response.statusCode()).isEqualTo(500);
    }

    @Test
    @DisplayName("自定义分组校验 Company 对象")
    void shouldOKWhenCreateValidCompanyWithGroup() {
        Employee validEmployee = new Employee("Jane", 30);
        Company validCompany = new Company(Collections.singletonList(validEmployee));
        MockRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/validation/company/companyGroup")
                .jsonEntity(validCompany)
                .responseType(Void.class);
        this.response = this.mockMvc.perform(requestBuilder);
        assertThat(this.response.statusCode()).isEqualTo(200);
    }

    @Test
    @DisplayName("自定义分组校验 Company 对象")
    void shouldFailedWhenCreateInvalidCompanyWithGroup() {
        Employee invalidEmployee = new Employee("", 15);
        Company invalidCompany = new Company(Collections.singletonList(invalidEmployee));
        MockRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/validation/company/companyGroup")
                .jsonEntity(invalidCompany)
                .responseType(Void.class);
        this.response = this.mockMvc.perform(requestBuilder);
        assertThat(this.response.statusCode()).isEqualTo(500);
    }

    @Test
    @DisplayName("RequestParam 参数 NotBlank 校验 - 空白值应返回 500")
    void shouldFailWhenRequestParamIsBlank() {
        MockRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/validation/param/notblank")
                .param("name", "  ")
                .responseType(Void.class);
        this.response = this.mockMvc.perform(requestBuilder);
        assertThat(this.response.statusCode()).isEqualTo(500);
    }

    @Test
    @DisplayName("RequestParam 参数 NotBlank 校验 - 合法值应返回 200")
    void shouldOkWhenRequestParamIsNotBlank() {
        MockRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/validation/param/notblank")
                .param("name", "validName")
                .responseType(Void.class);
        this.response = this.mockMvc.perform(requestBuilder);
        assertThat(this.response.statusCode()).isEqualTo(200);
    }
}
