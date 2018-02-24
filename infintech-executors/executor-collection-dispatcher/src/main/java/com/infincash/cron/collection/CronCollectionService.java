package com.infincash.cron.collection;

import java.util.List;

public interface CronCollectionService {
	/**
	 * 查询全部未分配订单
	 * @return
	 */
	List<Collection> readCollection();
	
	/**
	 * 分配待催收订单
	 * @return
	 */
	int assignCollection(List<Collection> list);
	
	/**
	 * 离职员工催收订单分配
	 * @return
	 */
	int assignExemployeeCollection();
}
