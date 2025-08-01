FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
