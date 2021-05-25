package com.xxl.job.solon;

import com.xxl.job.core.executor.XxlJobExecutor;
import org.noear.solon.cloud.annotation.CloudJob;
import com.xxl.job.solon.service.CloudJobServiceImpl;
import org.noear.solon.core.BeanExtractor;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.handle.Action;

import java.lang.reflect.Method;

/**
 * @author noear
 * @since 1.4
 */
class ExtractorOfCloudJob implements BeanExtractor<CloudJob> {
    @Override
    public void doExtract(BeanWrap bw, Method method, CloudJob anno) {
        String name = anno.value();

        if (name.trim().length() == 0) {
            throw new RuntimeException("xxl-job method-jobhandler name invalid, for[" + bw.clz() + "#" + method.getName() + "] .");
        }
        if (XxlJobExecutor.loadJobHandler(name) != null) {
            throw new RuntimeException("xxl-job jobhandler[" + name + "] naming conflicts.");
        }

        method.setAccessible(true);

        Action action = new Action(bw,  method);

        // registry jobhandler
        CloudJobServiceImpl.instance.register(name, action);
    }
}
