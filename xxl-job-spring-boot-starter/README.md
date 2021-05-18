
# 使用方式
## 1. 添加依赖
```xml
<dependency>
    <groupId>com.xuxueli</groupId>
    <artifactId>xxl-job-spring-boot-starter</artifactId>
    <version>2.3.0</version>
</dependency>
```

## 2. 配置 application.yml
遵循约定优于配置的原则, 提供更多的默认配置  
最低配置仅需提供 admin.address 即可
```yaml
spring:
  application:
    name: demo-app

xxl-job:
  admin:
    addresses: http://localhost:7878     # 必填
    access-token: abcdefghijklmn               # 可选, 若 amin 配有 accessToken, 则需要填写
  executor:
    enable: true                               # 是否启用, 默认: true
    app-name: demo-app                         # 可选, 为空则取值 ${spring.application.name}
    port-min: 30000                            # 可选, 默认: 30000
    port-max: 40000                            # 可选, 默认: 49151
    ip: 192.168.3.5                            # 可选, 为空则按 preferred-networks 匹配
    preferred-networks:                        # 可选, 当 ip 为空时, 将遍历网卡的 ip 与此值进行 startWith 匹配
    - 192.168.50
```
### 关于 ip 相关配置
若已经配置 ip, 则以配置为准  
若 ip 留空, 则按 preferred-networks 进行匹配  
若 ip 与 preferred-networks 均未提供, 则按 xxl-job-core 的 IpUtil 获取  

警告:  
多网卡环境, 采用 IpUtil 获取本机 ip 进行注册, 可能存在问题.  
比如, admin 与 job 不在同一台机器, 此时使用 job 获取的本机 ip 注册到 admin, 但 admin 可能无法访问此 ip!!!  

建议:  
手工指定本机 ip, 或者配置 preferred-networks, 以确保 admin 能正常访问此 ip


### 关于 port 相关配置
port-max 和 port-min 分别指端口的最大值和最小值  
启动时, 会在区间内自动查找可用端口.  
查找方法: 由最大值开始, 一直递减, 直到找到可用为止.  
若此区间内无可用端口, 则会抛出异常, 无法启动  

通常情况下, 建议使用默认的区间值  
未提供特定端口的配置, 若希望固定端口, 请将 port-min 与 port-max 设为同一值


## 3. 其他
照常编写 @XxlJob 之类的任务方法即可
