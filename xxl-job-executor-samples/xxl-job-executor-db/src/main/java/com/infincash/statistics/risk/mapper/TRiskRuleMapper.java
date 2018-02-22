package com.infincash.statistics.risk.mapper;

import com.infincash.statistics.risk.table.TRiskRule;

public interface TRiskRuleMapper {
    int deleteByPrimaryKey(String id);

    int insert(TRiskRule record);

    int insertSelective(TRiskRule record);

    TRiskRule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TRiskRule record);

    int updateByPrimaryKey(TRiskRule record);
}