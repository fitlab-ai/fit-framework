// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.example.config;

import org.fitframework.http.client.proxy.scanner.AddressLocator;
import org.fitframework.http.client.proxy.scanner.entity.Address;
import org.fitframework.annotation.Component;

/**
 * Provides a default implementation of the AddressLocator interface.
 * This class is responsible for returning a default address configuration for HTTP requests.
 * The default address includes the protocol (http), host (localhost), and port (8080).
 *
 * @author 季聿阶
 * @since 2025-06-01
 */
@Component
public class DefaultAddressLocator implements AddressLocator {
    @Override
    public Address address() {
        Address address = new Address();
        address.setProtocol("http");
        address.setHost("localhost");
        address.setPort(8080);
        return address;
    }
}