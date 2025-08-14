FROM openjdk:21-jdk-slim

WORKDIR /app

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

COPY build/libs/emailService-0.0.1-SNAPSHOT.jar app.jar


EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
