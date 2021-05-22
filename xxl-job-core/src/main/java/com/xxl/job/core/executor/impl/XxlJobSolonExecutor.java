package com.xxl.job.core.executor.impl;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.glue.GlueFactory;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import org.noear.solon.SolonApp;
import org.noear.solon.core.Aop;
import org.noear.solon.core.BeanExtractor;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * xxl-job executor (for solon)
 *
 * @author noear 2021/5/22 created
 */
public class XxlJobSolonExecutor extends XxlJobExecutor implements Plugin, BeanExtractor<XxlJob> {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobSolonExecutor.class);


    @Override
    public void start(SolonApp app) {
        // init JobHandler Repository (for method)
        Aop.context().beanExtractorAdd(XxlJob.class, this);

        // refresh GlueFactory
        GlueFactory.refreshInstance(2);

        // super start
        try {
            super.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // destroy
    @Override
    public void destroy() {
        super.destroy();
    }


    @Override
    public void doExtract(BeanWrap wrap, Method method, XxlJob anno) {
        String name = anno.value();

        if (name.trim().length() == 0) {
            throw new RuntimeException("xxl-job method-jobhandler name invalid, for[" + wrap.clz() + "#" + method.getName() + "] .");
        }
        if (loadJobHandler(name) != null) {
            throw new RuntimeException("xxl-job jobhandler[" + name + "] naming conflicts.");
        }


        method.setAccessible(true);

        // init and destory
        Method initMethod = null;
        Method destroyMethod = null;

        if (anno.init().trim().length() > 0) {
            try {
                initMethod = wrap.clz().getDeclaredMethod(anno.init());
                initMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("xxl-job method-jobhandler initMethod invalid, for[" + wrap.clz() + "#" + method.getName() + "] .");
            }
        }
        if (anno.destroy().trim().length() > 0) {
            try {
                destroyMethod = wrap.clz().getDeclaredMethod(anno.destroy());
                destroyMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("xxl-job method-jobhandler destroyMethod invalid, for[" + wrap.clz() + "#" + method.getName() + "] .");
            }
        }

        // registry jobhandler
        registJobHandler(name, new MethodJobHandler(wrap.raw(), method, initMethod, destroyMethod));
    }
}