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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = {"com.infincash.statistics.risk.mapper.prd"}, sqlSessionFactoryRef = "sqlSessionFactoryPrd")
public class PrdDbConfig {
	static final String MAPPER_LOCATION = "classpath:mapping/prd/*.xml";  
	
    @Autowired
    @Qualifier("prd")
    private DataSource prd;

    @Bean
    public SqlSessionFactory sqlSessionFactoryPrd() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(prd);
        factoryBean.setMapperLocations(
        	new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION)
        ); 
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplatePrd() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactoryPrd());
        return template;
    }
}