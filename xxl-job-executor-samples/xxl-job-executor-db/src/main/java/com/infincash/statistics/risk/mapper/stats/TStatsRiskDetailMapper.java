package com.infincash.statistics.risk.mapper.stats;

import java.util.List;

import com.infincash.statistics.risk.table.prd.extend.RiskStatsDTO;


public interface TStatsRiskDetailMapper {
    int insertBatch(List<RiskStatsDTO> list);
}