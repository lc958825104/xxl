package com.infincash.statistics.risk;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infincash.statistics.risk.mapper.prd.TRiskRuleMapper;
import com.infincash.statistics.risk.mapper.stats.TStatsRiskDetailMapper;
import com.infincash.statistics.risk.table.prd.extend.RiskStatsDTO;

@Service
public class RiskServiceImpl implements RiskService {

	@Autowired
    private TRiskRuleMapper readMapper;//这里会报错，但是并不会影响
	
	@Autowired
	private TStatsRiskDetailMapper writeMapper;
	
	@Override
	public List<RiskStatsDTO> countRecentRisk() {
		return readMapper.countRiskRule();
	}

	@Override
	public int writeRecentRisk(List<RiskStatsDTO> list) {
		return writeMapper.insertBatch(list);
	}
}
