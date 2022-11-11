package com.xxl.job.executor.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * glue job handler
 *
 * @author caigua 2022-11-11 16:29:45
 */

@Configuration
public class JdbcConfig {
    @Autowired
    private DataSource dataSource;

    @Bean({"jdbcTemplate"})
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(this.dataSource);
    }
}
