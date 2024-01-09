package com.xxl.job.admin.core.model;

import java.util.Date;

/**
 * xxl-job child job's pre job check
 * @author xinglj  2024
 */
public class XxlJobCheck {

	private int childJobId, doneJobId;

	public int getChildJobId() {
		return childJobId;
	}

	public void setChildJobId(int childJobId) {
		this.childJobId = childJobId;
	}

	public int getDoneJobId() {
		return doneJobId;
	}

	public void setDoneJobId(int doneJobId) {
		this.doneJobId = doneJobId;
	}


}
