package com.infincash.statistics.risk;

import java.util.List;

import com.infincash.statistics.risk.table.prd.extend.RiskStatsDTO;

public interface RiskService {
	List<RiskStatsDTO> countRecentRisk();
}
