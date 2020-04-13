package com.xxl.job.admin.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.xxl.job.admin.core.model.XxlJobGroup;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface XxlJobGroupDao {

    public List<XxlJobGroup> findAll();

    public List<XxlJobGroup> findByAddressType(@Param("addressType") int addressType);

    public int save(XxlJobGroup xxlJobGroup);

    public int update(XxlJobGroup xxlJobGroup);

    public int remove(@Param("id") int id);

    public XxlJobGroup load(@Param("id") int id);

    /**
     * 刷新执行器地址
     * 
     * @param appName 执行器/应用
     * @param timeout 超时时间
     * @return 影响行数
     */
    @Update("UPDATE xxl_job_group gp\n" + "LEFT JOIN (\n" + "\tSELECT registry_key\n"
        + "\t\t,GROUP_CONCAT(DISTINCT registry_value ORDER BY registry_value SEPARATOR ',') address_list\n"
        + "\tFROM `xxl_job_registry` \n" + "\tWHERE registry_group='EXECUTOR' AND registry_key=#{appName}\n"
        + "\t\tAND update_time>= DATE_ADD(NOW(),INTERVAL -#{timeout} SECOND)\n"
        + ") reg ON gp.app_name=reg.registry_key\n" + "SET gp.address_list=reg.address_list\n"
        + "WHERE gp.app_name=#{appName} AND gp.address_type=0")
    Integer refreshGroupByApp(@Param("appName") String appName, @Param("timeout") Integer timeout);

}
