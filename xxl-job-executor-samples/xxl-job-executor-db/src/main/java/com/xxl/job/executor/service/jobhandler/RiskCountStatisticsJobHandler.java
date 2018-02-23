package com.xxl.job.executor.service.jobhandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infincash.statistics.risk.RiskService;
import com.infincash.statistics.risk.table.prd.extend.RiskStatsDTO;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

/**
 * 任务Handler示例（Bean模式）
 *
 * 开发步骤： 1、继承"IJobHandler"：“com.xxl.job.core.handler.IJobHandler”；
 * 2、注册到Spring容器：添加“@Component”注解，被Spring容器扫描为Bean实例；
 * 3、注册到执行器工厂：添加“@JobHandler(value="自定义jobhandler名称")”注解，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 4、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2015-12-19 19:43:36
 */
@JobHandler(value = "risk-count-statistics")
@Component
public class RiskCountStatisticsJobHandler extends IJobHandler {
	@Autowired
	RiskService service;

	@Override
	public ReturnT<String> execute(String param) throws Exception {
		List<RiskStatsDTO> aa = service.countRecentRisk();
		for (RiskStatsDTO a:aa) {
			XxlJobLogger.log(a.getRiskRuleId() +", "+ a.getCount()+", "+ a.getTime());			
		}
		return SUCCESS;
	}
}