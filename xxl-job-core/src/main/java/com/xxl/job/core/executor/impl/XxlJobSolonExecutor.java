package com.xxl.job.core.executor.impl;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.glue.GlueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xxl-job executor (for solon)
 *
 * @author noear 2021/5/22 created
 */
public class XxlJobSolonExecutor extends XxlJobExecutor {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobSolonExecutor.class);

    @Override
    public void start() throws Exception {
        // refresh GlueFactory (add solon inject)
        GlueFactory.refreshInstance(2);

        // super start
        super.start();
    }

    // destroy
    @Override
    public void destroy() {
        super.destroy();
    }
}