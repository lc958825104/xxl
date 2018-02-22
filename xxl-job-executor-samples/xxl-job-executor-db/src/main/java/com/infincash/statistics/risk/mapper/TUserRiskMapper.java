package com.infincash.statistics.risk.mapper;

import com.infincash.statistics.risk.table.TUserRisk;

public interface TUserRiskMapper {
    int deleteByPrimaryKey(String id);

    int insert(TUserRisk record);

    int insertSelective(TUserRisk record);

    TUserRisk selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TUserRisk record);

    int updateByPrimaryKey(TUserRisk record);
}