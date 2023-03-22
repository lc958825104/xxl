package com.xxl.job.admin.core.alarm.impl;

import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.alarm.WeiUtils.WebHookMessage;
import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.core.util.I18nUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.DateUtil;
import com.xxl.job.core.util.GsonTool;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 发送企业微信机器人消息
 *
 * @author change
 * @date 2023-03-22 0022 11:20:30
 */
@Component
public class WeiJobAlarm implements JobAlarm {
    private static final Logger logger = LoggerFactory.getLogger(WeiJobAlarm.class);

    /**
     * fail alarm
     *
     * @param jobLog
     */
    @Override
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog) {
        boolean alarmResult = true;

        // send monitor webhook
        if (info != null && info.getAlarmWei() != null && info.getAlarmWei().trim().length() > 0) {

            // alarmContent
            String alarmContent = "Alarm Job LogId=" + jobLog.getId();
            if (jobLog.getTriggerCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "\nTriggerMsg=\n" + jobLog.getTriggerMsg().replaceAll("<br>", "|| ");
            }
            if (jobLog.getHandleCode() > 0 && jobLog.getHandleCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "\nHandleCode=" + jobLog.getHandleMsg();
            }

            //  info
            XxlJobGroup group = XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().load(info.getJobGroup());

            String content = loadEmailJobAlarmTemplate(info.getJobDesc(), group.getTitle(), info.getId(), alarmContent);

            String webhookUrl = info.getAlarmWei().trim();

            WebHookMessage message = new WebHookMessage();
            message.setMsgtype("markdown");
            WebHookMessage.Markdown markdown = new WebHookMessage.Markdown();
            markdown.setContent(content);
            message.setMarkdown(markdown);
            ReturnT returnT = XxlJobRemotingUtil.postBody(webhookUrl, null, 3, message, String.class);
            logger.info("请求结果:{}", GsonTool.toJson(returnT));
        }

        return alarmResult;
    }

    /**
     * 企业微信机器人消息模板
     *
     * @return 模板
     */
    private static String loadEmailJobAlarmTemplate(String jobDesc, String jobGroup, int taskId, String content) {
        return "# " + jobDesc + "\n"
                + "> 时间: " + DateUtil.formatDateTime(new Date()) + "\n"
                + "> `**事项详情**` \n"
                //执行器
                + "> " + I18nUtil.getString("jobinfo_field_jobgroup") + "：<font color=\"comment\">" + jobGroup + "</font> \n"
                //任务id
                + "> " + I18nUtil.getString("jobinfo_field_id") + "：<font color=\"warning\">" + taskId + "</font>\n"
                //告警类型
                + "> " + I18nUtil.getString("jobconf_monitor_alarm_title") + "：<font color=\"comment\">" + I18nUtil.getString("jobconf_monitor_alarm_type") + "</font>\n"
                //告警内容
                + "> " + I18nUtil.getString("jobconf_monitor_alarm_content") + "：\n<font color=\"info\">" + content + "</font> \n";
    }

}
