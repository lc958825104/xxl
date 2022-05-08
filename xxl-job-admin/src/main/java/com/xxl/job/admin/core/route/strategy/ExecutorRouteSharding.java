package com.xxl.job.admin.core.route.strategy;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.biz.model.TriggerParam;

import java.util.List;

/**
 * <p>
 * 分片规则的-路由器-直接采取的是随机路由算法
 * </p>
 *
 * @author daiqi
 * @since 2022/5/8 18:23
 */
public class ExecutorRouteSharding extends ExecutorRouteBusyover {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return super.route(triggerParam, addressList);
    }

}
