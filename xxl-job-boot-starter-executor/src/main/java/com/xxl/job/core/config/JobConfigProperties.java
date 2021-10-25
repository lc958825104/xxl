package com.xxl.job.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 获取定时任务相关的配置参数
 * @author cqyhm
 *
 */
@Setter@Getter@ToString
@ConfigurationProperties(prefix = JobConfigProperties.PREFIX, ignoreInvalidFields = true)
public class JobConfigProperties {
	
	public static final String PREFIX = "xxl.job";
	/**
	 * 通讯令牌
	 */
	private String accessToken;
	/**
	 * 调度器设置
	 */
	private Admin admin;
	/**
	 * 执行器设置
	 */
	private Executor executor;
	
	@Data
	public static class Admin {
		/**
		 * 调度器地址端口多个可以用逗号隔开
		 */
		private String addresses;
	}
	@Data
	public static class Executor {
		/**
		 * 执行器的名称(唯一编码,默认为当前应用程序的名称作为调度器的名称)
		 */
		private String appname;
		/**
		 * 执行器运行地址为空=ip:port
		 */
		private String address;
		/**
		 * 执行器所在的本机地址
		 */
		private String ip="";
		/**
		 * 执行器监控的端口
		 */
		private Integer port=0;
		/**
		 * 日志路径
		 */
		private String   logpath;
		/**
		 * 日志保留天数
		 */
		private Integer logretentiondays;
	}
}
