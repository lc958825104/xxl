package com.xxl.job.solon.service;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.noear.solon.cloud.service.CloudJobService;
import org.noear.solon.core.handle.Handler;

/**
 * @author noear
 * @since 1.4
 */
public class CloudJobServiceImpl implements CloudJobService {
    public static final CloudJobService instance = new CloudJobServiceImpl();

    @Override
    public boolean register(String name, Handler handler) {
        XxlJobExecutor.registJobHandler(name, new CloudJobHanderProxy(handler));
        return true;
    }
}
