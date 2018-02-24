package com.infincash.cron.collection.mapper;

import com.infincash.cron.collection.table.TBizCollectionRecord;

public interface CronCollectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TBizCollectionRecord record);

    int insertSelective(TBizCollectionRecord record);

    TBizCollectionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TBizCollectionRecord record);

    int updateByPrimaryKey(TBizCollectionRecord record);
}