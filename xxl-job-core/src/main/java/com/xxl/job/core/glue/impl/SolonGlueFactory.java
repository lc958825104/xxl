package com.xxl.job.core.glue.impl;

import com.xxl.job.core.glue.GlueFactory;
import org.noear.solon.Solon;
import org.noear.solon.core.Aop;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author noear 2021/5/22 created
 */
public class SolonGlueFactory extends GlueFactory {
    private static Logger logger = LoggerFactory.getLogger(SolonGlueFactory.class);


    /**
     * inject action of solon
     * @param instance
     */
    @Override
    public void injectService(Object instance){
        if (instance==null) {
            return;
        }

        if (Solon.global() == null) {
            return;
        }

        Aop.inject(instance);
    }
}
