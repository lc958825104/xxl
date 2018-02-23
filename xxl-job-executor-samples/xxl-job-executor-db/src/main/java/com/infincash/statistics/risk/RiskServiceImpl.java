package com.infincash.statistics.risk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infincash.statistics.risk.mapper.prd.TRiskRuleMapper;
import com.infincash.statistics.risk.table.prd.extend.RiskStatsDTO;

@Service
public class RiskServiceImpl implements RiskService {

	@Autowired
    private TRiskRuleMapper mapper;//这里会报错，但是并不会影响
	
	@Override
	public List<RiskStatsDTO> countRecentRisk() {
		return mapper.countRiskRule();
	}
}
