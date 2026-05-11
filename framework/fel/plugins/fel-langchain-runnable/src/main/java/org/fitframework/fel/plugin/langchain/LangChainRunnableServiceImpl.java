// SPDX-License-Identifier: MIT
// Copyright (c) 2025 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.plugin.langchain;

import org.fitframework.fel.service.langchain.LangChainRunnableService;
import org.fitframework.annotation.Component;
import org.fitframework.annotation.Fitable;
import org.fitframework.broker.client.BrokerClient;
import org.fitframework.broker.client.filter.route.FitableIdFilter;
import org.fitframework.conf.runtime.SerializationFormat;
import org.fitframework.inspection.Validation;

import java.util.concurrent.TimeUnit;

/**
 * LangChain Runnable 算子服务的实现。
 *
 * @author 刘信宏
 * @since 2024-06-12
 */
@Component
public class LangChainRunnableServiceImpl implements LangChainRunnableService {
    private static final int INVOKE_TIMEOUT = 30000;

    private final BrokerClient brokerClient;

    public LangChainRunnableServiceImpl(BrokerClient brokerClient) {
        this.brokerClient = Validation.notNull(brokerClient, "The broker client cannot be null.");
    }

    @Override
    @Fitable("org.fitframework.fel.plugin.langchain.runnable.invoke")
    public Object invoke(String taskId, String fitableId, Object input) {
        return this.brokerClient.getRouter(Validation.notBlank(taskId, "The task id cannot be blank."))
                .route(new FitableIdFilter(Validation.notBlank(fitableId, "The fitable id cannot be blank.")))
                .format(SerializationFormat.CBOR)
                .timeout(INVOKE_TIMEOUT, TimeUnit.MILLISECONDS)
                .invoke(Validation.notNull(input, "The input data cannot be null."));
    }
}
