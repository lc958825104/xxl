-- oracle 11g 测试通过
/*
-- 清理脚本
drop table xxl_job_info;
drop table xxl_job_log;
drop table xxl_job_log_report;
drop table xxl_job_logglue;
drop table xxl_job_registry;
drop table xxl_job_group;
drop table xxl_job_user;
drop table xxl_job_lock;

drop sequence SEQ_XXL_JOB_COMMON_ID;
drop sequence SEQ_XXL_JOB_LOG_ID;
*/

create sequence SEQ_XXL_JOB_COMMON_ID
    minvalue 10000001
    maxvalue 2147483647
    increment by 1
    cache 20
cycle;

create sequence SEQ_XXL_JOB_LOG_ID
    minvalue 10000001
    maxvalue 99999999999999
    increment by 1
    cache 20
cycle;

create table xxl_job_info (
                              id number(11) not null, -- AUTO_INCREMENT
                              job_group number(11) not null,
                              job_desc   varchar2(255) not null,
                              add_time date default null,
                              update_time date default null,
                              author   varchar2(64) default null,
                              alarm_email   varchar2(255) default null,
                              schedule_type   varchar2(50) default 'NONE' not null,
                              schedule_conf   varchar2(128) default null,
                              misfire_strategy   varchar2(50) default 'DO_NOTHING' not null,
                              executor_route_strategy   varchar2(50) default null,
                              executor_handler   varchar2(255) default null,
                              executor_param   varchar2(512) default null,
                              executor_block_strategy   varchar2(50) default null,
                              executor_timeout number(11) default '0' not null,
                              executor_fail_retry_count number(11) default '0' not null,
                              glue_type   varchar2(50) not null,
                              glue_source clob,
                              glue_remark   varchar2(128) default null,
                              glue_updatetime date default null,
                              child_jobid   varchar2(255) default null,
                              trigger_status number(4) default '0' not null,
                              trigger_last_time number(13) default '0' not null,
                              trigger_next_time number(13) default '0' not null,
                              primary key (id)
);
comment on column xxl_job_info.job_group is '执行器主键ID';
comment on column xxl_job_info.author is '作者';
comment on column xxl_job_info.alarm_email is '报警邮件';
comment on column xxl_job_info.schedule_type is '调度类型';
comment on column xxl_job_info.schedule_conf is '调度配置，值含义取决于调度类型';
comment on column xxl_job_info.misfire_strategy is '调度过期策略';
comment on column xxl_job_info.executor_route_strategy is '执行器路由策略';
comment on column xxl_job_info.executor_handler is '执行器任务handler';
comment on column xxl_job_info.executor_param is '执行器任务参数';
comment on column xxl_job_info.executor_block_strategy is '阻塞处理策略';
comment on column xxl_job_info.executor_timeout is '任务执行超时时间，单位秒';
comment on column xxl_job_info.executor_fail_retry_count is '失败重试次数';
comment on column xxl_job_info.glue_type is 'GLUE类型';
comment on column xxl_job_info.glue_source is 'GLUE源代码';
comment on column xxl_job_info.glue_remark is 'GLUE备注';
comment on column xxl_job_info.glue_updatetime is 'GLUE更新时间';
comment on column xxl_job_info.child_jobid is '子任务ID，多个逗号分隔';
comment on column xxl_job_info.trigger_status is '调度状态：0-停止，1-运行';
comment on column xxl_job_info.trigger_last_time is '上次调度时间';
comment on column xxl_job_info.trigger_next_time is '下次调度时间';


create table xxl_job_log (
                             id number(20) not null, -- AUTO_INCREMENT
                             job_group number(11) not null ,
                             job_id number(11) not null ,
                             executor_address   varchar2(255) default null ,
                             executor_handler   varchar2(255) default null ,
                             executor_param   varchar2(512) default null ,
                             executor_sharding_param   varchar2(20) default null ,
                             executor_fail_retry_count number(11) default '0' not null,
                             trigger_time date default null ,
                             trigger_code number(11) not null,
                             trigger_msg clob ,
                             handle_time date default null ,
                             handle_code number(11) not null,
                             handle_msg clob ,
                             alarm_status number(4) default '0' not null,
                             primary key (id)
);
create index i_trigger_time on xxl_job_log (trigger_time);
create index i_handle_code on xxl_job_log (handle_code);
comment on column xxl_job_log.job_group is '执行器主键ID';
comment on column xxl_job_log.job_id is '任务，主键ID';
comment on column xxl_job_log.executor_address is '执行器地址，本次执行的地址';
comment on column xxl_job_log.executor_handler is '执行器任务handler';
comment on column xxl_job_log.executor_param is '执行器任务参数';
comment on column xxl_job_log.executor_sharding_param is '执行器任务分片参数，格式如 1/2';
comment on column xxl_job_log.executor_fail_retry_count is '失败重试次数';
comment on column xxl_job_log.trigger_time is '调度-时间';
comment on column xxl_job_log.trigger_code is '调度-结果';
comment on column xxl_job_log.trigger_msg is '调度-日志';
comment on column xxl_job_log.handle_time is '执行-时间';
comment on column xxl_job_log.handle_code is '执行-状态';
comment on column xxl_job_log.handle_msg is '执行-日志';
comment on column xxl_job_log.alarm_status is '告警状态：0-默认、1-无需告警、2-告警成功、3-告警失败';

