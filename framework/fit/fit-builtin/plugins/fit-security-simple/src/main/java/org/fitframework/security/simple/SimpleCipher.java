// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.security.simple;

import org.fitframework.security.Decryptor;
import org.fitframework.security.Encryptor;
import org.fitframework.annotation.Component;
import org.fitframework.util.StringUtils;

/**
 * 表示 {@link Decryptor} 的简单实现。
 *
 * @author 季聿阶
 * @since 2023-07-31
 */
@Component
public class SimpleCipher implements Encryptor, Decryptor {
    @Override
    public String encrypt(String decrypted) {
        return CIPHER_PREFIX + decrypted + CIPHER_SUFFIX;
    }

    @Override
    public String decrypt(String encrypted) {
        if (StringUtils.startsWithIgnoreCase(encrypted, CIPHER_PREFIX) && StringUtils.endsWithIgnoreCase(encrypted, CIPHER_SUFFIX)) {
            return encrypted.substring(CIPHER_PREFIX.length(), encrypted.length() - CIPHER_SUFFIX.length());
        }
        return encrypted;
    }
}
