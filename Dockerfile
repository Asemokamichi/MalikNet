FROM openjdk:23-jdk-slim
LABEL authors="asemalikova"

WORKDIR /app

COPY build/libs/MalikNet-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]