package com.xxl.job.solon;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.noear.solon.SolonApp;
import org.noear.solon.cloud.CloudManager;
import org.noear.solon.cloud.annotation.CloudJob;
import com.xxl.job.solon.service.CloudJobServiceImpl;
import org.noear.solon.cloud.impl.CloudJobBuilder;
import org.noear.solon.cloud.impl.CloudJobExtractor;
import org.noear.solon.core.Aop;
import org.noear.solon.core.Plugin;

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

        //register Job Service
        CloudManager.register(CloudJobServiceImpl.instance);

        //add extractor for bean method
        Aop.context().beanExtractorAdd(XxlJob.class, new XxlJobExtractor());
        Aop.context().beanExtractorAdd(CloudJob.class, new CloudJobExtractor());
        Aop.context().beanBuilderAdd(CloudJob.class,new CloudJobBuilder());

        Aop.context().beanMake(XxlJobAutoConfig.class);

        Aop.beanOnloaded(() -> {
            try {
                XxlJobExecutor executor = Aop.get(XxlJobExecutor.class);
                executor.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
