package com.xxl.job.core.glue.impl;

import com.xxl.job.core.glue.GlueFactory;
import org.noear.solon.core.Aop;

/**
 * @author noear 2021/5/22 created
 */
public class SolonGlueFactory extends GlueFactory {
    /**
     * inject action of solon
     *
     * @param instance
     */
    @Override
    public void injectService(Object instance){
        if (instance==null) {
            return;
        }

        Aop.inject(instance);
    }
}
