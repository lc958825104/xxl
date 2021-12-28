package com.xxl.job.admin.core.alarm.impl;

import com.xxl.job.admin.core.alarm.AbstractJobAlarm;
import com.xxl.job.admin.core.alarm.JobAlarm;
import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.model.XxlJobGroup;
import com.xxl.job.admin.core.model.XxlJobInfo;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.util.XxlJobRemotingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Order(1)
@Component
public class WechatJobAlarm extends AbstractJobAlarm implements JobAlarm {

    private static Logger logger = LoggerFactory.getLogger(WechatJobAlarm.class);

    @Override
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog) {
        boolean alarmResult = true;

        // send monitor email
        if (info != null && XxlJobAdminConfig.getAdminConfig().isEnableWeChatAlarm() && StringUtils.hasText(XxlJobAdminConfig.getAdminConfig().getWechatHook())) {

            // alarmContent
            String alarmContent = "Alarm Job LogId=" + jobLog.getId();
            if (jobLog.getTriggerCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "\nTriggerMsg=" + jobLog.getTriggerMsg();
            }
            if (jobLog.getHandleCode() > 0 && jobLog.getHandleCode() != ReturnT.SUCCESS_CODE) {
                alarmContent += "\nHandleCode=" + jobLog.getHandleMsg();
            }

            // email info
            XxlJobGroup group = XxlJobAdminConfig.getAdminConfig().getXxlJobGroupDao().load(info.getJobGroup());
            String s = alarmContent.replaceAll("<br><br>", "\n")
                    .replaceAll("<br>", "\n")
                    .replaceAll("<span style=\"color:#00c0ef;\" >", "")
                    .replaceAll("</span>", "");
            String result = Arrays.asList(s.split("\n")).stream().map(input -> "<font color=\"comment\">" + input + "</font>").collect(Collectors.joining("\n"));
            String content = MessageFormat.format(loadAlarmTemplate(jobLog.getTriggerCode()),
                    group != null ? group.getTitle() : "null",
                    info.getId(),
                    info.getJobDesc(),
                    result);

            // make mail
            try {
                Map<String, Object> body = new HashMap<>();
                body.put("msgtype", "markdown");
                Map<String, Object> markdownContent = new HashMap<>();
                markdownContent.put("content", content);
                body.put("markdown", markdownContent);
                XxlJobRemotingUtil.postBody(XxlJobAdminConfig.getAdminConfig().getWechatHook(), null, 5, body, String.class);
            } catch (Exception e) {
                logger.error(">>>>>>>>>>> xxl-job, job fail alarm wechat send error, JobLogId:{}", jobLog.getId(), e);
                alarmResult = false;
            }
        }

        return alarmResult;
    }
}
