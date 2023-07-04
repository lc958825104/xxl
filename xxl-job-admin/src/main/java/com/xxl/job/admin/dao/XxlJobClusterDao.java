package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobCluster;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface XxlJobClusterDao {
    void replace(@Param("ip") String ip);

    List<XxlJobCluster> findAll();

    void delete(@Param("time") Date date);
}
