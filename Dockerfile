FROM bellsoft/liberica-openjre-alpine:21

LABEL org.opencontainers.image.source="https://github.com/michellao/temp-files-hoster"
LABEL org.opencontainers.image.licenses="MIT"

RUN adduser -Dh /app app
USER app
COPY ./build/libs/shorturl-0.0.1-SNAPSHOT.jar /app

WORKDIR /app

EXPOSE 8080
ENTRYPOINT ["java", "-Xms512M", "-Xmx1G", "-jar", "shorturl-0.0.1-SNAPSHOT.jar"]