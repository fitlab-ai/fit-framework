// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.waterflow.domain.utils;

import org.fitframework.waterflow.domain.context.FlowSession;
import org.fitframework.log.Logger;

/**
 * 流程调试工具
 *
 * @author xiafei
 * @since 1.0
 */
public class FlowDebug {
    private static final Logger LOG = Logger.get(FlowDebug.class);

    private static boolean isOpen = false;

    /**
     * 打印日志信息，包含线程ID和消息内容
     *
     * @param msg 需要打印的消息内容
     */
    public static void log(String msg) {
        if (!isOpen) {
            return;
        }
        LOG.debug("Thread:{0}. {1}", Thread.currentThread().getId(), msg);
    }

    /**
     * 打印日志信息，包含线程ID、消息内容和流会话信息
     *
     * @param session 流会话信息
     * @param msg 需要打印的消息内容
     */
    public static void log(FlowSession session, String msg) {
        if (!isOpen) {
            return;
        }
        LOG.debug("Thread:{0}. tokenCount:{1}, getTosSize={2}, isComplete={3}. msg={4}",
                Thread.currentThread().getId(),
                session.getWindow().tokenCount(),
                session.getWindow().getTosSize(),
                session.getWindow().isComplete(),
                msg);
    }
}
