package com.xxl.job.core.executor.impl;

import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.handler.impl.MethodJobHandler;
import org.noear.solon.SolonApp;
import org.noear.solon.core.Aop;
import org.noear.solon.core.BeanExtractor;
import org.noear.solon.core.BeanWrap;
import org.noear.solon.core.Plugin;

import java.lang.reflect.Method;

/**
 * @author noear 2021/5/22 created
 */
public class XxlJobSolonPlugin implements Plugin, BeanExtractor<XxlJob> {
    @Override
    public void start(SolonApp app) {
        //add extractor for bean method
        Aop.context().beanExtractorAdd(XxlJob.class, this);

        Aop.context().beanMake(XxlJobSolonAutoConfig.class);

        Aop.beanOnloaded(() -> {
            try {
                //
                XxlJobExecutor executor = Aop.get(XxlJobExecutor.class);

                executor.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void doExtract(BeanWrap wrap, Method method, XxlJob anno) {
        String name = anno.value();

        if (name.trim().length() == 0) {
            throw new RuntimeException("xxl-job method-jobhandler name invalid, for[" + wrap.clz() + "#" + method.getName() + "] .");
        }
        if (XxlJobExecutor.loadJobHandler(name) != null) {
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
        XxlJobExecutor.registJobHandler(name, new MethodJobHandler(wrap.raw(), method, initMethod, destroyMethod));
    }
}
