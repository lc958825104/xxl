package com.xxl.job.executor.service.jobhandler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.SQLJobHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * glue job handler
 *
 * @author caigua 2022-11-11 16:29:45
 */

@Component
public class MYSQLJobHandler extends SQLJobHandler {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    MYSQLJobHandler() {
        super();
    }


    @Override
    public void execSql(String sql[]) {

        boolean contains_select = false;

        for (String s : sql) {
            if (s.trim().toLowerCase().startsWith("select")) {
                contains_select = true;
                break;
            }
        }

        if (contains_select) {
            // 存在查询sql 一条一条执行
            for (String s : sql) {
                if (s.trim().toLowerCase().startsWith("select")) {

                    XxlJobHelper.log("exec sql : {} \n result: {}", s, jdbcTemplate.queryForList(s));


                } else {
                    XxlJobHelper.log("exec sql : {}  \n rows:{}", s, jdbcTemplate.update(s));
                }
            }

        } else {

            int rows[] = jdbcTemplate.batchUpdate(sql);
            XxlJobHelper.log("exec rows:{}", rows);

        }


    }
}
