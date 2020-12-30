FROM centos
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/fixer-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} springbootdocker.jar
RUN yum install -y java-11
ENTRYPOINT ["java","-jar", "/springbootdocker.jar"]
