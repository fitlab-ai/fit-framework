// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.integration.mybatis;

import org.fitframework.transaction.TransactionManager;
import org.fitframework.transaction.TransactionMetadata;
import org.fitframework.transaction.TransactionPropagationPolicy;

import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;

import java.sql.Connection;
import java.util.Locale;

import javax.sql.DataSource;

/**
 * 为 {@link TransactionFactory} 提供基于 {@code fit-transaction} 组件的实现。
 *
 * @author 梁济时
 * @since 2023-06-27
 */
public class ManagedTransactionFactory implements TransactionFactory {
    private static final String TRANSACTION_NAME_PREFIX = "Mybatis";

    private final TransactionManager transactionManager;

    public ManagedTransactionFactory(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Transaction newTransaction(Connection conn) {
        return new JdbcTransaction(conn);
    }

    @Override
    public Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit) {
        TransactionMetadata metadata = TransactionMetadata.custom()
                .name(generateTransactionName())
                .isolation(isolation(level))
                .propagation(TransactionPropagationPolicy.REQUIRED)
                .readonly(false)
                .build();
        org.fitframework.transaction.Transaction transaction = this.transactionManager.begin(metadata);
        return new ManagedTransaction(transaction);
    }

    static org.fitframework.transaction.TransactionIsolationLevel isolation(TransactionIsolationLevel level) {
        if (level != null) {
            switch (level) {
                case READ_UNCOMMITTED:
                    return org.fitframework.transaction.TransactionIsolationLevel.READ_UNCOMMITTED;
                case REPEATABLE_READ:
                    return org.fitframework.transaction.TransactionIsolationLevel.REPEATABLE_READ;
                case SERIALIZABLE:
                    return org.fitframework.transaction.TransactionIsolationLevel.SERIALIZABLE;
            }
        }
        return org.fitframework.transaction.TransactionIsolationLevel.READ_COMMITTED;
    }

    private static String generateTransactionName() {
        return String.format(Locale.ROOT,
                "%s-%s-%016x",
                TRANSACTION_NAME_PREFIX,
                Thread.currentThread().getName(),
                System.currentTimeMillis());
    }
}
