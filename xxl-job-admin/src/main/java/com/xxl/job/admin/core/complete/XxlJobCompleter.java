package com.xxl.job.admin.core.complete;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.XxlJobCheck;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.thread.JobTriggerPoolHelper;
import com.xxl.job.admin.core.trigger.TriggerTypeEnum;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.admin.dao.XxlJobCheckDao;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xuxueli 2020-10-30 20:43:10
 */
public class XxlJobCompleter {
    private static Logger logger = LoggerFactory.getLogger(XxlJobCompleter.class);

    /**
     * common fresh handle entrance (limit only once)
     *
     * @param xxlJobLog
     * @return
     */
    public static int updateHandleInfoAndFinish(XxlJobLog xxlJobLog) {

        // finish
        finishJob(xxlJobLog);

        // text最大64kb 避免长度过长
        if (xxlJobLog.getHandleMsg().length() > 15000) {
            xxlJobLog.setHandleMsg( xxlJobLog.getHandleMsg().substring(0, 15000) );
        }

        // fresh handle
        return XxlJobAdminConfig.getAdminConfig().getXxlJobLogDao().updateHandleInfo(xxlJobLog);
    }


    /**
     * do somethind to finish job
     */
    private static void finishJob(XxlJobLog xxlJobLog){

        // 1、handle success, to trigger child job
        String triggerChildMsg = null;
        if (XxlJobContext.HANDLE_CODE_SUCCESS == xxlJobLog.getHandleCode()) {
            XxlJobInfo xxlJobInfo = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().loadById(xxlJobLog.getJobId());
            if (xxlJobInfo!=null && xxlJobInfo.getChildJobId()!=null && xxlJobInfo.getChildJobId().trim().length()>0) {
                triggerChildMsg = "<br><br><span style=\"color:#00c0ef;\" > >>>>>>>>>>>"+ I18nUtil.getString("jobconf_trigger_child_run") +"<<<<<<<<<<< </span><br>";

                String[] childJobIds = xxlJobInfo.getChildJobId().split(",");
                for (int i = 0; i < childJobIds.length; i++) {
                    int childJobId = (childJobIds[i]!=null && childJobIds[i].trim().length()>0 && isNumeric(childJobIds[i]))?Integer.valueOf(childJobIds[i]):-1;
                    if (childJobId > 0) {
                    	
                    	//save and check dependency
                    	if( ! saveAndCheckChild(xxlJobInfo.getId(), childJobId)) continue;
                    	
                        JobTriggerPoolHelper.trigger(childJobId, TriggerTypeEnum.PARENT, -1, null, null, null);
                        ReturnT<String> triggerChildResult = ReturnT.SUCCESS;

                        // add msg
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg1"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i],
                                (triggerChildResult.getCode()==ReturnT.SUCCESS_CODE?I18nUtil.getString("system_success"):I18nUtil.getString("system_fail")),
                                triggerChildResult.getMsg());
                    } else {
                        triggerChildMsg += MessageFormat.format(I18nUtil.getString("jobconf_callback_child_msg2"),
                                (i+1),
                                childJobIds.length,
                                childJobIds[i]);
                    }
                }

            }
        }

        if (triggerChildMsg != null) {
            xxlJobLog.setHandleMsg( xxlJobLog.getHandleMsg() + triggerChildMsg );
        }

        // 2、fix_delay trigger next
        // on the way

    }

  //save and check dependency
    private static boolean saveAndCheckChild(int doneJobId, int childJobId) {
    	logger.info("任务{}已完成，检查子任务{}前置", doneJobId, childJobId);
    	XxlJobCheck check=new XxlJobCheck();
    	check.setChildJobId(childJobId);
    	check.setDoneJobId(doneJobId);
		XxlJobCheckDao dao = XxlJobAdminConfig.getAdminConfig().getXxlJobCheckDao();
		dao.save(check);
		List<XxlJobCheck> done = dao.list(childJobId);
		List<String> shouldDone=getAllPreJobId(childJobId);
		for(XxlJobCheck each: done) {
			shouldDone.remove(   each.getDoneJobId()+"");
		}
		boolean result = false;
		if(shouldDone.size()==0) {
			logger.info("子任务{}前置全部完成",childJobId);
			if(dao.delete(childJobId) ==done.size()) {
				logger.info("子任务{}前置满足，将要执行",childJobId);
				result =true;
			}else {
				logger.info("子任务{}前置满足，已被执行！",childJobId);
			}
		}else {
			logger.info("子任务{}等待前置{}完成中",childJobId, shouldDone);
		}
		return result;
	}


	private static List<String> getAllPreJobId(int childJobId) {
		List<String> result = new LinkedList<>();
		List<XxlJobInfo> pageList = XxlJobAdminConfig.getAdminConfig().getXxlJobInfoDao().pageList(0, Integer.MAX_VALUE, -1, -1, null, null, null);
		for(XxlJobInfo info : pageList) {
			for(String child: info.getChildJobId().split(",")) {
				if(child.trim().equals(childJobId+"")) result.add(""+info.getId());
			}
		}
		logger.info("{}的前置任务id：{}", childJobId, result.toString() );
		return result;
	}


	private static boolean isNumeric(String str){
        try {
            int result = Integer.valueOf(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
