package com.xxl.job.adminbiz;

import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.client.AdminBizClient;
import com.xxl.job.core.biz.model.HandleCallbackParam;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.enums.RegistryConfig;
import com.xxl.job.core.util.GsonTool;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * admin api test
 *
 * @author xuxueli 2017-07-28 22:14:52
 */
public class AdminBizTest {

    // admin-client
    private static String addressUrl = "http://127.0.0.1:8080/xxl-job-admin/";
    private static String accessToken = null;


    @Test
    public void callback() throws Exception {
        AdminBiz adminBiz = new AdminBizClient(addressUrl, accessToken);

        HandleCallbackParam param = new HandleCallbackParam();
        param.setLogId(1);
        param.setHandleCode(XxlJobContext.HANDLE_CODE_SUCCESS);

        List<HandleCallbackParam> callbackParamList = Arrays.asList(param);

        ReturnT<String> returnT = adminBiz.callback(callbackParamList);

        assertTrue(returnT.getCode() == ReturnT.SUCCESS_CODE);
    }

    /**
     * registry executor
     *
     * @throws Exception
     */
    @Test
    public void registry() throws Exception {
        AdminBiz adminBiz = new AdminBizClient(addressUrl, accessToken);

        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), "xxl-job-executor-example", "127.0.0.1:9999");
        ReturnT<String> returnT = adminBiz.registry(registryParam);

        assertTrue(returnT.getCode() == ReturnT.SUCCESS_CODE);
    }

    /**
     * registry executor remove
     *
     * @throws Exception
     */
    @Test
    public void registryRemove() throws Exception {
        AdminBiz adminBiz = new AdminBizClient(addressUrl, accessToken);

        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), "xxl-job-executor-example", "127.0.0.1:9999");
        ReturnT<String> returnT = adminBiz.registryRemove(registryParam);

        assertTrue(returnT.getCode() == ReturnT.SUCCESS_CODE);

    }

    /**
     *
     * {@link com.xxl.job.admin.controller.JobApiController#api(HttpServletRequest, String, String)}
     * if ("callback".equals(uri))
     *
     *  ERROR c.x.j.a.c.r.WebExceptionResolver - WebExceptionResolver:{}
     * java.lang.NullPointerException: null
     *         at com.xxl.job.admin.service.impl.AdminBizImpl.callback(AdminBizImpl.java:67)
     *         at com.xxl.job.admin.service.impl.AdminBizImpl.callback(AdminBizImpl.java:47)
     *         at com.xxl.job.admin.controller.JobApiController.api(JobApiController.java:59)
     *
     * @throws Exception
     */
    @Test
    public void callbackNullException() throws Exception {
        String data1 = "";

        List<HandleCallbackParam> callbackParamList1 = GsonTool.fromJson(data1, List.class, HandleCallbackParam.class);

        try {
            for (HandleCallbackParam handleCallbackParam : callbackParamList1) {
                System.out.println("item:"+handleCallbackParam);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
