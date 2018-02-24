package com.infincash.cron.collection.mapper;

import java.util.List;

import com.infincash.cron.collection.table.TBizCollection;

public interface TBizCollectionMapper {
	List<TBizCollection> queryAll(String badDebtDay);
	
    int deleteByPrimaryKey(Long id);

    int insert(TBizCollection record);

    int insertSelective(TBizCollection record);

    TBizCollection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TBizCollection record);

    int updateByPrimaryKey(TBizCollection record);
}