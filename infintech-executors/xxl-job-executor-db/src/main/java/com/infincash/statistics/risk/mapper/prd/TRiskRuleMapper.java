package com.infincash.statistics.risk.mapper.prd;

import java.util.List;

import com.infincash.statistics.risk.table.prd.extend.RiskStatsDTO;

public interface TRiskRuleMapper {
	List<RiskStatsDTO> countRiskRule();
}