package com.infincash.cron.collection.mapper;

import java.util.List;

import com.infincash.cron.collection.table.TBizCollectionRecord;

public interface TBizCollectionRecordMapper {
	List<TBizCollectionRecord> queryAll(String badDebtDay);
	
    int deleteByPrimaryKey(Long id);

    int insert(TBizCollectionRecord record);

    int insertSelective(TBizCollectionRecord record);

    TBizCollectionRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TBizCollectionRecord record);

    int updateByPrimaryKey(TBizCollectionRecord record);
}