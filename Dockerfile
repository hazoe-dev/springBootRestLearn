FROM eclipse-temurin:25.0.1_8-jdk

COPY target/job-app.jar job-app.jar

LABEL authors="hazoe-dev"

ENTRYPOINT ["java", "-jar", "/job-app.jar"]