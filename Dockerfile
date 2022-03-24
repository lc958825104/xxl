FROM maven:3-jdk-8-slim as build
WORKDIR /xxl-job
COPY settings.xml /root/.m2/settings.xml
COPY pom.xml pom.xml
COPY xxl-job-admin xxl-job-admin
COPY xxl-job-core xxl-job-core
COPY xxl-job-executor-samples xxl-job-executor-samples
RUN mvn package -P release

FROM openjdk:8-jre-slim
WORKDIR /opt
COPY --from=build /xxl-job/xxl-job-admin/target/xxl-job-admin-*.jar xxl-job-admin.jar
COPY --from=build /xxl-job/xxl-job-executor-samples/xxl-job-executor-sample-springboot/target/xxl-job-executor-sample-springboot-*.jar xxl-job-executor.jar
