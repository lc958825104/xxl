package com.xxl.job.solon;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.noear.solon.SolonApp;
import org.noear.solon.cloud.CloudManager;
import org.noear.solon.cloud.annotation.CloudJob;
import com.xxl.job.solon.service.CloudJobServiceImpl;
import org.noear.solon.core.Aop;
import org.noear.solon.core.Plugin;
import org.noear.solon.core.handle.Handler;

/**
 * @author noear
 * @since 1.4
 */
public class XPluginImp implements Plugin {
    @Override
    public void start(SolonApp app) {
        if (XxljobProps.instance.getJobEnable() == false) {
            return;
        }

        //add extractor for bean method
        Aop.context().beanExtractorAdd(XxlJob.class, new ExtractorOfXxlJobMethod());
        Aop.context().beanExtractorAdd(CloudJob.class, new ExtractorOfCloudJob());

        Aop.context().beanBuilderAdd(CloudJob.class, (clz, bw, anno) -> {
            if (Handler.class.isAssignableFrom(clz)) {
                CloudJobServiceImpl.instance.register(anno.value(), bw.raw());
            } else if (IJobHandler.class.isAssignableFrom(clz)) {
                XxlJobExecutor.registJobHandler(anno.value(), bw.raw());
            }
        });

        Aop.context().beanMake(AutoConfigXxlJob.class);

        Aop.beanOnloaded(() -> {
            try {
                XxlJobExecutor executor = Aop.get(XxlJobExecutor.class);
                executor.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //登记Job服务
        CloudManager.register(CloudJobServiceImpl.instance);
    }
}
