package com.xxl.job.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 读取应用的配置参数
 * @author cqyhm
 *
 */
@Setter@Getter@ToString
@ConfigurationProperties(prefix = ApplicationProperties.PREFIX, ignoreInvalidFields = true)
public class ApplicationProperties {
	/**
	 * 配置前缀
	 */
	public static final String PREFIX = "spring.application";
	/**
	 * 应用程序名称
	 */
	private String name;
}
