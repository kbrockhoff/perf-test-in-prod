FROM openjdk:11-jdk-slim-stretch

VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

EXPOSE 8082
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
