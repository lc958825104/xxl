package com.infincash.statistics.risk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infincash.statistics.risk.mapper.TRiskRuleMapper;

@Service
public class RiskServiceImpl implements RiskService {

	@Autowired
    private TRiskRuleMapper mapper;//这里会报错，但是并不会影响
	
	@Override
	public int countRecentRisk() {
		return mapper.countRiskRule();
	}

}
