FROM ghcr.io/graalvm/graalvm-ce:21.3.3 as native-image-builder

RUN gu install native-image

WORKDIR /app

# Copy the shadow JAR into the container
COPY build/libs/*.jar application.jar

# Build the native executable
RUN native-image \
    --no-server \
    --enable-http \
    --enable-https \
    --no-fallback \
    -H:Name=application \
    -H:Class=org.ops.engine.Application \
    -jar application.jar

CMD ["cp", "/app/application", "/output/application"]
