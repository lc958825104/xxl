package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobCheck;
import com.xxl.job.admin.core.model.XxlJobLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * job check
 */
@Mapper
public interface XxlJobCheckDao {

	// exist jobId not use jobGroup, not exist use jobGroup
	public List<XxlJobCheck> list(@Param("childJobId") int childJobId);

	public long save(XxlJobCheck xxlJobCheck);
	
	public int delete(@Param("childJobId") int childJobId);
 

}
