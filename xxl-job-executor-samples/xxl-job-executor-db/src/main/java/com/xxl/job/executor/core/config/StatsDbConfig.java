package com.xxl.job.executor.core.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.infincash.statistics.risk.mapper.stats"}, sqlSessionFactoryRef = "sqlSessionFactoryStats")
public class StatsDbConfig {
    @Autowired
    @Qualifier("stats")
    private DataSource stats;

    @Bean
    public SqlSessionFactory sqlSessionFactoryStats() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(stats); // 使用titan数据源, 连接titan库
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateStats() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryStats()); // 使用上面配置的Factory
        return template;
    }
}