package com.xxl.job.admin.core.alarm.WeiUtils;


/**
 * @author change
 * @date 2022-09-22 0022 02:52:55
 */
public class WebHookMessage {
    private String webHook;
    private String msgtype;
    private Markdown markdown;

    public String getWebHook() {
        return webHook;
    }

    public void setWebHook(String webHook) {
        this.webHook = webHook;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Markdown getMarkdown() {
        return markdown;
    }

    public void setMarkdown(Markdown markdown) {
        this.markdown = markdown;
    }

    public static class Markdown {
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
