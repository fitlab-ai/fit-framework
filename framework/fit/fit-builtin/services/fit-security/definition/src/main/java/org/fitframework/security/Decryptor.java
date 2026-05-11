// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security;

/**
 * 表示解密服务。
 *
 * @author 季聿阶
 * @since 2023-07-31
 */
public interface Decryptor {
    /** 表示通用待解密密文的前缀。 */
    String CIPHER_PREFIX = "enc(";

    /** 表示通用待解密密文的后缀。 */
    String CIPHER_SUFFIX = ")";

    /**
     * 将指定密文进行解密。
     *
     * @param encrypted 表示待解密的密文的 {@link String}。
     * @return 表示解密后的明文的 {@link String}。
     */
    String decrypt(String encrypted);
}
