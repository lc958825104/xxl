package com.infincash.cron.collection;

public interface CronCollectionService {
	/**
	 * 查询全部未分配订单
	 * 分配待催收订单
	 * @return
	 * @throws InfintechException 
	 */
	void assignCollection() throws InfintechException;
	
	/**
	 * 离职员工催收订单分配
	 * @return
	 */
	int assignExemployeeCollection();
}
