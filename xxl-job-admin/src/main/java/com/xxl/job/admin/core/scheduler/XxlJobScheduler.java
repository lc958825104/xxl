package com.xxl.job.admin.core.scheduler;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.thread.*;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.core.biz.ExecutorBiz;
import com.xxl.job.core.biz.client.ExecutorBizClient;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author xuxueli 2018-10-28 00:18:17
 */

public class XxlJobScheduler  {
    private static final Logger logger = LoggerFactory.getLogger(XxlJobScheduler.class);


    public void init() throws Exception {
        // init i18n
        // TODO 国际化阻塞策略
        initI18n();

        // admin trigger pool start
        // TODO 初始化 fastTriggerPool（用于 执行任务（步骤四、步骤7） 、slowTriggerPool（用于 执行慢任务（步骤四、步骤7）） 线程池
        //TODO 注意最大线程数 后面会用于 计算一次循环从数据库拉取的任务数
        JobTriggerPoolHelper.toStart();

        // admin registry monitor run
        //TODO 1.初始化registryOrRemoveThreadPool（用于 客户端注册） 2.开启 registryMonitorThread （用于扫描 有没有对应执行器的 客户端地址 每30秒扫描一次
        JobRegistryHelper.getInstance().start();

        // admin fail-monitor run
        //TODO 步骤4 开启线程 扫描异常任务 重试机制
        JobFailMonitorHelper.getInstance().start();

        // admin lose-monitor run ( depend on JobTriggerPoolHelper )
        //TODO 初始化 callbackThreadPool（用于接收客户端执行状态） 线程池  启动 monitorThread 处理长期处于进行中的任务
        JobCompleteHelper.getInstance().start();

        // admin log report start
        //todo 开启 logrThread 统计报表相关 间隔一分钟
        //TODO 每次统计都是统计一天的 数据量过大时会影响效率 可以做成增量的   在获取结果时异步 统计 或者每次只统计1分钟的
        JobLogReportHelper.getInstance().start();

        // start-schedule  ( depend on JobTriggerPoolHelper )
        //TODO 步骤7 任务的预处理 scheduleThread  ringThread
        JobScheduleHelper.getInstance().start();

        logger.info(">>>>>>>>> init xxl-job admin success.");
    }

    
    public void destroy() throws Exception {

        // stop-schedule
        JobScheduleHelper.getInstance().toStop();

        // admin log report stop
        JobLogReportHelper.getInstance().toStop();

        // admin lose-monitor stop
        JobCompleteHelper.getInstance().toStop();

        // admin fail-monitor stop
        JobFailMonitorHelper.getInstance().toStop();

        // admin registry stop
        JobRegistryHelper.getInstance().toStop();

        // admin trigger pool stop
        JobTriggerPoolHelper.toStop();

    }

    // ---------------------- I18n ----------------------

    private void initI18n(){
        for (ExecutorBlockStrategyEnum item:ExecutorBlockStrategyEnum.values()) {
            item.setTitle(I18nUtil.getString("jobconf_block_".concat(item.name())));
        }
    }

    // ---------------------- executor-client ----------------------
    private static ConcurrentMap<String, ExecutorBiz> executorBizRepository = new ConcurrentHashMap<String, ExecutorBiz>();
    public static ExecutorBiz getExecutorBiz(String address) throws Exception {
        // valid
        if (address==null || address.trim().length()==0) {
            return null;
        }

        // load-cache
        address = address.trim();
        ExecutorBiz executorBiz = executorBizRepository.get(address);
        if (executorBiz != null) {
            return executorBiz;
        }

        // set-cache
        executorBiz = new ExecutorBizClient(address, XxlJobAdminConfig.getAdminConfig().getAccessToken());

        executorBizRepository.put(address, executorBiz);
        return executorBiz;
    }

}
