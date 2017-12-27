# xxl-job-executor-sample-nutzboot

用NutzBoot作为xxl-job-executor的示例

## 文件介绍

* MainLauncher.java NutzBoot启动类, 其中的init方法,扫描/加载/注册ioc容器内的IJobHandler
* XxlJobConfig.java 读取配置信息,声明XxlJobExecutor对象
* ShardingJobHandler.java和DemoJobHandler.java 2个示例IJobHandler实现类

## 环境要求

* JDK8u112 以上

## 我有疑问?

请访问 https://nutz.cn 获取帮助