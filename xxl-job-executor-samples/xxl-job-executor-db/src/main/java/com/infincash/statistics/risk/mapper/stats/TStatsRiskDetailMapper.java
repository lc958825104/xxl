package com.infincash.statistics.risk.mapper.stats;

import com.infincash.statistics.risk.table.stats.TStatsRiskDetail;

public interface TStatsRiskDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TStatsRiskDetail record);

    int insertSelective(TStatsRiskDetail record);

    TStatsRiskDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TStatsRiskDetail record);

    int updateByPrimaryKey(TStatsRiskDetail record);
}