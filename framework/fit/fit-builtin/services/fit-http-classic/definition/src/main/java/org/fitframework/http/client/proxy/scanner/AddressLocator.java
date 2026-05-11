// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.http.client.proxy.scanner;

import org.fitframework.http.client.proxy.scanner.entity.Address;

/**
 * Defines a contract for locating and providing address information for HTTP requests.
 * Implementations of this interface are responsible for returning an {@link Address} object
 * that contains details such as protocol, host, port, and locator class.
 *
 * @author 王攀博
 * @since 2025-01-24
 */
public interface AddressLocator {
    /**
     * Retrieves the address information for an HTTP request.
     *
     * @return The address information as an {@link Address} object.
     */
    Address address();
}