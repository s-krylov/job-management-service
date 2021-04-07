ARG BUILD_IMAGE=maven:3.8.1-adoptopenjdk-15
ARG TOMCAT_IMAGE=tomcat:jdk15-adoptopenjdk-openj9

FROM ${BUILD_IMAGE} as common-dependency
COPY common/ /common/
WORKDIR /common
RUN mvn -B clean install
#####################

FROM common-dependency as base-dependency
COPY service/pom.xml /service/
WORKDIR /service
RUN mvn dependency:go-offline
#####################

FROM base-dependency as dependency
COPY job/ /job/
WORKDIR /job
RUN mvn -B clean install
#####################

FROM base-dependency as build
COPY service/ /service/
WORKDIR /service
RUN mvn -B clean package
#####################

FROM ${TOMCAT_IMAGE}
COPY --from=build service/target/service.war /usr/local/tomcat/webapps/
COPY --from=dependency job/target/job-impl.jar /usr/local/tomcat/lib/
COPY --from=common-dependency common/target/common.jar /usr/local/tomcat/lib/
ENV PATH=/usr/local/tomcat/bin:/opt/java/openjdk/bin:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
WORKDIR /usr/local/tomcat
EXPOSE 8080
CMD ["catalina.sh", "run"]