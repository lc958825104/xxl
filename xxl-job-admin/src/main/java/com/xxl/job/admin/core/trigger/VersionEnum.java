package com.xxl.job.admin.core.trigger;

/**
 * @Description TODO
 * @Author 姚仲杰#80998699
 * @Date 2022/1/4 16:45
 */
public enum VersionEnum {
    DEFAULT("default"),
    MASTER("master");
    
    private String version;
    VersionEnum(String version){
        this.version=version;
    }
    public String get(){
        return version;
    }
    
}
