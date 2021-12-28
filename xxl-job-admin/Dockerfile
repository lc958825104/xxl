FROM registry.cn-hangzhou.aliyuncs.com/bimface_common/centos7-jdk8:v2_u181
MAINTAINER BIMFACE

ENV TZ=PRC
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ADD target/xxl-job-admin.jar /app.jar

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /app.jar "]