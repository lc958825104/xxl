package com.xxl.job.admin.service;

import com.xxl.job.admin.core.model.XxlJobInfo;

/**
 * @author liyong
 */
public interface JobAllocation {

    /**
     * 计算单个任务的分配
     * @param jobInfo
     */
    default void allocation(XxlJobInfo jobInfo){}

    /**
     * 用于节点起来的时候 重新计算任务的分配
     */
    default void flush(){}

    /**
     * 启动初始化相关动作
     */
    default void init(boolean init){}
}
