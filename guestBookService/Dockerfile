FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/guestBookService-0.0.1-SNAPSHOT.jar app.jar

ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar /otel/opentelemetry-javaagent.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
