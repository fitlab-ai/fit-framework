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
import org.fitframework.http.annotation.RequestAddress;
import org.fitframework.http.annotation.RequestBean;
import org.fitframework.http.annotation.RequestBody;
import org.fitframework.http.annotation.RequestCookie;
import org.fitframework.http.annotation.RequestForm;
import org.fitframework.http.annotation.RequestHeader;
import org.fitframework.http.annotation.RequestMapping;
import org.fitframework.http.annotation.RequestQuery;

import java.util.List;

/**
 * This interface defines a set of methods for testing HTTP client proxy functionality.
 * It extends the TestInterface and provides specific annotations for configuring the HTTP request details.
 * The interface is marked with @HttpProxy to indicate it's a proxy for HTTP requests.
 * The @RequestAddress annotation specifies the base URL and port for the requests.
 * The @RequestMapping annotation sets the base path for all methods in this interface.
 *
 * @author 季聿阶
 * @since 2025-06-01
 */
@HttpProxy
@RequestAddress(protocol = "http", host = "localhost", port = "8080")
@RequestMapping(path = "/http-server")
public interface TestRequestAddress extends TestInterface {
    @Override
    @PostMapping(path = "/request-bean")
    Education requestBean(@RequestBean Education education);

    @Override
    @GetMapping(path = "/path-variable/{variable}")
    String pathVariable(@PathVariable(name = "variable") String variable);

    @Override
    @GetMapping(path = "/header")
    String header(@RequestHeader(name = "header") String header,
            @RequestHeader(name = "headers") List<Integer> headers);

    @Override
    @GetMapping(path = "/cookie")
    String cookie(@RequestCookie(name = "cookie") String cookieValue);

    @Override
    @GetMapping(path = "/query")
    String query(@RequestQuery(name = "query") String query);

    @Override
    @PatchMapping(path = "/request-body")
    String requestBody(@RequestBody String requestBody);

    @Override
    @PutMapping(path = "/form")
    String form(@RequestForm(name = "form") String form);
}