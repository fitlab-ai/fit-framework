// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.fel.engine.operators.patterns;

import org.fitframework.fel.core.pattern.Pattern;
import org.fitframework.fel.engine.flows.AiProcessFlow;
import org.fitframework.fel.engine.flows.ConverseLatch;
import org.fitframework.fel.engine.util.AiFlowSession;
import org.fitframework.waterflow.domain.context.FlowSession;
import org.fitframework.waterflow.domain.context.Window;
import org.fitframework.waterflow.domain.emitters.EmitterListener;
import org.fitframework.waterflow.domain.emitters.FlowEmitter;
import org.fitframework.waterflow.domain.flow.Flow;
import org.fitframework.inspection.Validation;
import org.fitframework.util.LazyLoader;
import org.fitframework.util.ObjectUtils;

/**
 * 流程委托单元。
 *
 * @author 刘信宏
 * @since 2024-06-04
 */
public abstract class AbstractFlowPattern<I, O> implements FlowPattern<I, O> {
    private static final String RESULT_ACTION_KEY = "resultAction";
    private static final String PARENT_SESSION_ID_KEY = "parentSessionId";

    private final LazyLoader<AiProcessFlow<I, O>> flowSupplier;
    private final EmitterListener<O, FlowSession> dataDispatcher = (data, session) -> {
        Object rawResultAction = session.getInnerState(RESULT_ACTION_KEY);
        if (rawResultAction == null) {
            return;
        }
        ResultAction<O> resultAction = ObjectUtils.cast(rawResultAction);
        resultAction.process(data, session);
    };

    /**
     * Constructor.
     */
    protected AbstractFlowPattern() {
        this.flowSupplier = LazyLoader.of(() -> {
            AiProcessFlow<I, O> flow = buildFlow();
            flow.register(this.dataDispatcher);
            return flow;
        });
    }

    /**
     * 构造处理流程。
     *
     * @return 表示数据处理流程的 {@code <}{@link AiProcessFlow}{@code <}{@link I}{@code , }{@link O}{@code >}。
     */
    protected abstract AiProcessFlow<I, O> buildFlow();

    @Override
    public void register(EmitterListener<O, FlowSession> handler) {
        this.getFlow().register(handler);
    }

    @Override
    public void unregister(EmitterListener<O, FlowSession> listener) {
        this.getFlow().unregister(listener);
    }

    @Override
    public void emit(O data, FlowSession session) {
        this.getFlow().emit(data, session);
    }

    @Override
    public FlowEmitter<O> invoke(I data) {
        FlowEmitter<O> emitter = new FlowEmitter.AutoCompleteEmitter<>();
        FlowSession flowSession = buildFlowSession(emitter);
        this.getFlow().converse(flowSession).offer(data);
        return emitter;
    }

    /**
     * 获取同步委托单元。
     *
     * @return 表示同步委托单元的 {@link Pattern}{@code <}{@link I}{@code , }{@link O}{@code >}。
     * @throws IllegalStateException 当流程发生异常时。
     */
    public Pattern<I, O> sync() {
        return new SimplePattern<>(data -> {
            FlowSession require = AiFlowSession.require();
            FlowSession session = new FlowSession(require.preserved());
            Window window = session.begin();
            session.copySessionState(require);
            ConverseLatch<O> conversation = this.getFlow().converse(session).offer(data);
            window.complete();
            return conversation.await();
        });
    }

    /**
     * 获取被装饰的流程对象。
     *
     * @return 表示被装饰流程对象的 {@link Flow}{@code <}{@link I}{@code >}。
     */
    public Flow<I> origin() {
        return this.getFlow().origin();
    }

    /**
     * Built the flow session for starting the conversation.
     *
     * @param emitter The {@link FlowEmitter}{@code <}{@link O}{@code >} representing output emitter.
     * @param <O> The output data type.
     * @return The new {@link FlowSession}.
     */
    protected static <O> FlowSession buildFlowSession(FlowEmitter<O> emitter) {
        FlowSession mainSession = AiFlowSession.require();
        FlowSession flowSession = FlowSession.newRootSession(mainSession, mainSession.preserved());
        flowSession.setInnerState(PARENT_SESSION_ID_KEY, mainSession.getId());
        ResultAction<O> resultAction = emitter::emit;
        flowSession.setInnerState(RESULT_ACTION_KEY, resultAction);
        return flowSession;
    }

    private AiProcessFlow<I, O> getFlow() {
        return Validation.notNull(this.flowSupplier.get(), "The flow cannot be null.");
    }

    /**
     * A functional interface defining an action to be performed with processed results.
     * Implementations handle both the result data and its associated flow session context.
     *
     * @param <O> The type of result data to be processed.
     */
    protected interface ResultAction<O> {
        /**
         * Process the result.
         *
         * @param data The result of {@link O}.
         * @param flowSession The result flow session of {@link FlowSession}.
         */
        void process(O data, FlowSession flowSession);
    }
}
