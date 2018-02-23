package com.infincash.statistics.risk.mapper.stats;

import java.util.List;

import com.infincash.statistics.risk.table.stats.TStatsRiskDetail;

public interface TStatsRiskDetailMapper {
    int insertBatch(List<TStatsRiskDetail> record);
}