package com.infincash.cron.collection.mapper;

import java.util.List;

import com.infincash.cron.collection.table.TBizCollectionOverdueBucket;

public interface TBizCollectionOverdueBucketMapper {
	List<TBizCollectionOverdueBucket> queryAll();
}