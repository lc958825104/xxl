package com.infincash.cron.collection.mapper;

import java.util.List;
import java.util.Map;

import com.infincash.cron.collection.table.TBizCollection;

public interface TBizCollectionMapper {
	List<TBizCollection> queryAll(String badDebtDay);

	List<Map<String, Object>> queryUserByRoleId(String roleId);
	
    int insertBatch(List<TBizCollection> record);

    int updateByPrimaryKeySelective(TBizCollection record);

    int updateByPrimaryKey(TBizCollection record);
}