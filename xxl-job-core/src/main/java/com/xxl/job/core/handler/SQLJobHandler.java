package com.xxl.job.core.handler;

import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.IJobHandler;

/**
 * glue job handler
 *
 * @author caigua 2022-11-11 16:29:45
 */
public abstract class SQLJobHandler extends IJobHandler {


	private long glueUpdatetime;

	// 具体sql实现
	private SQLJobHandler jobHandler;
	private String[] sqlString;

	public SQLJobHandler(){
	}

	public SQLJobHandler(SQLJobHandler jobHandler, long glueUpdatetime, String sqlSourcesString) {
		this.jobHandler = jobHandler;
		this.glueUpdatetime = glueUpdatetime;
		this.sqlString = sqlSourcesString.split("\n");

	}
	public long getGlueUpdatetime() {
		return glueUpdatetime;
	}

	@Override
	public void execute() throws Exception {
		XxlJobHelper.log("----------- glue.version:"+ glueUpdatetime +" -----------");
		execSql(sqlString);
	}

	/**
	 * sql执行
	 * @param sql
	 */
	public abstract void execSql(String sql[]);

}
