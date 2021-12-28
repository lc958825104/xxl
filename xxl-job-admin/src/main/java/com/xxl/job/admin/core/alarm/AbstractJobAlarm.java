package com.xxl.job.admin.core.alarm;

import com.xxl.job.admin.core.conf.XxlJobAdminConfig;
import com.xxl.job.admin.core.util.I18nUtil;
import org.springframework.util.StringUtils;

public abstract class AbstractJobAlarm {

    protected String appendEnv(String input) {
        if (StringUtils.hasText(XxlJobAdminConfig.getAdminConfig().getEnv())) {
            return input + "(" + XxlJobAdminConfig.getAdminConfig().getEnv() + ")";
        }
        return input;
    }

    /**
     * load email job alarm template
     *
     * @return
     */
    protected String loadAlarmTemplate(int triggerCode) {
        return "<font color=\"warning\">" + appendEnv(I18nUtil.getString("jobconf_monitor_detail")) + "</font>，请相关同事注意。\n> "
                + I18nUtil.getString("jobinfo_field_jobgroup") + ":<font color=\"comment\">{0}</font>\n> "
                + I18nUtil.getString("jobinfo_field_id") + ":<font color=\"comment\">{1}</font>\n> "
                + I18nUtil.getString("jobinfo_field_jobdesc") + ":<font color=\"comment\">{2}</font>\n> "
                + I18nUtil.getString("jobconf_monitor_alarm_title") + ":<font color=\"comment\">" + getAlarmType(triggerCode) + "</font>\n> "
                + I18nUtil.getString("jobconf_monitor_alarm_content") + ":<font color=\"comment\">{3}</font>\n> ";
    }

    protected String getAlarmType(int triggerCode) {
        if (triggerCode == 200) {
            // 触发成功 即为 执行失败
            return I18nUtil.getString("jobconf_monitor_alarm_execute_fail_type");
        }
        // 触发不成功, 为调度失败
        return I18nUtil.getString("jobconf_monitor_alarm_type");
    }

}
