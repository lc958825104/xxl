package com.xxl.job.admin.controller;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;


/**
 * Created by Rock on 2018-08-17.
 */
@Component
public class LoginEntity {


    @Value("${xxl.job.login.username}")
    private String loginUserName;
    @Value("${xxl.job.login.password}")
    private String loginPassword;

    public String getLoginUserName() { return this.loginUserName;}

    public String getLoginPassword() { return  this.loginPassword;}


}