create table xxl_job_log_report (
                                    id number(11) not null, -- AUTO_INCREMENT
                                    trigger_day date default null,
                                    running_count number(11) default '0' not null,
                                    suc_count number(11) default '0' not null,
                                    fail_count number(11) default '0' not null,
                                    update_time date default null,
                                    primary key (id)
);
create unique index i_trigger_day on xxl_job_log_report (trigger_day);
comment on column xxl_job_log_report.trigger_day is '调度-时间' ;
comment on column xxl_job_log_report.running_count is '运行中-日志数量' ;
comment on column xxl_job_log_report.suc_count is '执行成功-日志数量' ;
comment on column xxl_job_log_report.fail_count is '执行失败-日志数量' ;

create table xxl_job_logglue (
                                 id number(11) not null, -- AUTO_INCREMENT
                                 job_id number(11) not null,
                                 glue_type   varchar2(50) default null,
                                 glue_source clob,
                                 glue_remark   varchar2(128) not null,
                                 add_time date default null,
                                 update_time date default null,
                                 primary key (id)
);
comment on column xxl_job_logglue.job_id is '任务，主键ID';
comment on column xxl_job_logglue.glue_type is 'GLUE类型';
comment on column xxl_job_logglue.glue_source is 'GLUE源代码';
comment on column xxl_job_logglue.glue_remark is 'GLUE备注';

create table xxl_job_registry (
                                  id number(11) not null, -- AUTO_INCREMENT
                                  registry_group   varchar2(50) not null,
                                  registry_key   varchar2(255) not null,
                                  registry_value   varchar2(255) not null,
                                  update_time date default null,
                                  primary key (id)
);
create index i_g_k_v on xxl_job_registry(registry_group, registry_key, registry_value);

create table xxl_job_group (
                               id number(11) not null, -- AUTO_INCREMENT
                               app_name   varchar2(64) not null,
                               title   varchar2(12) not null,
                               address_type number(4) default '0' not null,
                               address_list clob,
                               update_time date default null,
                               primary key (id)
);
comment on column xxl_job_group.app_name is '执行器AppName';
comment on column xxl_job_group.title is '执行器名称';
comment on column xxl_job_group.address_type is '执行器地址类型：0=自动注册、1=手动录入';
comment on column xxl_job_group.address_list is '执行器地址列表，多地址逗号分隔';

create table xxl_job_user (
                              id number(11) not null, -- AUTO_INCREMENT
                              username   varchar2(50) not null,
                              password   varchar2(50) not null,
                              role number(4) not null,
                              permission   varchar2(255) default null,
                              primary key (id)
);
create unique index i_username on xxl_job_user(username);
comment on column xxl_job_user.username is '账号';
comment on column xxl_job_user.password is '密码';
comment on column xxl_job_user.role is '角色：0-普通用户、1-管理员';
comment on column xxl_job_user.permission is '权限：执行器ID列表，多个逗号分割';

create table xxl_job_lock (
                              lock_name   varchar2(50) not null,
                              primary key (lock_name)
);
comment on column xxl_job_lock.lock_name is '锁名称';


-- 初始化数据
insert into xxl_job_group(id, app_name, title, address_type, address_list, update_time) values (1, 'xxl-job-executor-sample', '示例执行器', 0, NULL, to_date('2018-11-03 22:21:31', 'yyyy-mm-dd hh24:mi:ss') );
insert into xxl_job_info(id, job_group, job_desc, add_time, update_time, author, alarm_email, schedule_type, schedule_conf, misfire_strategy, executor_route_strategy, executor_handler, executor_param, executor_block_strategy, executor_timeout, executor_fail_retry_count, glue_type, glue_source, glue_remark, glue_updatetime, child_jobid) values (1, 1, '测试任务1', to_date('2018-11-03 22:21:31', 'yyyy-mm-dd hh24:mi:ss'), to_date('2018-11-03 22:21:31', 'yyyy-mm-dd hh24:mi:ss'), 'XXL', '', 'CRON', '0 0 0 * * ? *', 'DO_NOTHING', 'FIRST', 'demoJobHandler', '', 'SERIAL_EXECUTION', 0, 0, 'BEAN', '', 'GLUE代码初始化', to_date('2018-11-03 22:21:31', 'yyyy-mm-dd hh24:mi:ss'), '');
insert into xxl_job_user(id, username, password, role, permission) values (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 1, NULL);
insert into xxl_job_lock ( lock_name) values ( 'schedule_lock');

commit;
