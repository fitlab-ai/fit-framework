// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.client;

import org.fitframework.example.entity.Education;
import org.fitframework.http.annotation.GetMapping;
import org.fitframework.http.annotation.HttpProxy;
import org.fitframework.http.annotation.PatchMapping;
import org.fitframework.http.annotation.PathVariable;
import org.fitframework.http.annotation.PostMapping;
import org.fitframework.http.annotation.PutMapping;
import org.fitframework.http.annotation.RequestBean;
import org.fitframework.http.annotation.RequestBody;
import org.fitframework.http.annotation.RequestCookie;
import org.fitframework.http.annotation.RequestForm;
import org.fitframework.http.annotation.RequestHeader;
import org.fitframework.http.annotation.RequestQuery;

import java.util.List;

/**
 * This interface defines a set of methods for testing HTTP client proxy functionality.
 * It extends the TestInterface and provides specific annotations for configuring the HTTP request details.
 * The interface is marked with @HttpProxy to indicate it's a proxy for HTTP requests.
 * Each method in this interface specifies the full URL for the HTTP request using the @GetMapping, @PostMapping, etc.
 * annotations.
 *
 * @author 季聿阶
 * @since 2025-06-01
 */
@HttpProxy
public interface TestRequestAddressInMethodMapping extends TestInterface {
    @Override
    @PostMapping(path = "http://localhost:8080/http-server/request-bean")
    Education requestBean(@RequestBean Education education);

    @Override
    @GetMapping(path = "http://localhost:8080/http-server/path-variable/{variable}")
    String pathVariable(@PathVariable(name = "variable") String variable);

    @Override
    @GetMapping(path = "http://localhost:8080/http-server/header")
    String header(@RequestHeader(name = "header") String header,
            @RequestHeader(name = "headers") List<Integer> headers);

    @Override
    @GetMapping(path = "http://localhost:8080/http-server/cookie")
    String cookie(@RequestCookie(name = "cookie") String cookieValue);

    @Override
    @GetMapping(path = "http://localhost:8080/http-server/query")
    String query(@RequestQuery(name = "query") String query);

    @Override
    @PatchMapping(path = "http://localhost:8080/http-server/request-body")
    String requestBody(@RequestBody String requestBody);

    @Override
    @PutMapping(path = "http://localhost:8080/http-server/form")
    String form(@RequestForm(name = "form") String form);
}